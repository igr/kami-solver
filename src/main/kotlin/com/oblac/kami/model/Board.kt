package com.oblac.kami.model

class Board(all: List<Tile>) {

    private val allTiles = all.toSet()
    // replace null with some stupidity
    private var parentBoard: Board? = null

    fun forEach(tileConsumer: (Tile) -> Unit) {
        allTiles.forEach(tileConsumer)
    }

    fun clone(): Board {
        val newBoard = Board(allTiles.toList())
        newBoard.parentBoard = this
        return newBoard
    }

    fun reduce() {
        var tilesToReduce = allTiles.toMutableSet()
        while (tilesToReduce.isNotEmpty()){
            tilesToReduce = tilesToReduce.first().incorporateAllNeighbours(tilesToReduce)
        }
    }
}