package com.oblac.kami.model

import java.util.stream.Stream

data class Tile(
	val x: Int,
	val y: Int,
	val color: Int) {

	private val connections = mutableSetOf<Tile>()

	/**
	 * Connects two tiles.
	 */
	fun connectTo(otherTile: Tile) {
		require(otherTile != this)

		connections.add(otherTile)
		otherTile.connections.add(this)
	}

	/**
	 * Returns connections stream.
	 */
	fun connections(): Stream<Tile> {
		return connections.stream()
	}

	/**
	 * Detaches this tile from all it's connections.
	 */
	fun detach() {
		connections.forEach {
			it.connections.remove(this)
		}
		connections.clear()
	}

	/**
	 * Joins this tile with the other tile by taking all its connections.
	 */
	fun join(otherTile: Tile) {
		otherTile.connections()
			.filter { it != this }
			.forEach { this.connectTo(it) }
		otherTile.detach()
	}

	/**
	 * Clones a tile to new one with optionally new color.
	 */
	fun clone(c: Int = this.color): Tile {
		return Tile(this.x, this.y, c)
	}

	override fun toString(): String {
		var s = ""
		connections.forEach { s += "${it.color} " }
		return "t: ($x, $y) c:$color → $s"
	}
}