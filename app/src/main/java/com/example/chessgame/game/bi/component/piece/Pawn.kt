package com.example.chessgame.game.bi.component.piece

import com.example.chessgame.R
import com.example.chessgame.game.Color
import com.example.chessgame.game.bi.component.Board
import com.example.chessgame.game.bi.component.EscapeOrSafe
import com.example.chessgame.game.bi.component.Game
import com.example.chessgame.game.bi.component.Square

class Pawn(color : Color, x : Int, y : Int): Piece(color, x, y) {
    override fun getPossibleMoves() {
        var square1 : Square? = null
        var square2 : Square? = null
        var square3 : Square? = null
        var square4 : Square? = null
        val board = Game.board
        if (color == Color.WHITE && this.y != 1){
            if (this.x != 8)
                square1 = board.findSquare(this.x + 1, this.y - 1)
            if (this.x != 1)
                square2 = board.findSquare(this.x - 1, this.y - 1)
            square3 = board.findSquare(this.x, this.y - 1)
            if (this.y == 7)
                square4 = if (board.findSquare(this.x, this.y - 1).isEmpty()) board.findSquare(this.x, this.y - 2) else null
        }
        else if (this.y != 8){
            if (this.x != 8)
                square1 = board.findSquare(this.x + 1, this.y + 1)
            if (this.x != 1)
                square2 = board.findSquare(this.x - 1, this.y + 1)
            square3 = board.findSquare(this.x, this.y + 1)
            if (this.y == 2)
                square4 = if (board.findSquare(this.x, this.y + 1).isEmpty()) board.findSquare(this.x, this.y + 2) else null
        }

        if  (Game.checkIfKingInDanger())
            this.checkIfDangerToKing(square1, square2)

        else {
            if (Game.checkIfEscapeMove()){
                this.checkEatEscapeOrSafeMove(square1, EscapeOrSafe.ESCAPE)
                this.checkEatEscapeOrSafeMove(square2, EscapeOrSafe.ESCAPE)
                this.checkNormalEscapeOrSafeMove(square3, EscapeOrSafe.ESCAPE)
                this.checkNormalEscapeOrSafeMove(square4, EscapeOrSafe.ESCAPE)
            }
            else if (Game.checkIfSaveMove()){
                this.checkEatEscapeOrSafeMove(square1, EscapeOrSafe.SAFE)
                this.checkEatEscapeOrSafeMove(square2, EscapeOrSafe.SAFE)
                this.checkNormalEscapeOrSafeMove(square3, EscapeOrSafe.SAFE)
                this.checkNormalEscapeOrSafeMove(square4, EscapeOrSafe.SAFE)
            }

            else{
                this.checkNormalMove(square3)
                this.checkNormalMove(square4)
                this.checkEatMove(square1)
                this.checkEatMove(square2)
            }

            }

    }

    private fun checkIfDangerToKing(square1: Square?, square2: Square?){
        if (square1?.hasOpponentKing(this.color) == true)
            (square1.piece.value as King).setIsInDanger(true)
        if (square2?.hasOpponentKing(this.color) == true)
            (square2.piece.value as King).setIsInDanger(true)
    }

    private fun checkNormalMove(square: Square?){
        if (square?.isEmpty() == true)
            square.setAsPossibleMove(true)
    }

    private fun checkEatMove(square: Square?){
        if (square?.hasOpponent(this.color) == true)
            square.setAsEatMove(true)
    }

    private fun checkNormalEscapeOrSafeMove(square: Square?, moveType:EscapeOrSafe){
        if (square?.isEmpty() == true)
            square.checkEscapeOrSafeMove(this, moveType)
    }

    private fun checkEatEscapeOrSafeMove(square:Square?, moveType: EscapeOrSafe){
        if (square?.hasOpponent(this.color) == true)
            square.checkEscapeOrSafeMove(this, moveType)
    }

    override fun getImageID() : Int{
        return if (this.color == Color.WHITE)
            R.drawable.pawn_white
        else
            R.drawable.pawn_black
    }
}