package com.example.tresenraya.data

import androidx.compose.ui.graphics.Color

data class Cell(val cellRow: Int, val cellCol: Int, var player: Player = Player.NONE, var color: Color = Color.White)

fun getBoard() = listOf(
    Cell(1,1),
    Cell(1,2),
    Cell(1,3),
    Cell(2,1),
    Cell(2,2),
    Cell(2,3),
    Cell(3,1),
    Cell(3,2),
    Cell(3,3)
)