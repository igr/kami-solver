package com.oblac.kami

import com.oblac.kami.model.Board
import kotlin.system.measureTimeMillis

class Puzzle(
	val index: Int,
	val numberOfSteps: Int,
	val boardFixer : (Board) -> Board = { it })

val puzzles = mapOf(
	"game-0" to Puzzle(0, 2),       // 15 ms (10 clicks)
	"game-1" to Puzzle(1, 4),       // 120 ms (4748 clicks)
	"game-2" to Puzzle(2, 4),       // 688 ms (6353 clicks)
	"game-3" to Puzzle(3, 3),       // 42 ms (281 clicks)
	"game-4" to Puzzle(4, 5),       // 255ms (4711 clicks)
	"game-5" to Puzzle(5, 9) { it.removeTilesOfColor(4) },      //

	"hard-1" to Puzzle(99901, 7) { it.removeTilesOfColor(5) },   // 8812ms (786046 clicks) OR
	"hard-2" to Puzzle(99902, 10)
)

fun main() {
	val puzzle = puzzles["game-1"] ?: error("Invalid puzzle name")
//	val puzzle = puzzles["hard-1"] ?: error("Invalid puzzle name")

	val loadedBoard = loadBoardFromScreenshot(puzzleScreenshotsIndex = puzzle.index)

	val board = puzzle.boardFixer.invoke(loadedBoard)

	println("Board: total ${board.colors.size} colors")

	board.tiles().forEach { println(it) }

	val elapsed = measureTimeMillis {
		Solver().solvePuzzle(board, numberOfSteps = puzzle.numberOfSteps)
	}

	println("Done in ${elapsed}ms")
}
