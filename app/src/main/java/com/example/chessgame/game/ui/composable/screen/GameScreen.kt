package com.example.chessgame.game.ui.composable.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.chessgame.game.ui.composable.BoardComposable
import com.example.chessgame.game.ui.composable.RestartComposable

@Composable
fun GameScreen() {
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        BoardComposable()
        RestartComposable()
    }

}