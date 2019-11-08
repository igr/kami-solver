package com.oblac.kami.model

/**
 * Capture the history of previous step that comes to current position.
 * We need to store the history so we can print the solution.
 */
class PreviousBoard(val board: Board, val changeClick: Click)