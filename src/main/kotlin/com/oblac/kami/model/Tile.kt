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

    // rename to unite
    fun incorporateAllNeighbours(tilesToReduce: MutableSet<Tile>): MutableSet<Tile> {
        var secondStepConnections = mutableSetOf<Tile>()
        var disappearingTiles = mutableSetOf<Tile>()
        connections.forEach{
            if(it.color == this.color) {
                secondStepConnections.addAll(it.connections)
                tilesToReduce.remove(it)
                it.disappear()
            }
        }
        connections.addAll(secondStepConnections)

        tilesToReduce.remove(this)
        return tilesToReduce
    }

    private fun disappear() {
        connections.forEach{
            it.connections.remove(it)
        }
    }

    override fun toString(): String {
        var s = ""
        connections.forEach { s += "${it.color} " }
        return "t: ($x, $y) $color → $s";
    }
}