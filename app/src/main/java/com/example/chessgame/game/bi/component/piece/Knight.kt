package com.example.chessgame.game.bi.component.piece

import com.example.chessgame.R
import com.example.chessgame.game.Color
import com.example.chessgame.game.bi.component.Board
import com.example.chessgame.game.bi.component.Game

class Knight(color : Color, x : Int, y : Int): Piece(color, x, y) {

    override fun getPossibleMoves() {
        if (this.y + 2 <= 8) {
            if (this.x + 1 <= 8)
                this.checkLMove(1, 2)
            if (this.x - 1 >= 1)
                this.checkLMove(-1, 2)
        }
        if (this.y - 2 >= 1) {
            if (this.x + 1 <= 8)
                this.checkLMove(1, -2)
            if (this.x - 1 >= 1)
                this.checkLMove(-1, -2)
        }

        if (this.x + 2 <= 8) {
            if (this.y + 1 <= 8)
                this.checkLMove(2, 1)
            if (this.y - 1 >= 1)
                this.checkLMove(2, -1)
        }
        if (this.x - 2 >= 1) {
            if (this.y + 1 <= 8)
                this.checkLMove(-2, +1)
            if (this.y - 1 >= 1)
                this.checkLMove(-2, -1)
        }
    }

    private fun checkLMove(xDirection:Int, yDirection:Int){
        val square = Game.board.findSquare(this.x + xDirection, this.y + yDirection)
        square.checkMove(this)
    }

    override fun getImageID() : Int{
        return if (this.color == Color.WHITE)
            R.drawable.knight_white
        else
            R.drawable.knight_black
    }
}