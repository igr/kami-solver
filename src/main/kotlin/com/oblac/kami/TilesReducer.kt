package com.oblac.kami

import com.oblac.kami.model.Tile
import kotlin.streams.toList

class TilesReducer {

	fun mergeAdjacentTilesOfSameColor(tilesList: Set<Tile>): Set<Tile> {
		val tilesToReduce = tilesList.toMutableSet()
		val remainingTiles = mutableSetOf<Tile>()

		while (tilesToReduce.isNotEmpty()) {
			val currentTile = tilesToReduce.first()
			var hadConnectionsWithSameColor = false

			currentTile.connections()
				.filter { currentTile.color == it.color }
				.peek { hadConnectionsWithSameColor = true }
				.peek { tilesToReduce.remove(it) }
				.toList()   // create new list to avoid concurrent modifications
				.forEach {
					currentTile.join(it)
				}

			if (!hadConnectionsWithSameColor) {
				tilesToReduce.remove(currentTile)
				remainingTiles.add(currentTile)
			}
		}

		return remainingTiles
	}

}