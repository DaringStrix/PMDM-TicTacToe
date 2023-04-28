package com.example.tresenraya.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tresenraya.R
import com.example.tresenraya.ui.state.TresEnRayaViewModel
import com.example.tresenraya.ui.theme.TresEnRayaTheme

class MyViewModelFactory(private val defaultCellColor: Color) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = TresEnRayaViewModel(defaultCellColor) as T
// https://stackoverflow.com/questions/67982230/jetpack-compose-pass-parameter-to-viewmodel
}
@Composable
fun Screen() {
    val viewModel: TresEnRayaViewModel = viewModel(factory = MyViewModelFactory(MaterialTheme.colors.primary))
    val configuration = LocalConfiguration.current.orientation

    viewModel.board.forEach { it.color = viewModel.defaultCellColor }
    TresEnRayaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {

            Scaffold(
                scaffoldState = rememberScaffoldState(),
                topBar = {
                    TopAppBar(title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                stringResource(R.string.Titulo),
                                Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    })
                },
                floatingActionButton = {
                    if (viewModel.isGameStarted) {
                        FloatingActionButton(
                            onClick = { viewModel.reset(viewModel.defaultCellColor) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = stringResource(R.string.Refresh)
                            )
                        }
                    }
                },
                bottomBar = {
                    BottomAppBar() {
                        if (viewModel.isGameStarted) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    "X won = ${viewModel.player1Wins} time(s)",
                                    Modifier.weight(1F),
                                    fontSize = 20.sp
                                )

                                Text(
                                    "0 won = ${viewModel.player2Wins} time(s)",
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
                    currentPlayer = viewModel.currentPlayer,
                    isGameStarted = viewModel.isGameStarted,
                    startGame = { viewModel.startGame() },
                    didSomeoneWon = { viewModel.whichPlayerWins(it) },
                    stage = viewModel.stage,
                    softReset = { viewModel.softReset(viewModel.defaultCellColor) },
                    orientation = configuration,
                    context = LocalContext.current
                )
            }
        }
    }
}
