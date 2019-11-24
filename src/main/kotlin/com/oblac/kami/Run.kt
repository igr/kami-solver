package com.oblac.kami

import com.oblac.kami.cmd.AppCtx
import kotlin.system.measureTimeMillis

fun main() {
	
	val elapsed = measureTimeMillis {
		AppCtx.solver().solvePuzzle(1, 4)
	}

	println("Done in ${elapsed}ms")
}
