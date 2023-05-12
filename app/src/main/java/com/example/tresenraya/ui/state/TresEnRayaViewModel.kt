package com.example.tresenraya.ui.state

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.tresenraya.data.*


class TresEnRayaViewModel(val defaultCellColor: Color) : ViewModel() {
    private val _board = getBoard().apply {
        map {
            it.color = defaultCellColor
        }
    }.toMutableStateList()
    val board: SnapshotStateList<Cell>
        get() = _board

    var currentPlayer by mutableStateOf(Player.X)

    var stage by mutableStateOf(Stage.STOPPED)

    var playerXWins by mutableStateOf(0)
    var playerOWins by mutableStateOf(0)

    fun gameStage(): String{
        return when (stage) {
            Stage.STOPPED -> ""
            Stage.PLAYING -> "Next turn \n $currentPlayer \n"
            Stage.WON -> "$currentPlayer \n WINS \n Press any cell for another round"
            Stage.DRAW -> "No one \n WINS \n Press any cell for another round"
        }
    }

    fun startGame(){
        stage = Stage.PLAYING
    }
    fun flipCurrentPlayer() {
        if (stage != Stage.PLAYING) return
        currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
    }

    fun changePlayer(element: Cell) : Boolean {
        if (stage == Stage.PLAYING) {
            if (element.player == Player.NONE) {
                _board[_board.indexOf(element)] = board[_board.indexOf(element)].copy(player = currentPlayer)
                paintWinningMove(element)
                flipCurrentPlayer()
                return true
            } else {
                return false
            }
        } else {
            softReset(defaultCellColor); flipCurrentPlayer()
            return true
        }
    }

    fun reset() {
        currentPlayer = Player.X
        softReset(defaultCellColor)
        stage = Stage.STOPPED
        playerXWins = 0
        playerOWins = 0
    }

    fun softReset(color: Color) {
        counter()
        _board.forEach {it.player = Player.NONE; it.color = color}
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
                Check.ROW -> allCellsTrue.add(board.find { it.cellRow == checkCell.cellRow && it.cellCol == cell }?.let { it.player == currentPlayer } == true)
                Check.COL -> allCellsTrue.add(board.find { it.cellCol == checkCell.cellCol && it.cellRow == cell }?.let { it.player == currentPlayer } == true)
                Check.DIAGONAL -> allCellsTrue.add(board.find { it.cellCol == cell && it.cellRow == cell }?.let { it.player == currentPlayer } == true)
                Check.INV_DIAGONAL -> allCellsTrue.add(board.find { it.cellCol == 4 - cell && it.cellRow == cell }?.let { it.player == currentPlayer } == true)
            }
        }

        if (allCellsTrue == listOf(true, true, true)) stage = Stage.WON
        else if (stage == Stage.PLAYING && _board.all { it.player != Player.NONE }) stage = Stage.DRAW

        return allCellsTrue == listOf(true, true, true)
    }

    fun paintWinningMove(cell: Cell) {
        _board.forEach {if (checkWinningMove(Check.ROW,cell) && it.cellRow == cell.cellRow) it.color = Color.Red}                     // 3-in-the-row-1
        _board.forEach {if (checkWinningMove(Check.COL,cell) && it.cellCol == cell.cellCol) it.color = Color.Red}                     // 3-in-the-column-1
        _board.forEach {if (checkWinningMove(Check.DIAGONAL,cell) && it.cellRow == it.cellCol) it.color = Color.Red}                   // 3-in-the-diagonal
        _board.forEach {if (checkWinningMove(Check.INV_DIAGONAL,cell) && it.cellCol == 3 - (it.cellRow - 1)) it.color = Color.Red}    // 3-in-the-inverted-diagonal
    }
}