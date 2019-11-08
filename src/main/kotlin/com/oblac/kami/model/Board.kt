package com.oblac.kami.model

import java.util.stream.Stream

class Board(
	private val allTiles: Set<Tile>,
	val parentBoard: PreviousBoard? = null
) {

	fun tiles(): Stream<Tile> {
		return allTiles.stream()
	}

	fun countColors(): Int {
		return (allTiles
			.map { it.color }
			.max() ?: 0) + 1
	}
}