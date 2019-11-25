package com.oblac.kami

import com.oblac.kami.model.Board
import com.oblac.kami.model.Click
import kotlin.streams.toList

class ClicksProducer(private val board: Board) {

	fun createClicks(): List<Click> {
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