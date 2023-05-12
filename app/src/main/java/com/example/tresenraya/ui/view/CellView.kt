package com.example.tresenraya.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tresenraya.data.Cell
import com.example.tresenraya.data.Player

@Composable
fun CellView(
    element: Cell,
    onClick: (Cell) -> Unit,
    cellheight: Dp,
) {
    Card(
        backgroundColor = element.color,
        modifier = Modifier
            .clickable {
                onClick(element)
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