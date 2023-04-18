package com.example.tresenraya.ui.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tresenraya.data.Player
import com.example.tresenraya.ui.state.Cell
import com.example.tresenraya.ui.state.Stage

@Composable
fun CellView(
    element: Cell,
    nextPlayer: () -> Unit,
    currentPlayer: Player,
    didSomeoneWon: (Cell) -> Unit,
    softReset: () -> Unit,
    stage: MutableState<Stage>,
    cellheight: Dp,
    context: Context
) {

    if (stage.value==Stage.PLAYING) {
        Card(
            backgroundColor = element.color.value,
            modifier = Modifier
                .clickable {
                    if (element.player.value == Player.NONE) {
                        element.player.value = currentPlayer
                        didSomeoneWon(element)
                        nextPlayer()
                    }else{
                        Toast.makeText(context, "Casilla llena", Toast.LENGTH_SHORT).show()
                    }
                }
                .size(cellheight)
                .padding(4.dp)
                .fillMaxWidth(),
            elevation = 8.dp
        ) {
            Text(
                text = element.player.value.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }else{
        Card(
            backgroundColor = element.color.value,
            modifier = Modifier
                .clickable { softReset(); nextPlayer()  }
                .size(cellheight)
                .padding(4.dp)
                .fillMaxWidth(),
            elevation = 8.dp
        ) {
            Text(
                text = element.player.value.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }

}