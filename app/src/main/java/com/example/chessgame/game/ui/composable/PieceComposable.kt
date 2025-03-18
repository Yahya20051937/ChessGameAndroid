package com.example.chessgame.game.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chessgame.game.bi.component.piece.Piece

@Composable
fun PieceComposable(piece: Piece) {
    Image(
        painter = painterResource(piece.getImageID()),
        contentDescription = "piece",
        modifier = Modifier
            .size(35.dp)
    )
}