package com.oblac.kami

import com.oblac.kami.model.Board
import com.oblac.kami.model.Tile

/**
 * Splits a board into several boards.
 */
fun splitBoard(board: Board): List<Board> {
	val splitSets = mutableSetOf<MutableSet<Tile>>()

	board.tiles().forEach { tile ->
		var tileConnected = false

		splitSets
			.filter { it.isNotEmpty() }
			.forEach set@{ set ->
				val firstTile = set.first()
				if (tile.isConnectedTo(firstTile)) {
					set.add(tile)
					tileConnected = true
					return@set
				}
			}

		if (!tileConnected) {
			splitSets.add(mutableSetOf(tile))
		}
	}

	if (splitSets.size == 1) {
		return listOf(board)
	}

	return splitSets.asSequence().map { Board(it) }.toList()
}
