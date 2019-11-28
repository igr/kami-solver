package com.oblac.kami.model

import java.util.stream.Collectors

data class Click(val board: Board, val tile: Tile, val nextColor: Int) {

	/**
	 * Applies the click on the board.
	 */
	fun click(): Board {
		val newTileOf = mutableMapOf<Tile, Tile>()

		val newTiles = board.tiles()
			.map {
				val newTile = if (it == tile)
					it.clone(nextColor) else it.clone()
				newTileOf[it] = newTile
				newTile
			}
			.collect(Collectors.toSet())

		board.tiles().forEach {
			it.connections().forEach { connection ->
				newTileOf[connection]?.let { newConnection -> newTileOf[it]?.connectTo(newConnection) }
			}
		}

		return Board(newTiles, parent = this)
	}

}