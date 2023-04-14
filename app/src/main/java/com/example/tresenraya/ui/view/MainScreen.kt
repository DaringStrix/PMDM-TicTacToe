package com.example.tresenraya

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tresenraya.ui.state.TresEnRayaViewModel

@Composable
fun MainScreen() {
    val viewModel: TresEnRayaViewModel = viewModel()
    val primary = MaterialTheme.colors.primary
    viewModel.board.forEach { it.color.value = primary }
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = {
            TopAppBar(title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.Titulo), Modifier.weight(1F))
                    IconButton(onClick = { viewModel.reset(primary) }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = stringResource(R.string.Refresh)
                        )
                    }
                }
            })
        }, bottomBar = {
            BottomAppBar() {
                if (viewModel.isGameStarted.value) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "X won = ${viewModel.player1Wins.value} time(s)", Modifier.weight(1F),
                                fontSize = 20.sp
                            )
                            Text(
                                "0 won = ${viewModel.player2Wins.value} time(s)",
                                fontSize = 20.sp
                            )

                    }
                }
            }
        }
    ) { paddingValues ->
        Content(
            paddingValues = paddingValues,
            newcell = viewModel.board,
            changePlayer = { viewModel.flipCurrentPlayer() },
            currentPlayer = viewModel.currentPlayer.value,
            isGameStarted = viewModel.isGameStarted.value,
            startGame = { viewModel.startGame() },
            didSomeoneWon = { viewModel.whichPlayerWins(it) },
            stage = viewModel.stage

        ) { viewModel.softReset(primary) }
    }
}
