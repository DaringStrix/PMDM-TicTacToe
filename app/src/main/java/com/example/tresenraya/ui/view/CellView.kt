package com.example.tresenraya.ui.view

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tresenraya.data.Cell
import com.example.tresenraya.data.Player
import com.example.tresenraya.data.Stage

@Composable
fun CellView(
    element: Cell,
    nextPlayer: () -> Unit,
    currentPlayer: Player,
    didSomeoneWon: (Cell) -> Unit,
    softReset: () -> Unit,
    stage: Stage,
    cellheight: Dp,
    context: Context
) {
    Card(
        backgroundColor = element.color,
        modifier = Modifier
            .clickable {
                if (stage == Stage.PLAYING) {
                    if (element.player == Player.NONE) {
                        element.player = currentPlayer
                        didSomeoneWon(element)
                        nextPlayer()
                    } else {
                        Toast.makeText(context, "Casilla llena", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    softReset(); nextPlayer()
                }
            }
            .size(cellheight)
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = 8.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = element.player.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            )
        }
    }

}