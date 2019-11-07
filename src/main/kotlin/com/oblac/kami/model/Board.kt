package com.oblac.kami.model

import java.util.stream.Stream

class Board(private val allTiles: Set<Tile>) {

	constructor(all: List<Tile>) : this(all.toSet())

	// replace null with some stupidity
	private var parentBoard: Board? = null

	fun tiles(): Stream<Tile> {
		return allTiles.stream()
	}

	fun countColors(): Int {
		return (allTiles
			.map { it.color }
			.max() ?: 0) + 1
	}
}