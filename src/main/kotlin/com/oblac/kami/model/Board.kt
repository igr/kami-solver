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
	 * Indicates the depth of this board in the hierarchy.
	 */
	val depth: Int = if (parentBoard != null) parentBoard.board.depth + 1 else 0

	/**
	 * Returns stream of tiles this board consist of.
	 */
	fun tiles(): Stream<Tile> {
		return allTiles.stream()
	}

	// Instead of having a function, I go here with lazy value
	// as Board is immutable. This also gives some performance
	val colors by lazy {
		allTiles
			.map { it.color }
			.distinct()
	}

	/**
	 * Returns history of all previous boards, from the first one to the current one.
	 */
	fun history(): List<Click> {
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