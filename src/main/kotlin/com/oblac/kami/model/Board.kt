package com.oblac.kami.model

import com.oblac.kami.Clicker
import com.oblac.kami.TilesReducer
import java.util.stream.Stream

class Board(
	tiles: Set<Tile>,
	val parentBoard: PreviousBoard? = null
) {

	private val allTiles: Set<Tile> = TilesReducer().mergeAdjacentTilesOfSameColor(tiles)

	/**
	 * Returns stream of tiles this board consist of.
	 */
	fun tiles(): Stream<Tile> {
		return allTiles.stream()
	}

	/**
	 * Counts total number of distinctive colors.
	 */
	fun countColors(): Int {
		return colors().count()
	}

	/**
	 * Returns list of colors used by all tiles.
	 */
	fun colors(): List<Int> {
		return allTiles
			.map { it.color }
			.distinct()
	}

	/**
	 * Returns history of all previous boards, from the first to this one.
	 */
	fun history(): MutableList<Click> {
		val history = mutableListOf<Click>()

		var parent = parentBoard
		while (parent != null) {
			history.add(parent.changeClick)
			parent = parent.board.parentBoard
		}

		history.reverse()
		return history
	}
	
	/**
	 * Applies a click and generates new board.
	 */
	fun click(click: Click): Board {
		return clicker.apply(click)
	}

	/**
	 * Caching the `clicker` instance.
	 * @see Clicker
	 */
	private val clicker by lazy { Clicker(this) }

}