package com.oblac.kami.model

import com.oblac.kami.TilesReducer
import java.util.stream.Stream

class Board(
	tiles: Set<Tile>,
	val parent: Click? = null
) {

	private val allTiles: Set<Tile> = TilesReducer().mergeAdjacentTilesOfSameColor(tiles)

	/**
	 * Indicates the depth of this board in the hierarchy of the game.
	 */
	val depth: Int = if (parent != null) parent.board.depth + parent.tile.weight else 0

	/**
	 * Returns stream of tiles this board consist of.
	 */
	fun tiles(): Stream<Tile> {
		return allTiles.stream()
	}

	// Instead of having a function, I go here with lazy value
	// as Board is immutable - gives better performances.
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

		var parent = parent
		while (parent != null) {
			history.add(parent)
			parent = parent.board.parent
		}

		history.reverse()
		return history
	}

	/**
	 * Removes all the tiles of given color.
	 */
	fun removeTilesOfColor(color: Int): Board {

		allTiles.filter { it.color == color }.forEach { it.detach() }
		val newSet = allTiles.filter { it.color != color }.toSet()

		return Board(newSet, parent)
	}

	override fun toString(): String {
		return "Board.$depth (${allTiles.size}/${colors.size})"
	}
}