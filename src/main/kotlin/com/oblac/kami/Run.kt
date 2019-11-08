package com.oblac.kami

import com.oblac.kami.cmd.Cmd
import com.oblac.kami.model.Board
import com.oblac.kami.model.Click
import kotlin.streams.toList

fun main() {
	val tilesVisitor = TilesVisitor()

	ImageParser().processImage("kami2.png", tilesVisitor)
	val maxClicks = 3

	val board = Cmd.reducer.reduce(tilesVisitor.toBoard())
	board.tiles().forEach { println(it) }

	solve(board, maxClicks, deep = 0)
}


fun solve(board: Board, maxClicks: Int, deep: Int): Boolean {
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
		val newBoard = Cmd.clicker.click(board, click)
		if (solve(newBoard, maxClicks, deep + 1)) {
			return true
		}
	}
	return false
}

fun printSolvedSolution(board: Board, totalSteps: Int) {
	println("Solved in $totalSteps steps")

	val history = mutableListOf<Click>()

	var parent = board.parentBoard
	while (parent != null) {
		history.add(parent.changeClick)
		parent = parent.board.parentBoard
	}

	history.reverse()
	history.forEach { println(it) }
}


fun createClicks(board: Board): List<Click> {
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
