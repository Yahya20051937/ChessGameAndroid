package com.example.chessgame.game.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chessgame.game.bi.component.Game
import com.example.chessgame.game.Color as c
import com.example.chessgame.game.bi.viewmodel.GameViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BoardComposable(){
    val y = 200
    val height = 360
    val width = 360
    val squareSize = width.toFloat() / 8
    Column  (
        modifier = Modifier.offset(y = y.dp)
            .width(width.dp)
            .height((y + height).dp)
    ) {
        for (i in 0..7)
            Row (
                Modifier.fillMaxWidth()
                    .height(squareSize.dp)
            ) {
                for (square in Game.board.squares.subList(i * 8, (i + 1) * 8))
                   SquareComposable(square, width = squareSize)

            }
}}

