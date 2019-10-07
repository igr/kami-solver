package com.oblac.kami.model

data class Tile(
    private val x: Int,
    private val y: Int,
    private val color: Int) {

    private val connections = mutableSetOf<Tile>()

    fun connectTo(neighbour: Tile) {
        connections.add(neighbour)
        neighbour.connections.add(this)
    }

    fun uniteSameColorNeighbours(): MutableSet<Tile> {
        val secondStepConnections = mutableSetOf<Tile>()
        val obsoleteTiles = mutableSetOf<Tile>()
        connections.forEach{
            if(it.color == this.color) {
                secondStepConnections.addAll(it.connections)
                obsoleteTiles.add(it)
            }
        }
        connections.addAll(secondStepConnections)

        return obsoleteTiles
    }

    fun disappear() {
        connections.forEach{
            it.connections.remove(it)
        }
    }

    override fun toString(): String {
        var s = ""
        connections.forEach { s += "${it.color} " }
        return "t: ($x, $y) $color → $s"
    }
}