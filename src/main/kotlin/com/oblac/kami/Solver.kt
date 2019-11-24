package com.oblac.kami

import com.oblac.kami.cmd.Clicker
import com.oblac.kami.cmd.Reducer
import com.oblac.kami.model.Board
import com.oblac.kami.model.Click
import kotlin.streams.toList

class Solver(
	private val clicker: Clicker,
	private val reducer: Reducer,
	private val imageParser: ImageParser) {

	fun solvePuzzle(puzzleScreenshotsIndex: Int, numberOfSteps: Int) {
		val board = loadBoard(puzzleScreenshotsIndex)

		board.tiles().forEach { println(it) }

		solve(board, numberOfSteps, deep = 0)
	}

	private fun loadBoard(puzzleScreenshotsIndex: Int): Board {
		val tilesVisitor = TilesVisitor()

		imageParser.processImage("puzzles/kami${puzzleScreenshotsIndex}.png", tilesVisitor)

		return reducer.reduce(tilesVisitor.toBoard())
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

		val clicks = createClicks(board)

		for (click in clicks) {
			val newBoard = clicker.click(board, click)
			if (solve(newBoard, maxClicks, deep + 1)) {
				return true
			}
		}
		return false
	}

	private fun createClicks(board: Board): List<Click> {
		val colorCount = board.countColors()

		return board.tiles()
			.map {
				val clicks = mutableListOf<Click>()
				for (c in 0 until colorCount) {
					if (c != it.color) {
						clicks.add(Click(it, c))
					}
				}
				clicks
			}
			.flatMap { it.stream() }
			.toList()
	}

	private fun printSolvedSolution(board: Board, totalSteps: Int) {
		println("Solved in ${totalSteps + 1} steps:")

		val history = mutableListOf<Click>()

		var parent = board.parentBoard
		while (parent != null) {
			history.add(parent.changeClick)
			parent = parent.board.parentBoard
		}

		history.reverse()
		history.forEach { println(it) }
	}

}
