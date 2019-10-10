package com.oblac.kami

fun main() {

	val tilesVisitor = TilesVisitor()

	ImageParser().processImage("kami.png", tilesVisitor)

//    tilesVisitor.forEach { println(it) }

	val board = tilesVisitor.toBoard()

	val board2 = board.reduce()

	board2.forEach { println(it) }
}
