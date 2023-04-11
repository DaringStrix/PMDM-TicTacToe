package com.example.tresenraya

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tresenraya.model.Cell

@Composable
fun CellView(element: Cell, onClick: (Int) -> Unit) {

    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .clickable {
                onClick(element.cellId - 1)
            }
            .size(100.dp)
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = 8.dp
    ) {
        Text(
            text = element.player,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color(0xFFFFFFFF),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}