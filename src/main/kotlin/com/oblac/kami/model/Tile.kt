package com.oblac.kami.model

import java.lang.Integer.min
import java.util.stream.Stream

// Kotlin, ah Kotlin - can't ignore var/val from ctor in data classes :(
// need to ignore `weight`
class Tile(
	val x: Int,
	val y: Int,
	val color: Int,
	var weight: Int) {

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
	 * Returns connections count.
	 */
	val connectionsCount: Int
		get() {
			return connections.size
		}

	/**
	 * Returns true if given color matches one of the connections.
	 */
	fun matchesAdjacentColors(color: Int): Boolean {
		return connections.find {
			it.color == color
		} != null
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
		this.weight = min(otherTile.weight, this.weight)

		otherTile.connections()
			.filter { it != this }
			.forEach { this.connectTo(it) }
		otherTile.detach()
	}


	fun isConnectedTo(tile: Tile): Boolean {
		if (this == tile) {
			return true
		}

		val todo = mutableSetOf<Tile>()
		val processed = mutableSetOf(this)

		todo.addAll(connections)

		while (true) {
			val next = todo.firstOrNull() ?: return false
			todo.remove(next)

			if (next == tile) {
				return true
			}
			if (processed.contains(next)) {
				continue
			}
			todo.addAll(next.connections)
			processed.add(next)
		}
	}



	/**
	 * Clones a tile to new one with optionally new color.
	 */
	fun clone(c: Int = this.color): Tile {
		return Tile(this.x, this.y, c, weight)
	}

	override fun toString(): String {
		var s = ""
		connections.forEach { s += "${it.color} " }
		return "t: ($x, $y) w:$weight c:$color → $s"
	}


	// EQUALS && HASHCODE

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Tile

		if (x != other.x) return false
		if (y != other.y) return false
		if (color != other.color) return false

		return true
	}

	override fun hashCode(): Int {
		var result = x
		result = 31 * result + y
		result = 31 * result + color
		return result
	}
}