package com.oblac.kami.cmd

import com.oblac.kami.model.Board
import com.oblac.kami.model.Click

class Clicker(private val cloner: Cloner, private val reducer: Reducer) {

	fun click(board: Board, click: Click): Board {
		val clonedBoard = cloner.clone(board, click)

		return reducer.reduce(clonedBoard)
	}
}