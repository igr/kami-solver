package com.oblac.kami

fun main() {

    val tilesVisitor = TilesVisitor()

    ImageParser().processImage("kami.png", tilesVisitor)

    tilesVisitor.toBoard().forEach { println(it) }
}
