package com.example.chessgame.game.bi.component

import com.example.chessgame.game.Color
import com.example.chessgame.game.bi.component.piece.King
import com.example.chessgame.game.bi.component.piece.Pawn
import com.example.chessgame.game.bi.component.piece.Piece
import com.example.chessgame.game.bi.factory.Factory

class Army (
    val color : Color) {
    val pieces : List<Piece> = Factory.generatePieces(color)

    fun king() : King{
        return this.pieces[0] as King
    }

    fun deactivateMocking(){
        for (piece in this.pieces)
            piece.deactivateMocking()
    }

    fun activateMocking(){
        for (piece in this.pieces)
            piece.activateMocking()
    }

    fun canEscapeDanger():Boolean{
        for (piece in this.pieces)
            if (piece.getEscapeMoves().isNotEmpty())
                return true
        return false

    }

    fun resetPieces(){
        for (piece in this.pieces)
            piece.reset()
    }


}