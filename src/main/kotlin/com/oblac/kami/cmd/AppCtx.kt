package com.oblac.kami.cmd

import com.oblac.kami.ImageParser
import com.oblac.kami.Solver

/**
 * Application context.
 */
object AppCtx {

	val solver = {
		Solver(clicker, reducer, ImageParser())
	}

	private val reducer = Reducer()

	private val cloner = Cloner()

	private val clicker = Clicker(cloner, reducer)
}