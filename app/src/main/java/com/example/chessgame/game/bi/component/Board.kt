package com.example.chessgame.game.bi.component

import com.example.chessgame.game.Color
import com.example.chessgame.game.bi.component.piece.Piece
import com.example.chessgame.game.bi.factory.Factory

class Board {
    val squares = Factory.generateSquares()
    val whiteArmy = Army(Color.WHITE)
    val blackArmy = Army(Color.BLACK)

    public fun getArmy(color: Color, opposite:Boolean = false):Army{
        return if (color == Color.WHITE)
            if (!opposite)
                this.whiteArmy
            else
                this.blackArmy
        else
            if (!opposite)
                this.blackArmy
            else
                this.whiteArmy
    }



    fun deactivateMocking(){
        for (square in this.squares)
            square.deactivateMocking()
        this.whiteArmy.deactivateMocking()
        this.blackArmy.deactivateMocking()
    }

    fun activateMocking(){
        for (square in this.squares)
            square.activateMocking()
        this.whiteArmy.activateMocking()
        this.blackArmy.activateMocking()
    }

    fun findSquare(x : Int, y : Int) : Square{
        return squares[(y - 1) * 8 + (x - 1)]
    }

    fun unselectSquares(){
        for (square in this.squares)
            square.unselect()
    }

    fun getOpponentArmy(piece: Piece):Army{
        return if (piece.color == Color.WHITE)
            this.blackArmy
        else
            this.whiteArmy
    }

    fun getArmy(piece: Piece):Army{
        return if (piece.color == Color.WHITE)
            this.whiteArmy
        else
            this.blackArmy
    }

    fun freeAndResetSquares(){
        for (s in this.squares)
            s.freeAndReset()
    }
}