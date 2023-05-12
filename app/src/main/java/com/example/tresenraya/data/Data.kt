package com.example.tresenraya.data;

enum class Player(private val displayName: String) {
    X("X") ,
    O("O") ,
    NONE("");
    override fun toString() = displayName
}

enum class Stage {STOPPED, PLAYING, DRAW, WON}
enum class Check { ROW, COL, DIAGONAL, INV_DIAGONAL }

