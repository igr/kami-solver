package com.oblac.kami.cmd

/**
 * Application context.
 */
object Cmd {

	val reducer = Reducer()

	val cloner = Cloner()

	val clicker = Clicker(cloner, reducer)
}