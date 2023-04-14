package com.example.tresenraya.ui.state

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.example.tresenraya.data.Player

class Cell(val cellRow: Int, val cellCol: Int) {
    var player = mutableStateOf(Player.NONE)
    var color = mutableStateOf(Color.White)
}

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