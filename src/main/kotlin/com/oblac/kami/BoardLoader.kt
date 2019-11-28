package com.oblac.kami

import com.oblac.kami.model.Board
import java.io.File

/**
 * Loads a Board from the screenshot file.
 */
fun loadBoardFromScreenshot(puzzleScreenshotsIndex: Int): Board {
	val tilesVisitor = TilesVisitor()

	val imageName = "puzzles/kami${puzzleScreenshotsIndex}"
	val pngImageName = "${imageName}.png"
	val jpegImageName = "${imageName}.jpeg"

	val imageFile =
		if (File(pngImageName).exists()) {
			File(pngImageName)
		} else {
			File(jpegImageName)
		}

	ImageParser().processImage(imageFile, tilesVisitor)

	val loadedTiles = tilesVisitor.loadedTiles()

	return Board(loadedTiles)
}
