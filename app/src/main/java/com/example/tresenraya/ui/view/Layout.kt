package com.example.tresenraya.ui.view

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
fun Layout(
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
    context: Context,

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
                        newcell = newcell,
                        changePlayer= changePlayer,
                        currentPlayer = currentPlayer,
                        didSomeoneWon = didSomeoneWon,
                        stage = stage,
                        softReset = softReset,
                        context = context,
                        cellheight = 100.dp
                    )
                }
            } else {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Content(
                        gameStage = gameStage,
                        textModifier = Modifier,
                        gridModifier = Modifier
                            .padding(vertical = 40.dp).fillMaxWidth(),
                        newcell = newcell,
                        changePlayer= changePlayer,
                        currentPlayer = currentPlayer,
                        didSomeoneWon = didSomeoneWon,
                        stage = stage,
                        softReset = softReset,
                        context = context,
                        cellheight = 140.dp
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

