package com.oblac.kami

import kotlin.system.measureTimeMillis

data class Puzzle(val index: Int, val numberOfSteps: Int)

val puzzles = listOf(
	Puzzle(0, 2),
	Puzzle(1, 4),
	Puzzle(2, 4),
	Puzzle(3, 7)
)

fun main() {
	val puzzle = puzzles[3]

	val board = BoardLoader().loadBoardFromScreenshot(puzzleScreenshotsIndex = puzzle.index)

	board.tiles().forEach { println(it) }

	val elapsed = measureTimeMillis {
		Solver().solvePuzzle(board, numberOfSteps = puzzle.numberOfSteps)
	}

	println("Done in ${elapsed}ms")
}
