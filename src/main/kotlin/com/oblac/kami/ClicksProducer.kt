package com.oblac.kami

import com.oblac.kami.model.Board
import com.oblac.kami.model.Click
import java.util.stream.Stream

class ClicksProducer {

	/**
	 * Creates all possible clicks for the board.
	 */
	fun createClicks(board: Board): Stream<Click> {
		return board
			.tiles()
			.map {
				val clicks = mutableListOf<Click>()
				for (newColor in board.colors) {
					if (newColor == it.color) {
						// ignore clicks of the same color
						continue
					}
					// *** OPTIMISATION ***
					if (!it.matchesAdjacentColors(newColor)) {
						// ignore clicks in color different from the
						// connections colors
						continue
					}
					clicks.add(Click(board, it, newColor))
				}
				clicks
			}
			.flatMap { it.stream() }
	}

}