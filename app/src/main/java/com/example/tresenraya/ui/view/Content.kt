package com.example.tresenraya.ui.view

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tresenraya.data.Cell
import com.example.tresenraya.data.Player
import com.example.tresenraya.data.Stage

@Composable
fun Content(
    paddingValues: PaddingValues,
    newcell: SnapshotStateList<Cell>,
    changePlayer: () -> Unit,
    currentPlayer: Player,
    isGameStarted: Boolean,
    startGame: () -> Unit,
    didSomeoneWon: (Cell) -> Unit,
    stage: Stage,
    softReset: () -> Unit,
    orientation: Int,
    context: Context
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        val gameStage = when (stage) {
            Stage.PLAYING -> "Next turn \n $currentPlayer \n"
            Stage.WON -> "$currentPlayer \n WINS \n Press any cell for another round"
            Stage.DRAW -> "No one \n WINS \n Press any cell for another round"
        }

        if (isGameStarted) {
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = gameStage,
                        Modifier.weight(0.5F),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        Modifier
                            .padding(horizontal = 50.dp)
                            .width(240.dp)
                            .weight(0.5F),
                        userScrollEnabled = false,
                        content = {
                            items(items = newcell) { element ->
                                CellView(
                                    element,
                                    nextPlayer = { changePlayer() },
                                    cellheight = 75.dp,
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
            } else {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = gameStage,
                        Modifier.padding(bottom = 50.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        Modifier
                            .padding(horizontal = 50.dp)
                            .width(300.dp),
                        userScrollEnabled = false,
                        content = {
                            items(items = newcell) { element ->
                                CellView(
                                    element,
                                    nextPlayer = { changePlayer() },
                                    cellheight = 100.dp,
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
            }

        } else {
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
                    text = "Start Game", fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

