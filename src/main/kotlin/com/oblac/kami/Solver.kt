package com.oblac.kami

import com.oblac.kami.model.Board
import java.util.*

class Solver(board: Board, private val maxClicks: Int) {

	var clicksCounter = 0
	var currentBoard : Board = board

	// *** OPTIMISATION ***
	// Instead of having one queue, we have many: one for each number of colors.
	// We assume that clicks that _reduce_ number of colors is "better" ones
	// as they might go towards the solution
	private var queue = arrayListOf<LinkedList<Board>>()

	init {
		val maxColors = board.colors.size
		for (color in 0..maxColors) queue.add(LinkedList())
		queue[maxColors].add(board)
	}

	fun solvePuzzle(): Int {
		while (true) {
			currentBoard = nextBoard() ?: return -1

			if (currentBoard.colors.size == 1) {
				return currentBoard.colors[0]
			}

			val remainingClicks = maxClicks - currentBoard.depth

			if (remainingClicks == 0) continue

			// *** OPTIMISATION ***
			if (remainingClicks + 1 < currentBoard.colors.size) {
				// if the number of colors on the board is greater then number of clicks (+1)
				// then there is no point on going any further. For example, if there are 3 nodes
				// left with all different colors, you can't solve it with 1 click, only with 2.
				continue
			}

			ClicksProducer()
				.createClicks(currentBoard)
				.parallel()
//				.sorted { c1, c2 -> c2.nextColor - c1.nextColor }
				.sorted { c1, c2 -> c2.tile.connectionsCount - c1.tile.connectionsCount }
				.peek { printDebug(++clicksCounter) }
				.map { it.click() }
				.forEach { addBoardToQueue(it) }
		}
	}

	/**
	 * Returns the very next board to process.
	 */
	private fun nextBoard(): Board? {
		for (boardList in queue) {
			if (boardList.isNotEmpty()) return boardList.removeFirst() else continue
		}
		return null
	}

	private fun addBoardToQueue(board: Board) {
		val boardColors = board.colors.size
		val boardList = queue[boardColors]
		synchronized(boardList) {
			boardList.add(0, board)

			// *** OPTIMISATION ***
			// new boards are always added first,
			// so deeper boards are going to be processed first.
			// this also super-significantly saves the memory usage
			// as boards are not accumulated
		}
	}

	private fun printDebug(clicksCounter: Int) {
		if (clicksCounter % 100_000 == 0) {
			println("\t$clicksCounter")
		}

	}

}
