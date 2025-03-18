package com.example.chessgame.game.bi.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.chessgame.game.Color
import com.example.chessgame.game.bi.component.Army
import com.example.chessgame.game.bi.component.Game
import com.example.chessgame.game.bi.component.Square
import com.example.chessgame.game.bi.component.piece.King
import com.example.chessgame.game.bi.component.piece.Piece
import com.example.chessgame.game.bi.factory.Factory

class GameViewModel () : ViewModel() {
    var turn = Color.WHITE
    private var selectedPiece : Piece? = null

    init {
        this.fillSquares()
    }

    fun restart(){
        Game.restart()
        this.turn = Color.WHITE
        this.selectedPiece = null
        this.fillSquares()
    }

    fun select(square: Square){
        if (selectedPiece != square.piece.value) {
            this.unselect()
            selectedPiece = square.piece.value
            if (!Game.board.getArmy(selectedPiece!!).king().isInDanger())  // if the king is not in danger, look for the possible moves.
                selectedPiece!!.getMoves()   ///  this approach is to know if the king is trapped ( no piece has escape moves when the king is in danger ) or not.
            else
                this.displayPieceEscapeMoves()  // if the king is in danger, we've already looked for the escape moves and stored them in the piece escape moves attribute.
        }
        else
            this.unselect()
    }

    fun movePiece(to:Square){
        to.removePiece()
        Game.board.findSquare(selectedPiece!!.x, selectedPiece!!.y).setPiece(null)
        selectedPiece!!.x = to.x
        selectedPiece!!.y = to.y
        to.setPiece(selectedPiece!!)
        this.unselect()
        Game.board.getArmy(this.turn).king().setIsInDanger(false) // after that the player has made a move, the king cannot be in danger. The game will end when the player has no escape moves.
        this.whichPiecesAreDefendingKing(this.turn)
        this.freeEscapeMoves()

        this.turn = if (this.turn == Color.WHITE) Color.BLACK else Color.WHITE
        this.isKingInDanger(this.turn)  // here, if the king is in danger, we will get the escape moves and save them in the pieces
        // if no escape move is found the game is done.  if the king is not in danger, we find the pieces that are defending the king.

    }





    private fun unselect(){
        this.selectedPiece = null
        Game.board.unselectSquares()
    }



    private fun fillSquares(){
        for (piece in Game.board.whiteArmy.pieces)
            Game.board
                .findSquare(piece.x, piece.y)
                .setPiece(piece)
        for (piece in Game.board.blackArmy.pieces)
            Game.board
                .findSquare(piece.x, piece.y)
                .setPiece(piece)
    }

    private fun isKingInDanger(color: Color){ // after that the opponent has made the move, we check if the king is in danger know, so that we find the escape moves.
        Game.board.getArmy(color).king().setIsInDanger(false)
        Game.checkIfKingInDanger(color)
        if (!Game.board.getArmy(color).king().isInDanger())
            this.whichPiecesAreDefendingKing(color)
        else {
            Game.activateMocking()
            Game.activateCheckEscapeMove()
            for (piece in Game.board.getArmy(color).pieces) {
                piece.setIsDefendingKing(to=false, mocking = false)  // if the king is in danger, no piece is defending the king. we don't the run the function below because it won't work if the king is in danger, du to its logic, so we just wet isDefendingKing to false
                piece.getPossibleMoves()
            }
            Game.deactivateCheckEscapeMove()
            Game.deactivateMocking()
            if (!Game.board.getArmy(color).canEscapeDanger())
                Game.board.getArmy(color).king().setIsTrapped(true)  // salat
        }
    }

    private fun whichPiecesAreDefendingKing(color: Color){
        /*after each move from both sides, we check which pieces are defending king, because they can change due to player move,
         or by opponent move, but king in danger can only change du to opponent move, so we check it after that the opponent has made a move.*/
        Game.activateMocking()
        for (piece in Game.board.getArmy(color).pieces)
            if (piece !is King)
                piece.checkIsDefendingKing()
        Game.deactivateMocking()
    }

    private fun displayPieceEscapeMoves(){
        for (square in this.selectedPiece!!.getEscapeMoves())
            square.setAsEscapeMove(true)
    }

    private fun freeEscapeMoves(){
        for (piece in Game.board.getArmy(this.turn).pieces)
            piece.freeEscapeMoves()
    }




}