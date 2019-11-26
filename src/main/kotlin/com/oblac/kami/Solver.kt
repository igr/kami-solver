package com.oblac.kami

import com.oblac.kami.model.Board

class Solver {

	private var clicksCount = 0

	fun solvePuzzle(board: Board, numberOfSteps: Int) {
		solve(board, numberOfSteps, deep = 0)
	}

	private fun solve(board: Board, maxClicks: Int, deep: Int): Boolean {
		val colorCount = board.countColors()

		if (colorCount == 1) {
			printSolvedSolution(board, deep)
			return true
		}

		if (deep >= maxClicks) {
			return false
		}

		val clicks = ClicksProducer().createClicks(board)

		for (click in clicks) {

			clicksCount++
			if (clicksCount % 100_000 == 0) {
				println("\t$clicksCount")
			}

			val newBoard = board.click(click)
			if (solve(newBoard, maxClicks, deep + 1)) {
				return true
			}
		}

		return false
	}

	private fun printSolvedSolution(board: Board, totalSteps: Int) {
		println("Solved in $totalSteps steps and $clicksCount clicks:")

		board.history().forEach { println(it) }
	}


}
