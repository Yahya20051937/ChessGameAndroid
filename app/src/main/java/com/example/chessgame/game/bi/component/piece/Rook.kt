package com.example.chessgame.game.bi.component.piece

import com.example.chessgame.R
import com.example.chessgame.game.Color
import com.example.chessgame.game.bi.component.Board
import com.example.chessgame.game.bi.component.Game

class Rook(color : Color, x : Int, y : Int): Piece(color, x, y) {
    override fun getPossibleMoves() {
        this.getVerticalMoves()
        this.getHorizontalMoves()
    }

    override fun getImageID() : Int{
        return if (this.color == Color.WHITE)
            R.drawable.rook_white
        else
            R.drawable.rook_black
    }
}