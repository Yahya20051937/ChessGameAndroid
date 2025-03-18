package com.example.chessgame.game.ui.composable


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chessgame.game.Color as c
import com.example.chessgame.game.bi.component.Square
import com.example.chessgame.game.bi.component.piece.King
import com.example.chessgame.game.bi.viewmodel.GameViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SquareComposable(square: Square, width : Float){
    val gameViewModel: GameViewModel = koinViewModel()
    val piece by square.piece.collectAsState()
    val isPossibleMove by square.isPossibleMove.collectAsState()
    val isEatMove by square.isEatMove.collectAsState()
    val isKingInDanger by if (piece is King) (piece as King).isInDanger.collectAsState() else remember { mutableStateOf(false) }
    val isKingTrapped by if (piece is King) (piece as King).isTrapped.collectAsState() else remember { mutableStateOf(false) }
    val isDefendingKing by if (piece != null) piece!!.isDefendingKing.collectAsState() else remember { mutableStateOf(false) }
    val isEscapeMove by square.isEscapeMove.collectAsState()

    Surface (
        modifier = Modifier
            .width(width.dp)
            .fillMaxHeight()
            .clickable {
                if (piece != null && gameViewModel.turn == piece!!.color) {
                    println("square clicked.")
                    gameViewModel.select(square)
                } else if (isPossibleMove || isEscapeMove || isEatMove)
                    gameViewModel.movePiece(square)

            }
            .border(width = 0.5.dp, color = Color.Black),
        color =

        if (isKingTrapped)
            Color(169, 169, 169)

        else
            if (isEatMove)
                Color(181, 101, 29)

            else
                if (isKingInDanger) // piece is King and king is in danger.
                Color(255, 102, 102)
                else
                    if (isDefendingKing)
                        Color(173, 216, 230)
                    else if (isPossibleMove)
                        Color(144, 239, 144)
                    else
                        if (isEscapeMove)
                            Color(255, 255, 0)
                        else
                            if (square.color == c.BLACK)
                                Color.Black
                            else
                                Color.White


    ) {
        if (piece != null)
            PieceComposable(piece!!)
        Text(text = "${square.x}  ${square.y} ", color = Color.Red, fontSize = 5.sp)
    }
}