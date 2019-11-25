package com.oblac.kami

import com.oblac.kami.model.Board
import com.oblac.kami.model.Click
import com.oblac.kami.model.PreviousBoard
import com.oblac.kami.model.Tile
import kotlin.streams.toList

class Clicker(private val board: Board) {

	fun apply(click: Click): Board {
		val newTileOf = mutableMapOf<Tile, Tile>()

		val newTiles = board.tiles()
			.map {
				val newTile = if (it == click.tile)
					it.clone(click.nextColor) else it.clone()
				newTileOf[it] = newTile
				newTile
			}
			.toList()

		board.tiles().forEach {
			it.connections().forEach { connection ->
				newTileOf[connection]?.let { newConnection -> newTileOf[it]?.connectTo(newConnection) }
			}
		}

		return Board(newTiles.toSet(), parentBoard = PreviousBoard(board, click))
	}

}