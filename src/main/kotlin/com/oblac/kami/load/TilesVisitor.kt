package com.oblac.kami.load

import com.oblac.kami.model.Tile

/**
 * Quick and UGLY visitor.
 */
class TilesVisitor {

	private var tilesPerRow = 0
	private var rowCount = 0
	private var tileIndexInRow = 0

	private val allTiles = mutableListOf<Tile>()

	val x
		get() = tileIndexInRow
	val y
		get() = rowCount

	fun visitTile(color: Int): Tile {
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

		return t
	}

	fun visitRowEnd() {
		rowCount++
		tileIndexInRow = 0
	}

	/**
	 * Returns set of loaded tiles.
	 */
	fun loadedTiles(): Set<Tile> {
		return allTiles.toSet()
	}
}