package com.oblac.kami

import kotlin.system.measureTimeMillis

fun main() {
	val board = BoardLoader().loadBoardFromScreenshot(puzzleScreenshotsIndex = 1)

	board.tiles().forEach { println(it) }

	val elapsed = measureTimeMillis {
		Solver().solvePuzzle(board, numberOfSteps = 4)
	}

	println("Done in ${elapsed}ms")
}
