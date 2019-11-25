package com.oblac.kami

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.abs

class ImageParser {

	fun processImage(imageName: String, visitor: TilesVisitor) {
		val image: BufferedImage = ImageIO.read(File(imageName))
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
				val tile = visitor.visitTile(colorOf(pixel))

				image.setRGB(x, y, pixel xor 0xFFFFFF)
				g.drawString("(${tile.x},${tile.y})", x, y)

				x += deltaX
			}
			y += deltaY
			x = offsetX
			visitor.visitRowEnd()
		}

		ImageIO.write(image, "PNG", File("out.png"))
	}

	private val usedColors = mutableListOf<Int>()

	private fun colorOf(rgb: Int): Int {
		val matchedColor = usedColors.indexOfFirst { isMatchingColor(rgb, it) }
		if (matchedColor > -1) {
			return matchedColor
		}
		usedColors.add(rgb)
		return usedColors.size - 1
	}

	private fun isMatchingColor(intColor1: Int, intColor2: Int, percent: Int = 80): Boolean {
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