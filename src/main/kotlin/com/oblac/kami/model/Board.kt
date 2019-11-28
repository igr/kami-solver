package com.oblac.kami.model

import com.oblac.kami.TilesReducer
import java.util.stream.Stream

class Board(
	tiles: Set<Tile>,
	val parentBoard: PreviousBoard? = null
) {

	private val allTiles: Set<Tile> = TilesReducer().mergeAdjacentTilesOfSameColor(tiles)

	/**
	 * Indicates the depth of this board in the hierarchy of the game.
	 */
	val depth: Int = if (parentBoard != null) parentBoard.board.depth + 1 else 0

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

		var parent = parentBoard
		while (parent != null) {
			history.add(parent.changeClick)
			parent = parent.board.parentBoard
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

		return Board(newSet, parentBoard)
	}

}