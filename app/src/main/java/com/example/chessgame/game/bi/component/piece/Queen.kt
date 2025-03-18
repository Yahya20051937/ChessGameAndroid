package com.example.chessgame.game.bi.component.piece

import com.example.chessgame.R
import com.example.chessgame.game.Color
import com.example.chessgame.game.bi.component.Board
import com.example.chessgame.game.bi.component.Game

class Queen(color : Color, x : Int, y : Int): Piece(color, x, y) {
    override fun getPossibleMoves() {
        this.getDiagonalMoves(1, 1)
        this.getDiagonalMoves(1, -1)
        this.getDiagonalMoves(-1, 1)
        this.getDiagonalMoves(-1, -1)
        this.getHorizontalMoves()
        this.getVerticalMoves()
    }

    override fun getImageID() : Int{
        return if (this.color == Color.WHITE)
            R.drawable.queen_white
        else
            R.drawable.queen_black
    }
}