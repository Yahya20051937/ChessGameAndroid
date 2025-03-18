package com.example.chessgame.game.bi.component

import com.example.chessgame.game.Color
import com.example.chessgame.game.bi.component.piece.King
import com.example.chessgame.game.bi.component.piece.Piece
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Square (
    val x : Int,
    val y : Int,
    val color: Color
) {
    private val _piece = MutableStateFlow<Piece?>(null)
    val piece : StateFlow<Piece?> = _piece

    //private var isMocking = false   // this will always have the same value as in game.isMocking
    private var mockPiece : Piece? = null

    private val _isPossibleMove = MutableStateFlow(false)
    val isPossibleMove : StateFlow<Boolean> = _isPossibleMove

    private val _isEatMove = MutableStateFlow(false)
    val isEatMove : StateFlow<Boolean> = _isEatMove

    private val _isEscapeMove = MutableStateFlow(false)
    val isEscapeMove : StateFlow<Boolean> = _isEscapeMove

    /*private val _isSelected = MutableStateFlow(false)
    val isSelected : StateFlow<Boolean> = _isSelected*/

    fun deactivateMocking(){
        this.mockPiece = null
    }

    fun activateMocking(){
        //this.isMocking = true
        this.mockPiece = this._piece.value
    }


    fun setPiece(to : Piece?){
        if (!Game.isMocking())
            this._piece.value = to
        else
            this.mockPiece = to
    }

    fun getPiece() : Piece?{
        return if (Game.isMocking()) this.mockPiece else _piece.value
    }

    fun setAsPossibleMove(b:Boolean){
        _isPossibleMove.value = b
    }

    fun setAsEscapeMove(b:Boolean){
        _isEscapeMove.value = b;
    }

    fun setAsEatMove(b: Boolean){
        _isEatMove.value = b
    }


    fun unselect(){
        this.setAsEatMove(false)
        this.setAsPossibleMove(false)
        this.setAsEscapeMove(false)
    }


    fun isEmpty() :Boolean{
        return (if (Game.isMocking())  this.mockPiece == null else this._piece.value == null) || this.getPiece()?.isDead() == true
    }

    fun hasOpponent(turn:Color):Boolean{
       return !this.isEmpty() &&  this.getPiece()!!.color != turn
    }

    fun hasOpponentKing(turn: Color) : Boolean{
        return this.hasOpponent(turn) && this.getPiece() is King
    }



    fun checkMove(piece: Piece) : Boolean{   // return true if we must check the next square.
        return if (Game.checkIfKingInDanger())
            this.checkIfDangerToKing(piece)
        else
            if (Game.checkIfEscapeMove())
                this.checkEscapeOrSafeMove(piece, EscapeOrSafe.ESCAPE)
            else
                if (Game.checkIfSaveMove())
                    this.checkEscapeOrSafeMove(piece, EscapeOrSafe.SAFE)
                else
                    this.checkNormalMove(piece)
    }

    private fun checkNormalMove(piece: Piece) : Boolean{
        if (this.isEmpty()) {
            this.setAsPossibleMove(true)
            return true
        } else {
            if (this.hasOpponent(piece.color))
                this.setAsEatMove(true)
            return false
        }
    }

    fun checkEscapeOrSafeMove(piece: Piece, moveType:EscapeOrSafe) : Boolean{
        if (this.isEmpty() || this.hasOpponent(piece.color)){
            // Mocking is activated.
            val pieceInSquare = this.moveMove(piece)
            Game.board.getArmy(piece).king().setIsInDanger(false)   // check if now , after that the piece has moved, the king is in danger or not (if not, it's an escape move.)
            Game.checkIfKingInDanger(piece.color)
            if (!Game.board.getArmy(piece).king().isInDanger()) {
                if (moveType == EscapeOrSafe.ESCAPE)
                    piece.addEscapeMove(this)  // if we reach this line, it's not the player who clicked the square , but we want to find the escape moves and save them in the piece escape moves attribute.
                else if (moveType == EscapeOrSafe.SAFE)
                   this.setAsPossibleMove(true)
            }
            this.resetMockMove(pieceInSquare, piece)
            return this.isEmpty()
        }
        return false;
    }



    private fun moveMove(piece: Piece) : Piece?{
        // Mock the scenario where the piece has made the move
        val pieceInSquare = this.getPiece()
        pieceInSquare?.kill() // mock kill the opponent piece in the square if exists
        Game.board.findSquare(piece.x, piece.y).setPiece(null)
        this.setPiece(piece)
        return pieceInSquare
    }

    private fun resetMockMove(pieceInSquare: Piece?, piece: Piece){
        pieceInSquare?.revive()
        this.setPiece(pieceInSquare)
        Game.board.findSquare(piece.x, piece.y).setPiece(piece)
    }

    private fun checkIfDangerToKing(piece:Piece) : Boolean{
        if (this.isEmpty())
            return true
        if (this.hasOpponentKing(piece.color)) {
            println(piece.toString() + "is A danger to King , Mocking is " + Game.isMocking().toString() + "square : $x $y")
            (this.getPiece() as King).setIsInDanger(true)
        }
        return false
    }

    fun removePiece(){
        this._piece.value?.kill()
        this._piece.value = null
    }

    fun freeAndReset(){
        this._piece.value = null
        this._isEscapeMove.value = false
        this._isEatMove.value = false
        this._isPossibleMove.value = false
    }


}