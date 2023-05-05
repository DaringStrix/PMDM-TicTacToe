package com.example.tresenraya.ui.view

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tresenraya.data.Cell
import com.example.tresenraya.data.Player
import com.example.tresenraya.data.Stage

@Composable
fun Content(
    gameStage: String,
    textModifier: Modifier,
    gridModifier: Modifier,
    newcell: SnapshotStateList<Cell>,
    changePlayer: () -> Unit,
    currentPlayer: Player, didSomeoneWon: (Cell) -> Unit,
    stage: Stage,
    softReset: () -> Unit,
    context: Context,
    cellheight: Dp
) {
    Text(
        text = gameStage,
        textModifier
            .background(MaterialTheme.colors.primary).fillMaxWidth(),color= MaterialTheme.colors.background,
        textAlign = TextAlign.Center,
        fontSize = 20.sp
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        gridModifier,
        userScrollEnabled = false,
        content = {
            items(items = newcell) { element ->
                CellView(
                    element,
                    nextPlayer = { changePlayer() },
                    cellheight = cellheight,
                    currentPlayer = currentPlayer,
                    didSomeoneWon = didSomeoneWon,
                    softReset = { softReset() },
                    stage = stage,
                    context = context
                )
            }
        }
    )
}