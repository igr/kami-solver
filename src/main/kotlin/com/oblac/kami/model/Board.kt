package com.oblac.kami.model

class Board(all: List<Tile>) {

    private val allTiles = all.toSet()
    private var parentBoard: Board? = null

    fun forEach(tileConsumer: (Tile) -> Unit) {
        allTiles.forEach(tileConsumer)
    }

    fun clone(): Board {
        var newBoard = Board(allTiles.toList())
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