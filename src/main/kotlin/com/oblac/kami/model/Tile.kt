package com.oblac.kami.model

data class Tile(
    private val x: Int,
    private val y: Int,
    private val color: Int) {

    private val connections = mutableSetOf<Tile>()

    fun connectTo(neighbour: Tile) {
        connections.add(neighbour);
        neighbour.connections.add(this)
    }

    override fun toString(): String {
        var s = ""
        connections.forEach { s += "${it.color} " }
        return "t: ($x, $y) $color → $s";
    }
}