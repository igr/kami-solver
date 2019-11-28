package com.oblac.kami

import kotlin.system.measureTimeMillis

data class Puzzle(val index: Int, val numberOfSteps: Int)

val puzzles = mapOf(
	"game-0" to Puzzle(0, 2),       // 15 ms (10 clicks)
	"game-1" to Puzzle(1, 4),       // 120 ms (4748 clicks)
	"game-2" to Puzzle(2, 4),       // 688 ms (6353 clicks)
	"game-3" to Puzzle(3, 3),       // 42 ms (281 clicks)
	"game-4" to Puzzle(4, 5),       // 487ms (10922 clicks)

	"hard-1" to Puzzle(99901, 7),
	"hard-2" to Puzzle(99902, 7)
)

fun main() {
	val puzzle = puzzles["game-4"] ?: error("Invalid puzzle name")
//	val puzzle = puzzles["hard-1"] ?: error("Invalid puzzle name")

	val board = BoardLoader().loadBoardFromScreenshot(puzzleScreenshotsIndex = puzzle.index)

	board.tiles().forEach { println(it) }

	val elapsed = measureTimeMillis {
		Solver().solvePuzzle(board, numberOfSteps = puzzle.numberOfSteps)
	}

	println("Done in ${elapsed}ms")
}
