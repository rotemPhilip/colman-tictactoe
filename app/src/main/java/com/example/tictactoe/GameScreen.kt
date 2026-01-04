package com.example.tictactoe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ui.theme.TictactoeTheme

data class GameState(
    val board: List<List<Char?>> = List(3) { List(3) { null } },
    val currentPlayer: Char = 'X',
    val winner: Char? = null,
    val isDraw: Boolean = false
)

fun checkWinner(board: List<List<Char?>>): Char? {
    // Check rows
    for (i in 0..2) {
        if (board[i][0] != null && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
            return board[i][0]
        }
    }
    // Check columns
    for (j in 0..2) {
        if (board[0][j] != null && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
            return board[0][j]
        }
    }
    // Check diagonals
    if (board[0][0] != null && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
        return board[0][0]
    }
    if (board[0][2] != null && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
        return board[0][2]
    }
    return null
}

fun isBoardFull(board: List<List<Char?>>): Boolean {
    return board.all { row -> row.all { it != null } }
}

@Composable
fun GameScreen() {
    var gameState by remember { mutableStateOf(GameState()) }

    fun handleCellClick(row: Int, col: Int) {
        if (gameState.board[row][col] == null && gameState.winner == null && !gameState.isDraw) {
            val newBoard = gameState.board.map { it.toMutableList() }
            newBoard[row][col] = gameState.currentPlayer
            val winner = checkWinner(newBoard)
            val isDraw = isBoardFull(newBoard) && winner == null

            gameState = gameState.copy(
                board = newBoard,
                currentPlayer = if (gameState.currentPlayer == 'X') 'O' else 'X',
                winner = winner,
                isDraw = isDraw
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tic Tac Toe",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when {
            gameState.winner != null -> {
                Text(
                    text = "Player '${gameState.winner}' Wins!",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
            gameState.isDraw -> {
                Text(
                    text = "It's a Draw!",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
            else -> {
                Text(
                    text = "Player '${gameState.currentPlayer}' turn",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
        }

        GameBoard(gameState, onCellClick = ::handleCellClick)
    }
}

@Composable
fun GameBoard(gameState: GameState, onCellClick: (Int, Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .aspectRatio(1f)
            .border(2.dp, Color.Black)
    ) {
        for (i in 0..2) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                for (j in 0..2) {
                    GameCell(player = gameState.board[i][j]) {
                        onCellClick(i, j)
                    }
                    if (j < 2) {
                        Spacer(modifier = Modifier.width(2.dp).fillMaxHeight().background(Color.Black))
                    }
                }
            }
            if (i < 2) {
                Spacer(modifier = Modifier.height(2.dp).fillMaxWidth().background(Color.Black))
            }
        }
    }
}

@Composable
fun RowScope.GameCell(player: Char?, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = player?.toString() ?: "", fontSize = 48.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    TictactoeTheme {
        GameScreen()
    }
}
