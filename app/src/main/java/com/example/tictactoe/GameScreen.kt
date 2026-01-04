package com.example.tictactoe

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ui.theme.TictactoeTheme

@Composable
fun GameScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Tic Tac Toe",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 32.dp)
        )
        GameBoard()
    }
}

@Composable
fun GameBoard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .border(2.dp, Color.Black)
    ) {
        for (i in 0..2) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                for (j in 0..2) {
                    GameCell()
                }
            }
        }
    }
}

@Composable
fun RowScope.GameCell() {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .border(1.dp, Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "", fontSize = 48.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    TictactoeTheme {
        GameScreen()
    }
}