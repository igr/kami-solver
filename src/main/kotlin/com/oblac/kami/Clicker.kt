package com.oblac.kami

import com.oblac.kami.model.Board
import com.oblac.kami.model.Click
import com.oblac.kami.model.PreviousBoard
import com.oblac.kami.model.Tile
import java.util.stream.Collectors

class Clicker(private val board: Board) {

	/**
	 * Applies the click on the board.
	 */
	fun apply(click: Click): Board {
		val newTileOf = mutableMapOf<Tile, Tile>()

		val newTiles = board.tiles()
			.map {
				val newTile = if (it == click.tile)
					it.clone(click.nextColor) else it.clone()
				newTileOf[it] = newTile
				newTile
			}
			.collect(Collectors.toSet())

		board.tiles().forEach {
			it.connections().forEach { connection ->
				newTileOf[connection]?.let { newConnection -> newTileOf[it]?.connectTo(newConnection) }
			}
		}

		return Board(newTiles, parentBoard = PreviousBoard(board, click))
	}

}