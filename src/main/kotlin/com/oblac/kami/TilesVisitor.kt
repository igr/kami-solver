package com.oblac.kami

import com.oblac.kami.model.Board
import com.oblac.kami.model.Tile

class TilesVisitor {

	private var tilesPerRow = 0
	private var rowCount = 0
	private var tileIndexInRow = 0

	private val allTiles = mutableListOf<Tile>()

	fun visitTriangle(color: Int) {
		val t = Tile(
			x = tileIndexInRow, y = rowCount,
			color = color)

		if (rowCount == 0) {
			tilesPerRow++
		}

		// connect above
		if (rowCount != 0) {
			t.connectTo(allTiles[allTiles.size - tilesPerRow])
		}

		// connect left
		if (tileIndexInRow != 0) {
			if (tileIndexInRow % 2 == (rowCount % 2)) {
				t.connectTo(allTiles.last())
			}
		}

		allTiles.add(t)

		// continue loop

		tileIndexInRow++
	}

	fun visitRowEnd() {
		rowCount++
		tileIndexInRow = 0
	}

	fun forEach(tileConsumer: (Tile) -> Unit) {
		allTiles.forEach(tileConsumer)
	}

	fun toBoard(): Board {
		return Board(allTiles)
	}
}