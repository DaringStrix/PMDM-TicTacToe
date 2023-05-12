package com.example.tresenraya.ui.view

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tresenraya.data.Cell
import com.example.tresenraya.data.Stage

@Composable
fun Layout(
    paddingValues: PaddingValues,
    board: SnapshotStateList<Cell>,
    onClick: (Cell) -> Unit,
    stage: Stage,
    orientation: Int,
    startGame: () -> Unit,
    gameStage: String
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        if (stage != Stage.STOPPED) {
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Content(
                    gameStage = gameStage,
                    textModifier = Modifier
                        .weight(0.55F),
                    gridModifier = Modifier
                        .weight(0.45F),
                    newcell = board,
                    onClick = { cell -> onClick(cell) },
                    cellheight = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 100.dp else 140.dp,
                    orientation = orientation
                )
            }

        } else {
            Row(Modifier.weight(0.4F).padding(top = 25.dp, bottom = 25.dp)) {
                Text(
                    text = "TIC TAC TOE",
                    Modifier
                        .background(MaterialTheme.colors.background)
                        .fillMaxWidth().padding(top = 100.dp)
                        .border(BorderStroke(5.dp, MaterialTheme.colors.primary)),
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 50.sp,
                    textAlign = TextAlign.Center,

                    )
            }
            Row(Modifier.weight(0.6F)) {
                Card(
                    backgroundColor = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(4.dp)
                        .fillMaxWidth()
                        .clickable { startGame() },
                    elevation = 8.dp
                ) {
                    Text(
                        text = "Start Game",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

