package com.example.chessgame.game.bi.factory

import com.example.chessgame.game.Color
import com.example.chessgame.game.bi.component.Square
import com.example.chessgame.game.bi.component.piece.Bishop
import com.example.chessgame.game.bi.component.piece.King
import com.example.chessgame.game.bi.component.piece.Knight
import com.example.chessgame.game.bi.component.piece.Pawn
import com.example.chessgame.game.bi.component.piece.Piece
import com.example.chessgame.game.bi.component.piece.Queen
import com.example.chessgame.game.bi.component.piece.Rook

object Factory {
    fun generateSquares() : List<Square>{
        val pieces = mutableListOf<Square>()
        for (j in 1..8)
            for (i in 1..8)
                pieces.add(Square(x = i, y = j, color = this.squareColor(i, j)))
        return pieces
    }

    private fun squareColor(i : Int, j : Int) : Color{
        return if (i % 2 != 0)
            if (j % 2 == 0)
                Color.WHITE
            else
                Color.BLACK
        else
            if (j % 2 == 0)
                Color.BLACK
            else
                Color.WHITE
    }

    fun generatePieces(color: Color) : List<Piece>{
        val y = if (color == Color.BLACK) 1 else 8
        val pawnsY = if (color == Color.BLACK) y + 1 else y -1
        val pieces = mutableListOf<Piece>()
        this.generateKing(color, pieces, y)
        this.generateQueen(color, pieces, y)
        this.generatePawns(color, pieces, pawnsY)
        this.generateRooks(color, pieces, y)
        this.generateKnights(color, pieces, y)
        this.generateBishops(color, pieces, y)
        return pieces
    }

    private fun generatePawns(color : Color, pieces:MutableList<Piece>, y : Int){
        for (i in 1..8)
            pieces.add(Pawn(x=i, y=y, color=color))
    }

    private fun generateRooks(color: Color, pieces: MutableList<Piece>, y: Int){
        pieces.add(Rook(x=1, y=y, color=color))
        pieces.add(Rook(x=8, y=y, color=color))
    }

    private fun generateKnights(color: Color, pieces: MutableList<Piece>, y: Int){
        pieces.add(Knight(x=2, y=y, color=color))
        pieces.add(Knight(x=7, y=y, color=color))
    }

    private fun generateBishops(color: Color, pieces: MutableList<Piece>, y: Int){
        pieces.add(Bishop(x=3, y=y, color=color))
        pieces.add(Bishop(x=6, y=y, color=color))
    }

    private fun generateKing(color: Color, pieces: MutableList<Piece>, y: Int){
        pieces.add(King(x=5, y=y, color=color))
    }

    private fun generateQueen(color: Color, pieces: MutableList<Piece>, y: Int){
        pieces.add(Queen(x=4, y=y, color=color))
    }



}