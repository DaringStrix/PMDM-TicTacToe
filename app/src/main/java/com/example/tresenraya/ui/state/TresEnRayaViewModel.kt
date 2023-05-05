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

    var playerXWins by mutableStateOf(0)
    var playerOWins by mutableStateOf(0)


    fun flipCurrentPlayer() {
        if (stage != Stage.PLAYING) return
        currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
    }

    fun startGame() {
        isGameStarted = true
    }

    fun reset(color: Color) {
        currentPlayer = Player.X
        isGameStarted = false
        stage = Stage.PLAYING
        softReset(color)
        playerXWins = 0
        playerOWins = 0
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
        if (stage != Stage.WON) return
        if (currentPlayer == Player.X) playerXWins++ else playerOWins++
    }

    private fun checkWinningMove(check: Check, checkCell: Cell): Boolean {
        val allCellsTrue = ArrayList<Boolean>()
        for (cell in 1..3) {
            when (check) {
                Check.ROW -> allCellsTrue.add(board.find { it.cellRow == checkCell.cellRow && it.cellCol == cell }
                    ?.let { it.player == currentPlayer } == true)
                Check.COL -> allCellsTrue.add(board.find { it.cellCol == checkCell.cellCol && it.cellRow == cell }
                    ?.let { it.player == currentPlayer } == true)
                Check.DIAGONAL -> allCellsTrue.add(board.find { it.cellCol == cell && it.cellRow == cell }
                        ?.let { it.player == currentPlayer } == true)
                Check.INV_DIAGONAL -> allCellsTrue.add(board.find { it.cellCol == 4 - cell && it.cellRow == cell }
                        ?.let { it.player == currentPlayer } == true)
            }
        }

        if (allCellsTrue == listOf(true, true, true)) stage = Stage.WON else if (stage == Stage.PLAYING && _board.all { it.player != Player.NONE }) stage = Stage.DRAW

        return allCellsTrue == listOf(true, true, true)
    }

    fun paintWinningMove(cell: Cell) {
        _board.forEach { if (checkWinningMove(Check.ROW, cell) && it.cellRow == cell.cellRow) it.color = Color.Red}                     // 3-in-the-row-1
        _board.forEach { if (checkWinningMove(Check.COL, cell) && it.cellCol == cell.cellCol) it.color = Color.Red}                     // 3-in-the-column-1
        _board.forEach { if (checkWinningMove(Check.DIAGONAL,cell) && it.cellRow == it.cellCol) it.color = Color.Red}                   // 3-in-the-diagonal
        _board.forEach { if (checkWinningMove(Check.INV_DIAGONAL, cell) && it.cellCol == 3 - (it.cellRow - 1)) it.color = Color.Red}    // 3-in-the-inverted-diagonal
    }
}