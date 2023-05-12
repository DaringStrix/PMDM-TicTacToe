package com.example.tresenraya.ui.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.sp
import com.example.tresenraya.data.Cell

@Composable
fun Content(
    gameStage: String,
    textModifier: Modifier,
    gridModifier: Modifier,
    newcell: SnapshotStateList<Cell>,
    onClick: (Cell) -> Unit,
    cellheight: Dp,
    orientation: Int,
) {
    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Text(
            text = gameStage,
            textModifier
                .background(MaterialTheme.colors.primary)
                .fillMaxWidth(), color = MaterialTheme.colors.background,
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        gridModifier,
        userScrollEnabled = false,
        content = {
            items(items = newcell) { element ->
                CellView(
                    element,
                    onClick = { cell -> onClick(cell) },
                    cellheight = cellheight,
                )
            }
        }
    )
}