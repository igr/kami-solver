package com.oblac.kami.load

import com.oblac.kami.Puzzle
import com.oblac.kami.model.Board
import java.io.File

/**
 * Loads a Board from the screenshot file.
 */
fun loadBoardFromScreenshot(puzzle: Puzzle): Board {
	val tilesVisitor = TilesVisitor()

	val imageName = "puzzles/kami${puzzle.screenshotIndex}"
	val pngImageName = "${imageName}.png"
	val jpegImageName = "${imageName}.jpeg"

	val imageFile =
		if (File(pngImageName).exists()) {
			File(pngImageName)
		} else {
			File(jpegImageName)
		}

	ImageParser(puzzle.limits).processImage(imageFile, tilesVisitor)

	val loadedTiles = tilesVisitor.loadedTiles()

	return Board(loadedTiles)
}
