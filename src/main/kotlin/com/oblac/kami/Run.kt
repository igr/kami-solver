package com.oblac.kami

import com.oblac.kami.cmd.Reducer
import com.oblac.kami.model.Board
import com.oblac.kami.model.Click
import kotlin.streams.toList

fun main() {
	val tilesVisitor = TilesVisitor()

	ImageParser().processImage("kami.png", tilesVisitor)

	val board = Reducer().reduce(tilesVisitor.toBoard())
	board.tiles().forEach { println(it) }

	foo(board, 0)
}


fun foo(board: Board, deep: Int) {
	val colorCount = board.countColors()
	if (colorCount == 1) {
		println("SOLVED")
		return
	}
	if (deep > 4) {
		return
	}

	val clicks = createClicks(board)

	for (click in clicks) {
		val newBoard = click.apply(board)
		foo(newBoard, deep + 1)
	}

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
