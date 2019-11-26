package com.oblac.kami

import com.oblac.kami.model.Board
import com.oblac.kami.model.Click
import kotlin.streams.toList

/**
 * Opposite to Clicker, this class is not part of the board.
 * it just uses the board to generate list of clicks.
 */
class ClicksProducer() {

	/**
	 * Creates all possible clicks for the board.
	 */
	fun createClicks(board: Board): List<Click> {
		val colors = board.colors()

		return board.tiles()
			.map {
				val clicks = mutableListOf<Click>()
				for (c in colors) {
					if (c != it.color) {
						clicks.add(Click(it, c))
					}
				}
				clicks
			}
			.flatMap { it.stream() }
			.toList()
	}

}