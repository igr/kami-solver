package com.oblac.kami

import com.oblac.kami.model.Board
import java.util.*

class Solver {

	private var queue = arrayListOf<LinkedList<Board>>()

	fun solvePuzzle(board: Board, numberOfSteps: Int) {
		val maxColors = board.colors.size

		println("Total $maxColors colors")

		for (color in 0..maxColors) queue.add(LinkedList())

		queue[maxColors].add(board)

		solve(numberOfSteps)
	}

	private fun solve(maxClicks: Int) {
		var clicksCounter = 0
		while (true) {
			val board = nextBoard()
			if (board.colors.size == 1) {
				printSolvedSolution(board, clicksCounter)
				return
			}

			val remainingClicks = maxClicks - board.depth
			// ***
			if (remainingClicks == 0) continue
			// ***
			if (remainingClicks + 1 < board.colors.size) {
				// if the number of colors on the board is greater then number of clicks (+1)
				// then there is no point on going any further. For example, if there are 3 nodes
				// left with different colors all connected, you can't solve it with 1 click, only with 2.
				continue
			}

			ClicksProducer()
				.createClicks(board)
				.parallel()
				.peek {
					clicksCounter++
					printDebug(clicksCounter)
				}
				.map { board.click(it) }
				.forEach {
					addBoardToQueue(it)
				}
		}
	}

	/**
	 * Returns the very next board to process.
	 */
	private fun nextBoard(): Board {
		for (boardList in queue) {
			if (boardList.isNotEmpty()) return boardList.removeFirst() else continue
		}
		throw Exception("No boards left :(")
	}

	private fun addBoardToQueue(board: Board) {
		synchronized(queue) {
			val boardColors = board.colors.size
			queue[boardColors].add(0, board)

			// new boards are always added first,
			// so deeper boards are going to be processed first
		}
	}

	private fun printDebug(clicksCounter: Int) {
		if (clicksCounter % 100_000 == 0) {
			println("\t$clicksCounter")
			for ((index, list) in queue.withIndex()) {
				println("\t\t$index: ${list.size}")
			}
		}

	}

	private fun printSolvedSolution(board: Board, totalClicks: Int) {
		println("Solved in ${board.depth} steps and total $totalClicks clicks:")

		board.history().forEach { println(it) }
	}

}
