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
        val tilesToReduce = allTiles.toMutableSet()
        while (tilesToReduce.isNotEmpty()){
            val currentTile = tilesToReduce.first()
            val obsoleteTiles = currentTile.uniteSameColorNeighbours()
            obsoleteTiles.forEach {
                it.disappear()
            }

            tilesToReduce.removeAll(obsoleteTiles)
            tilesToReduce.remove(currentTile)
        }
    }
}