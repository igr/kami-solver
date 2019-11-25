package com.oblac.kami

import com.oblac.kami.model.Board

class BoardLoader {

	/**
	 * Loads board from the screnshot file.
	 */
	fun loadBoardFromScreenshot(puzzleScreenshotsIndex: Int): Board {
		val tilesVisitor = TilesVisitor()

		ImageParser().processImage("puzzles/kami${puzzleScreenshotsIndex}.png", tilesVisitor)

		val loadedTiles = tilesVisitor.loadedTiles()

		return Board(loadedTiles)
	}
}