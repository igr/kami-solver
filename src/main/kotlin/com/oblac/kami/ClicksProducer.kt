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

//			// sort tiles by number of connections, from max to min
//			.sorted { o1, o2 -> o2.connectionsCount - o1.connectionsCount }

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
					clicks.add(Click(it, newColor))
				}
				clicks
			}
			.flatMap { it.stream() }
	}

}