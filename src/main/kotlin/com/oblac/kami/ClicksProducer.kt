package com.oblac.kami

import com.oblac.kami.model.Board
import com.oblac.kami.model.Click
import java.util.stream.Stream

/**
 * Opposite to Clicker, this class is not part of the board.
 * it just uses the board to generate list of clicks.
 */
class ClicksProducer {

	/**
	 * Creates all possible clicks for the board.
	 */
	fun createClicks(board: Board): Stream<Click> {
		val colors = board.colors()

		return board.tiles()

			// sort by number of connections, from max to min
			//.sorted { o1, o2 -> o2.connectionsCount - o1.connectionsCount }

			.map {
				val clicks = mutableListOf<Click>()
				for (newColor in colors) {
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
					clicks.add(Click(it, newColor))
				}
				clicks
			}
			.flatMap { it.stream() }
	}

}