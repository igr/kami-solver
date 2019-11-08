package com.oblac.kami.cmd

import com.oblac.kami.model.Board
import com.oblac.kami.model.Tile
import kotlin.streams.toList

class Reducer {

	fun reduce(board: Board): Board {
		val tilesToReduce = board.tiles().toList().toMutableSet()
		val remainingTiles = mutableSetOf<Tile>()

		while (tilesToReduce.isNotEmpty()) {
			val currentTile = tilesToReduce.first()
			var hadConnectionsWithSameColor = false

			currentTile.connections()
				.filter { currentTile.color == it.color }
				.peek { hadConnectionsWithSameColor = true }
				.peek { tilesToReduce.remove(it) }
				.toList()
				.forEach {
					currentTile.join(it)
				}

			if (!hadConnectionsWithSameColor) {
				tilesToReduce.remove(currentTile)
				remainingTiles.add(currentTile)
			}
		}

		return Board(remainingTiles, board.parentBoard)
	}

}