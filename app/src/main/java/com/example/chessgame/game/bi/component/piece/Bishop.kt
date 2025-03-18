package com.example.chessgame.game.bi.component.piece

import com.example.chessgame.R
import com.example.chessgame.game.Color
import com.example.chessgame.game.bi.component.Board
import com.example.chessgame.game.bi.component.Game
import kotlin.math.max
import kotlin.math.min

class Bishop(color : Color, x : Int, y : Int): Piece(color, x, y) {
    override fun getPossibleMoves() {
        this.getDiagonalMoves(1, 1)
        this.getDiagonalMoves(1, -1)
        this.getDiagonalMoves(-1, 1)
        this.getDiagonalMoves(-1, -1)
    }



    override fun getImageID() : Int{
        return if (this.color == Color.WHITE)
            R.drawable.bishop_white
        else
            R.drawable.bishop_black
    }
}