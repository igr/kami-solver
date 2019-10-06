package com.oblac.kami.model

class Board(all: List<Tile>) {

    private val allTiles = all.toSet()

    fun forEach(tileConsumer: (Tile) -> Unit) {
        allTiles.forEach(tileConsumer)
    }
}