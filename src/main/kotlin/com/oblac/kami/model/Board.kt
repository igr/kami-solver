package com.oblac.kami.model

import kotlin.streams.toList

class Board(private val allTiles: Set<Tile>) {

	constructor(all: List<Tile>) : this(all.toSet())

	// replace null with some stupidity
	private var parentBoard: Board? = null

	fun forEach(tileConsumer: (Tile) -> Unit) {
		allTiles.forEach(tileConsumer)
	}

	fun reduce(): Board {
		val tilesToReduce = allTiles.toMutableSet()
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
		return Board(remainingTiles)
	}
}