package com.oblac.kami

import com.oblac.kami.model.Board

class Solver {

	fun solvePuzzle(board: Board, numberOfSteps: Int) {
		solve(board, numberOfSteps, deep = 0)
	}

	private fun solve(board: Board, maxClicks: Int, deep: Int): Boolean {
		val colorCount = board.countColors()

		if (colorCount == 1) {
			printSolvedSolution(board, deep - 1)
			return true
		}

		if (deep >= maxClicks) {
			return false
		}

		val clicks = ClicksProducer(board).createClicks()

		for (click in clicks) {
			val newBoard = board.click(click)
			if (solve(newBoard, maxClicks, deep + 1)) {
				return true
			}
		}

		return false
	}

	private fun printSolvedSolution(board: Board, totalSteps: Int) {
		println("Solved in ${totalSteps + 1} steps:")

		board.history().forEach { println(it) }
	}


}
