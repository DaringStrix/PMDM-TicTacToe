package com.example.tresenraya.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.tresenraya.data.*


class TresEnRayaViewModel(val defaultCellColor: Color) : ViewModel() {
    private val _board = getBoard().toMutableStateList()
    val board: SnapshotStateList<Cell>
        get() = _board

    var currentPlayer by mutableStateOf(Player.X)

    var stage by mutableStateOf(Stage.PLAYING)

    var isGameStarted by mutableStateOf(false)

    var player1Wins by mutableStateOf(0)
    var player2Wins by mutableStateOf(0)


    fun flipCurrentPlayer() {
        if (currentPlayer == Player.X && stage == Stage.PLAYING) currentPlayer =
            Player.O else if (stage == Stage.PLAYING) currentPlayer = Player.X
    }

    fun startGame() {
        isGameStarted = true
    }

    fun reset(color: Color) {
        currentPlayer = Player.X
        isGameStarted = false
        stage = Stage.PLAYING
        softReset(color)
        player1Wins = 0
        player2Wins = 0
    }

    fun softReset(color: Color) {
        counter()
        _board.forEach {
            it.player = Player.NONE
            it.color = color
        }
        stage = Stage.PLAYING

    }

    private fun counter() {
        if (stage == Stage.WON) {
            if (currentPlayer.toString() == "X") {
                player1Wins++
            } else if (currentPlayer.toString() == "O") {
                player2Wins++
            }
        }
    }

    private fun checkRowsOrCols(check: Check, cell: Cell): Boolean {
        val allCellsTrue = ArrayList<Boolean>()
        if (check == Check.ROW || check == Check.COL) {
            for (x in 1..3) {
                if (check == Check.ROW) allCellsTrue.add(board.find { it.cellRow == cell.cellRow && it.cellCol == x }
                    ?.let { it.player == currentPlayer } == true)
                else allCellsTrue.add(board.find { it.cellCol == cell.cellCol && it.cellRow == x }
                    ?.let { it.player == currentPlayer } == true)
            }
        } else {
            for (x in 0..2) {
                val inversor = 3
                if (check==Check.DIAGONAL) {
                    allCellsTrue.add(board.find { it.cellCol == x + 1 && it.cellRow == x + 1 }
                        ?.let { it.player == currentPlayer } == true)
                } else {
                    allCellsTrue.add(board.find { it.cellCol == inversor - x && it.cellRow == x + 1 }
                        ?.let { it.player == currentPlayer } == true)
                }
            }
        }
        return allCellsTrue == listOf(true, true, true)
    }

    fun whichPlayerWins(cell: Cell) {
        if (checkRowsOrCols(Check.ROW, cell)) {       // 3-in-the-row-1
            stage = Stage.WON
            _board.forEach { if (it.cellRow == cell.cellRow) it.color = Color.Red }
        }
        if (checkRowsOrCols(Check.COL, cell)) {      // 3-in-the-column-1
            stage = Stage.WON
            _board.forEach { if (it.cellCol == cell.cellCol) it.color = Color.Red }
        }
        if (checkRowsOrCols(Check.DIAGONAL, cell)) {       // 3-in-the-diagonal
            stage = Stage.WON
            _board.forEach { if (it.cellRow == it.cellCol) it.color = Color.Red }
        }
        if (checkRowsOrCols(Check.INV_DIAGONAL, cell)) {        // 3-in-the-inverted-diagonal
            stage = Stage.WON
            val inversor = 3
            _board.forEach { if (it.cellCol == inversor - (it.cellRow - 1)) it.color = Color.Red }
        }
        if (stage == Stage.PLAYING && _board.all { it.player != Player.NONE }) {
            stage = Stage.DRAW
        }
    }
}