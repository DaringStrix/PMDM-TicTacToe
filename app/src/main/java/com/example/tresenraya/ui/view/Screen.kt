package com.example.tresenraya.ui.view

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tresenraya.R
import com.example.tresenraya.data.Stage
import com.example.tresenraya.ui.state.MyViewModelFactory
import com.example.tresenraya.ui.state.TresEnRayaViewModel
import com.example.tresenraya.ui.theme.TresEnRayaTheme

@Composable
fun Screen() {
    val viewModel: TresEnRayaViewModel =
        viewModel(factory = MyViewModelFactory(MaterialTheme.colors.primary))
    val configuration = LocalConfiguration.current.orientation

    val context = LocalContext.current
    TresEnRayaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                scaffoldState = rememberScaffoldState(),
                topBar = {
                    if (configuration != ORIENTATION_LANDSCAPE && viewModel.stage != Stage.STOPPED) {
                        TopAppBar(title = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    viewModel.gameStage(),
                                    Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        },Modifier.height(100.dp))
                    }
                },
                floatingActionButtonPosition = FabPosition.Center,
                isFloatingActionButtonDocked = true,
                floatingActionButton = {
                    if (viewModel.stage != Stage.STOPPED) {
                        FloatingActionButton(
                            onClick = { viewModel.reset() }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = stringResource(R.string.Refresh)
                            )
                        }
                    }
                },
                bottomBar = {
                    if (viewModel.stage != Stage.STOPPED) {
                        BottomAppBar() {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    "X won = ${viewModel.playerXWins} time(s)",
                                    Modifier.weight(1F),
                                    fontSize = 20.sp
                                )

                                Text(
                                    "0 won = ${viewModel.playerOWins} time(s)",
                                    fontSize = 20.sp
                                )

                            }
                        }
                    }
                }
            ) { paddingValues ->
                Layout(
                    paddingValues = paddingValues,
                    board = viewModel.board,
                    onClick = { cell ->
                        if (!viewModel.changePlayer(cell))
                            Toast.makeText(context, "Casilla llena", Toast.LENGTH_SHORT).show()
                    },
                    stage = viewModel.stage,
                    startGame= {viewModel.startGame()},
                    orientation = configuration,
                    gameStage = viewModel.gameStage()
                )
            }
        }
    }
}
