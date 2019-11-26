package com.oblac.kami

import com.oblac.kami.model.Board

class Solver {

	private var clicksCount = 0

	fun solvePuzzle(board: Board, numberOfSteps: Int) {
		solve(board, numberOfSteps, deep = 0)
	}

	var solved = false

	private fun solve(board: Board, maxClicks: Int, deep: Int): Boolean {
		val colorCount = board.colors.size

		if (colorCount == 1) {
			printSolvedSolution(board, deep)
			return true
		}

		if (deep >= maxClicks) {
			return false
		}

		// *** OPTIMISATION ***
		val remainingClicks = maxClicks - deep
		if (remainingClicks + 1 < colorCount) {
			// if the number of colors on the board is greater then number of clicks (+1)
			// then there is no point on going any further
			return false
		}

		ClicksProducer()
			.createClicks(board)
			// *** OPTIMISATION ***
			.parallel()
			.forEach { click ->
				if (solved) return@forEach

				clicksCount++
				if (clicksCount % 100_000 == 0) {
					println("\t$clicksCount")
				}

				val newBoard = board.click(click)

				if (solve(newBoard, maxClicks, deep + 1)) {
					solved = true
					// return true
				}
			}

		return solved
	}

	private fun printSolvedSolution(board: Board, totalSteps: Int) {
		println("Solved in $totalSteps steps and $clicksCount clicks:")

		board.history().forEach { println(it) }
	}


}
