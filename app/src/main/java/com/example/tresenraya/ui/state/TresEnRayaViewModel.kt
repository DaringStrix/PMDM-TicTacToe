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

    var currentPlayer = mutableStateOf(Player.X)
//    var winner = mutableStateOf(Player.NONE)

    val stage = mutableStateOf(
        Stage.PLAYING
    )

    var isGameStarted = mutableStateOf(false)

    var player1Wins = mutableStateOf(0)
    var player2Wins = mutableStateOf(0)


    fun flipCurrentPlayer() {
        if (currentPlayer.value == Player.X) currentPlayer.value =
            Player.O else currentPlayer.value = Player.X
    }

    fun startGame() {
        isGameStarted.value = true
    }

    fun reset(color: Color) {
        currentPlayer.value = Player.X
        isGameStarted.value = false
        stage.value = Stage.PLAYING
        softReset(color)
    }

    fun softReset(color: Color) {
        counter()
        board.forEach {
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

    fun whichPlayerWins(cell: Cell) {
        if (board.find { it.cellRow == cell.cellRow && it.cellCol == 1 }
                ?.let { it.player.value == currentPlayer.value } == true      // 3-in-the-row-1
            && board.find { it.cellRow == cell.cellRow && it.cellCol == 2 }
                ?.let { it.player.value == currentPlayer.value } == true
            && board.find { it.cellRow == cell.cellRow && it.cellCol == 3 }
                ?.let { it.player.value == currentPlayer.value } == true
        ) {
            stage.value = Stage.WON
            board.find { it.cellRow == cell.cellRow && it.cellCol == 1 }
                ?.let { it.color.value = Color.Red }
            board.find { it.cellRow == cell.cellRow && it.cellCol == 2 }
                ?.let { it.color.value = Color.Red }
            board.find { it.cellRow == cell.cellRow && it.cellCol == 3 }
                ?.let { it.color.value = Color.Red }

        } else if (board.find { it.cellCol == cell.cellCol && it.cellRow == 1 }
                ?.let { it.player.value == currentPlayer.value } == true      // 3-in-the-column-1
            && board.find { it.cellCol == cell.cellCol && it.cellRow == 2 }
                ?.let { it.player.value == currentPlayer.value } == true
            && board.find { it.cellCol == cell.cellCol && it.cellRow == 3 }
                ?.let { it.player.value == currentPlayer.value } == true
        ) {
            stage.value = Stage.WON
            board.find { it.cellCol == cell.cellCol && it.cellRow == 1 }
                ?.let { it.color.value = Color.Red }
            board.find { it.cellCol == cell.cellCol && it.cellRow == 2 }
                ?.let { it.color.value = Color.Red }
            board.find { it.cellCol == cell.cellCol && it.cellRow == 3 }
                ?.let { it.color.value = Color.Red }

        } else if (
            board.find { it.cellCol == 1 && it.cellRow == 1 }
                ?.let { it.player.value == currentPlayer.value } == true     // 3-in-the-diagonal
            && board.find { it.cellCol == 2 && it.cellRow == 2 }
                ?.let { it.player.value == currentPlayer.value } == true
            && board.find { it.cellCol == 3 && it.cellRow == 3 }
                ?.let { it.player.value == currentPlayer.value } == true
        ) {
            stage.value = Stage.WON
            board.find { it.cellCol == 1 && it.cellRow == 1 }?.let { it.color.value = Color.Red }
            board.find { it.cellCol == 2 && it.cellRow == 2 }?.let { it.color.value = Color.Red }
            board.find { it.cellCol == 3 && it.cellRow == 3 }?.let { it.color.value = Color.Red }

        } else if (
            board.find { it.cellRow + it.cellCol == 4 }.let { true }
            && board.find { it.cellCol == 1 && it.cellRow == 3 }
                ?.let { it.player.value == currentPlayer.value } == true       // 3-in-the-opposite-diagonal
            && board.find { it.cellCol == 2 && it.cellRow == 2 }
                ?.let { it.player.value == currentPlayer.value } == true
            && board.find { it.cellCol == 3 && it.cellRow == 1 }
                ?.let { it.player.value == currentPlayer.value } == true
        ) {
            stage.value = Stage.WON
            board.find { it.cellCol == 1 && it.cellRow == 3 }?.let { it.color.value = Color.Red }
            board.find { it.cellCol == 2 && it.cellRow == 2 }?.let { it.color.value = Color.Red }
            board.find { it.cellCol == 3 && it.cellRow == 1 }?.let { it.color.value = Color.Red }
        } else if (board.all { it.player.value != Player.NONE }) {
            stage.value = Stage.DRAW
        }else{
            flipCurrentPlayer()
        }

    }
}