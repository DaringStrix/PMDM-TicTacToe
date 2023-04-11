package com.example.tresenraya

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.tresenraya.model.Cell
import com.example.tresenraya.model.Player
import com.example.tresenraya.model.getBoard

class TresEnRayaViewModel : ViewModel() {
    private val _boardCell = getBoard().toMutableStateList()
    val boardCell: SnapshotStateList<Cell>
        get() = _boardCell
    var currentPlayer = mutableStateOf(Player.X)
    var isGameStarted = mutableStateOf(false)

    fun changePlayer(cellId: Int) {
        if (isGameStarted.value) boardCell[cellId].player = currentPlayer.value.toString()
        if (currentPlayer.value == Player.X) currentPlayer.value = Player.O else currentPlayer.value = Player.X
    }

    fun startGame() {
        isGameStarted.value = true
    }

}