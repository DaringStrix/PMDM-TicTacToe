package com.example.tresenraya.ui.state

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.tresenraya.data.Player

enum class Stage { PLAYING, DRAW, WON }

class TresEnRayaViewModel : ViewModel() {
    private val _board = getBoard().toMutableStateList()
    val board: SnapshotStateList<Cell>
        get() = _board
    val primaryColor = mutableStateOf(Color.White)


    var currentPlayer = mutableStateOf(Player.X)

    val stage = mutableStateOf(Stage.PLAYING)

    var isGameStarted = mutableStateOf(false)

    var player1Wins = mutableStateOf(0)
    var player2Wins = mutableStateOf(0)


    fun flipCurrentPlayer() {
        if (currentPlayer.value == Player.X && stage.value == Stage.PLAYING) currentPlayer.value =
            Player.O else if (stage.value == Stage.PLAYING) currentPlayer.value = Player.X
    }

    fun startGame() {
        isGameStarted.value = true
    }

    fun reset(color: Color) {
        currentPlayer.value = Player.X
        isGameStarted.value = false
        stage.value = Stage.PLAYING
        softReset(color)
        player1Wins.value = 0
        player2Wins.value = 0
    }

    fun softReset(color: Color) {
        counter()
       _board.forEach {
            it.player.value = Player.NONE
            it.color.value = color
        }
        stage.value = Stage.PLAYING

    }

    private fun counter() {
        if (stage.value == Stage.WON) {
            if (currentPlayer.value.toString() == "X") {
                player1Wins.value++
            } else if (currentPlayer.value.toString() == "O") {
                player2Wins.value++
            }
        }
    }

    private fun checkRowsOrCols(checkRows: Boolean, cell: Cell): Boolean {
        val allCellsTrue = ArrayList<Boolean>()
        for (x in 1..3) {
            if (checkRows) allCellsTrue.add(board.find { it.cellRow == cell.cellRow && it.cellCol == x }
                ?.let { it.player.value == currentPlayer.value } == true)
            else allCellsTrue.add(board.find { it.cellCol == cell.cellCol && it.cellRow == x }
                ?.let { it.player.value == currentPlayer.value } == true)
        }
        return allCellsTrue == listOf(true, true, true)
    }

    private fun checkDiagonals(inverted: Boolean): Boolean {
        val allCellsTrue = ArrayList<Boolean>()
        for (x in 0..2) {
            val inversor = 3
            if (!inverted) {
                allCellsTrue.add(board.find { it.cellCol == x+1 && it.cellRow == x+1 }
                    ?.let { it.player.value == currentPlayer.value } == true)
            }else {
                allCellsTrue.add(board.find { it.cellCol == inversor-x && it.cellRow == x+1 }
                    ?.let { it.player.value == currentPlayer.value } == true)
            }
        }
        return allCellsTrue == listOf(true, true, true)
    }

    fun whichPlayerWins(cell: Cell) {
        if (checkRowsOrCols(true, cell)) {       // 3-in-the-row-1
            stage.value = Stage.WON
           _board.forEach { if (it.cellRow == cell.cellRow) it.color.value = Color.Red }
        }
        if (checkRowsOrCols(false, cell)) {      // 3-in-the-column-1
            stage.value = Stage.WON
           _board.forEach { if (it.cellCol == cell.cellCol) it.color.value = Color.Red }
        }
        if (checkDiagonals(false)) {       // 3-in-the-diagonal
            stage.value = Stage.WON
           _board.forEach { if (it.cellRow == it.cellCol) it.color.value = Color.Red }
        }
        if (checkDiagonals(true)) {        // 3-in-the-inverted-diagonal
            stage.value = Stage.WON
            val inversor=3
           _board.forEach {if (it.cellCol == inversor-(it.cellRow-1)) it.color.value = Color.Red }
        }
        if (stage.value == Stage.PLAYING &&_board.all { it.player.value != Player.NONE }) {
            stage.value = Stage.DRAW
        }
    }
}