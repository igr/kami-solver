package com.oblac.kami

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.abs
import kotlin.math.sqrt

class ImageParser {

	private val usedRgbColors = mutableListOf<Int>()

	init {
		usedRgbColors.add(0xffffff)
	}

	fun processImage(imageFile: File, visitor: TilesVisitor) {
		val image: BufferedImage = ImageIO.read(imageFile)

		collectAllColors(image)
		println("Total colors: ${usedRgbColors.size - 1}")

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
				val color = identifyColor(pixel, x, y)

				if (color == 0) {
					visitor.visitEmptyTile()
				} else {
					// tile exist
					val tile = visitor.visitTile(color)

					image.setRGB(x, y, pixel xor 0xFFFFFF)

					val str = "(${tile.x},${tile.y}):${tile.color}"
					val fm = g.fontMetrics
					val rect = fm.getStringBounds(str, g)
					g.color = Color.BLACK
					g.fillRect(x, y - fm.ascent, rect.width.toInt(), rect.height.toInt())
					g.color = Color.WHITE
					g.drawString(str, x, y)
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
	 * Tries it best to identify a color.
	 */
	private fun identifyColor(rgb: Int, x: Int, y: Int): Int {
		for (threshold in 75 downTo 50 step 5) {
			val matchedColor = usedRgbColors.indexOfFirst { isMatchingColor(rgb, it, threshold) }
			if (matchedColor > -1) {
				return matchedColor
			}
		}
		throw Exception("Color not detected at $x,$y")
	}

	private fun collectAllColors(image: BufferedImage) {
		val deltaX = 17
		val y = 2100
		var x = image.width - deltaX

		while (x > 350) {
			val pixel = image.getRGB(x, y)
			image.graphics.drawString("x", x, y)

			val matchedColor = usedRgbColors.indexOfFirst { isMatchingColor(pixel, it, 75) }
			if (matchedColor == -1) {
				usedRgbColors.add(pixel)
			}

			x -= deltaX
		}
	}

	// Not all tiles of the same color are actually colored the same:
	// the game has some 'brightness' overlay so top tiles are more lighter
	private fun isMatchingColor(intColor1: Int, intColor2: Int, percent: Int = 75): Boolean {
		val threshold = 255 - (255 / 100f * percent)
		val color1 = Color(intColor1)
		val color2 = Color(intColor2)

		//val diffAlpha = abs(color1.alpha - color2.alpha)
		val diffRed = abs(color1.red - color2.red)
		val diffGreen = abs(color1.green - color2.green)
		val diffBlue = abs(color1.blue - color2.blue)

		val distance = sqrt((diffRed * diffRed + diffGreen * diffGreen + diffBlue * diffBlue).toDouble())

		return distance < threshold
	}
}