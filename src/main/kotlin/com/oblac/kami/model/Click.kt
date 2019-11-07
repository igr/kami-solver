package com.oblac.kami.model

import com.oblac.kami.cmd.Cloner
import com.oblac.kami.cmd.Reducer

class Click(private val tile: Tile, private val nextColor: Int) {

	fun apply(board: Board): Board {
		val clonedBoard = Cloner().clone(board, tile, nextColor)
		return Reducer().reduce(clonedBoard)
	}

}