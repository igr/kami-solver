package com.oblac.kami.load

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.abs

/**
 * Dirty and UGLY image parser, just good enough.
 * Some screenshots have to be manually fixed before running.
 */
class ImageParser(private val limits: Pair<Int, Int>) {

	private val usedColors = mutableListOf<Int>()

	fun processImage(imageFile: File, visitor: TilesVisitor) {
		val image: BufferedImage = ImageIO.read(imageFile)

		val g = image.graphics
		val w = image.width
		val h = image.height

		val offsetX = 57
		val deltaX = offsetX * 2
		val deltaY = 65
		val offsetY = 128
		val maxY = h - 450

		var x = offsetX;
		var y = offsetY;

		while (y < maxY) {
			while (x < w) {
				val pixel = image.getRGB(x, y)
				val colorIndex = colorOf(pixel)

				if (visitor.x <= limits.first && visitor.y <= limits.second) {
					var weight = calculateTileWeight(visitor)

					val tile = visitor.visitTile(colorIndex, weight)

					image.setRGB(x, y, pixel xor 0xFFFFFF)

					val str = "(${tile.x},${tile.y})${tile.weight}/${tile.color}"
					val fm = g.fontMetrics
					val rect = fm.getStringBounds(str, g)
					g.color = Color.BLACK
					g.fillRect(x - 10, y - fm.ascent, rect.width.toInt(), rect.height.toInt())
					g.color = Color.WHITE
					g.drawString(str, x - 10, y)
				}

				x += deltaX
			}
			y += deltaY
			x = offsetX
			visitor.visitRowEnd()
		}

		ImageIO.write(image, "PNG", File("out.png"))
	}

	/**
	 * Calculates the weight of the tile. The weight is used
	 * on symmetrical boards.
	 */
	private fun calculateTileWeight(visitor: TilesVisitor): Int {
		var weight = 1
		if (limits.first != Int.MAX_VALUE) {
			// we have X limit
			if (visitor.x < limits.first) {
				weight *= 2
			} else if (visitor.y % 2 == 0) {
				// not ALL edges are symmetrical!
				weight *= 2
			}
		}
		if (limits.second != Int.MAX_VALUE) {
			// we have Y limit
			if (visitor.y < limits.second) {
				weight *= 2
			}
		}
		if (visitor.x == limits.first && visitor.y == limits.second) {
			// special case! the corner!
			weight = 1
		}
		return weight
	}

	private fun colorOf(rgb: Int): Int {
		val matchedColor = usedColors.indexOfFirst { isMatchingColor(rgb, it) }
		if (matchedColor > -1) {
			return matchedColor
		}
		usedColors.add(rgb)
		return usedColors.size - 1
	}

	// Not all tiles of the same color are actually colored the same:
	// the game has some 'brightness' overlay so top tiles are more lighter
	private fun isMatchingColor(intColor1: Int, intColor2: Int, percent: Int = 75): Boolean {
		val threadSold = 255 - (255 / 100f * percent)
		val color1 = Color(intColor1)
		val color2 = Color(intColor2)

		val diffAlpha = abs(color1.alpha - color2.alpha)
		val diffRed = abs(color1.red - color2.red)
		val diffGreen = abs(color1.green - color2.green)
		val diffBlue = abs(color1.blue - color2.blue)

		if (diffAlpha > threadSold) {
			return false
		}
		if (diffRed > threadSold) {
			return false
		}
		if (diffGreen > threadSold) {
			return false
		}
		if (diffBlue > threadSold) {
			return false
		}

		return true
	}
}