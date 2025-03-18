package com.example.chessgame.game.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessgame.game.bi.component.Game
import com.example.chessgame.game.bi.viewmodel.GameViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RestartComposable(){
    val whiteKingTrapped by Game.board.whiteArmy.king().isTrapped.collectAsState()
    val blackKingTrapped by Game.board.blackArmy.king().isTrapped.collectAsState()
    val gameViewModel : GameViewModel = koinViewModel()

    if (whiteKingTrapped || blackKingTrapped)
        Surface (
            color = Color.Black,
            modifier = Modifier
                .clickable { gameViewModel.restart() }
                .padding(top = 20.dp)
                .width(90.dp)
                .height(40.dp)
        ) {
            Text(
                text = "Restart",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
}