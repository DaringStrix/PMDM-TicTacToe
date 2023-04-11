package com.example.tresenraya.model

class Cell(val cellId: Int) {
    var player = ""
}

fun getBoard() = listOf(
    Cell(1),
    Cell(2),
    Cell(3),
    Cell(4),
    Cell(5),
    Cell(6),
    Cell(7),
    Cell(8),
    Cell(9)
)