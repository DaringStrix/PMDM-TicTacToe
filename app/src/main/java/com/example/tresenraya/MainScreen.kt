package com.example.tresenraya

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen() {
    val viewModel: TresEnRayaViewModel = viewModel()
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = {
            TopAppBar(title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.Titulo), Modifier.weight(1F))
                }
            })
        },
    ) { paddingValues ->
        Content(
            paddingValues = paddingValues,
            newcell = viewModel.boardCell,
            changePlayer = { viewModel.changePlayer(it) },
            currentPlayer = viewModel.currentPlayer.value.toString(),
            isGameStarted = viewModel.isGameStarted.value,
            startGame= { viewModel.startGame() }
        )
    }

}
