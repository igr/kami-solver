package com.oblac.kami.cmd

import com.oblac.kami.model.Board
import com.oblac.kami.model.Tile
import kotlin.streams.toList

class Cloner {

	fun clone(board: Board, clickedTile: Tile, newColor: Int): Board {
		val newTileOf = mutableMapOf<Tile, Tile>()

		val newTiles = board.tiles()
			.map {
				val newTile = if (it == clickedTile)
					cloneTile(it, newColor) else cloneTile(it)
				newTileOf[it] = newTile
				newTile
			}
			.toList()

		board.tiles().forEach {
			it.connections().forEach { connection ->
				newTileOf[connection]?.let { newConnection -> newTileOf[it]?.connectTo(newConnection) }
			}
		}

		return Board(newTiles)
	}

	private fun cloneTile(t: Tile, c: Int = t.color): Tile {
		return Tile(t.x, t.y, c)
	}

}