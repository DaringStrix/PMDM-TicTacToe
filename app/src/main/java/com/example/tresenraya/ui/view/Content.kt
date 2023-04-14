package com.example.tresenraya

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tresenraya.data.Player
import com.example.tresenraya.ui.state.Cell
import com.example.tresenraya.ui.state.Stage
import com.example.tresenraya.ui.view.CellView

@Composable
fun Content(
    paddingValues: PaddingValues,
    newcell: SnapshotStateList<Cell>,
    changePlayer: () -> Unit,
    currentPlayer: Player,
    isGameStarted: Boolean,
    startGame: () -> Unit,
    didSomeoneWon: (Cell) -> Unit,
    stage: MutableState<Stage>,
    softReset: () -> Unit
) {

    Column(
        Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        val gameStage = when (stage.value) {
            Stage.PLAYING -> "Next turn \n $currentPlayer \n"
            Stage.WON -> "$currentPlayer \n WINS \n Press any cell for another round"
            Stage.DRAW -> "No one \n WINS \n Press any cell for another round"
        }

        if (isGameStarted) {
            Text(
                text = gameStage,
                Modifier.padding(bottom = 50.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            LazyVerticalGrid(
                columns = GridCells.Adaptive(100.dp),
                Modifier.padding(horizontal = 50.dp),
                userScrollEnabled = false,
                content = {
                    items(items = newcell) { element ->
                        CellView(
                            element,
                            nextPlayer = { changePlayer() },
                            currentPlayer = currentPlayer,
                            didSomeoneWon = didSomeoneWon,
                            softReset= { softReset() },
                            stage = stage
                        )
                    }
                }
            )
        } else {
            Button(onClick = startGame) {
                Card(
                    backgroundColor = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(4.dp)
                        .fillMaxWidth(),
                    elevation = 8.dp
                ) {
                    Text(
                        text = "Start Game", fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

