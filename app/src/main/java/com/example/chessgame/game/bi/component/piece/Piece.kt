package com.example.chessgame.game.bi.component.piece

import com.example.chessgame.R
import com.example.chessgame.game.Color
import com.example.chessgame.game.bi.component.Army
import com.example.chessgame.game.bi.component.Board
import com.example.chessgame.game.bi.component.Game
import com.example.chessgame.game.bi.component.Square
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.max
import kotlin.math.min

abstract class Piece (
    open val color : Color,
    var x : Int,
    var y : Int
) {

    val startingX : Int = this.x
    val startingY :Int = this.y

    private var isDead = false

    private val _isDefendingKing = MutableStateFlow(false)
    val isDefendingKing : StateFlow<Boolean> = _isDefendingKing

    // Mock attributes
    private var mockX = this.x
    private var mockY = this.y
    private var mockDead = this.isDead
    //var isMocking = false  // this will always have the same value as in game.isMocking
    private var isMockDefendingKing = false

    private var escapeMoves = mutableListOf<Square>()  // this list will work only when the king is in danger , and it's the turn of this player, we will fill it with escape moves, after a move is done we will free it.

    open fun reset(){
        this.isDead = false
        this.x = this.startingX
        this.y = this.startingY
        this._isDefendingKing.value = false
        this.escapeMoves = mutableListOf()
    }

    open fun getImageID() : Int{
        return R.drawable.pawn_white
    }

    fun setIsDefendingKing(to:Boolean, mocking:Boolean = false){
        if (!Game.isMocking() || !mocking) this._isDefendingKing.value = to else this.isMockDefendingKing = to
    }

    fun isDefendingKing():Boolean{
        return if (Game.isMocking()) this.isMockDefendingKing else this._isDefendingKing.value
    }

    open fun deactivateMocking(){
        this.mockX = 1
        this.mockY = 1
        this.mockDead = false
        this.isMockDefendingKing = false
    }

    open fun activateMocking(){
        this.mockX = this.x
        this.mockY = this.y
        this.mockDead = this.isDead
        this.isMockDefendingKing = this._isDefendingKing.value
    }

    fun isDead() : Boolean{
        return if (Game.isMocking()) this.mockDead else this.isDead
    }

    fun kill(){
        if (Game.isMocking())
            this.mockDead = true
        else
            this.isDead = true
    }

    fun revive(){
        if (Game.isMocking())
            this.mockDead = false
        else
            this.isDead = false
    }


    internal fun getDiagonalMoves(xDirection:Int, yDirection:Int){
        var i = xDirection
        var j = yDirection
        while (max(this.x + i, this.y + j) <= 8 && min(this.x + i, this.y + j) >= 1){
            val square = Game.board.findSquare(this.x + i, this.y + j)
            if (square.checkMove(this)) {
                i += xDirection
                j += yDirection
            }
            else
                break

        }
    }

    internal fun getHorizontalMoves(){
        for (i in 1..<this.x){
            val square = Game.board.findSquare(this.x - i, this.y)
            if (!square.checkMove(this))
                break;
        }

        for (i in this.x+1..8){
            val square = Game.board.findSquare(i, this.y)
            if (!square.checkMove(this))
                break;
        }
    }

    internal fun getVerticalMoves(){
        for (j in 1..<this.y){
            val square = Game.board.findSquare(this.x, this.y - j)
            if (!square.checkMove( this))
                break;
        }

        for (j in this.y + 1..8){
            val square = Game.board.findSquare(this.x, j)
            if (!square.checkMove( this))
                break;
        }
    }


    fun getMoves(){
        /*
        * If the piece is defending the king, we need to check that after any move the piece will make, it will either be still defending the king, or just eat the piece that is making a the danger
        * If the king is in danger, we need to get only the moves that will save the king
        * If the piece is the king, the move shouldn't make the king in danger, because if so, the game is done.
        * */

        if (Game.board.getArmy(this).king().isInDanger()){
            Game.activateMocking()
            Game.activateCheckEscapeMove()
        }

        else if (this.isDefendingKing() || this is King) {
            Game.activateMocking()
            Game.activateCheckSafeMove()
        }

        this.getPossibleMoves()
        if (Game.checkIfEscapeMove() || Game.checkIfSaveMove()){
            Game.deactivateCheckEscapeMove()
            Game.deactivateCheckSafeMove()
            Game.deactivateMocking()
        }
    }

    internal abstract fun getPossibleMoves()


    fun checkIsDefendingKing(){
        /*Here, we mock the nonexistence of the piece, we we check if now the king is in danger (using the mock danger attribute), if so this piece is defending the king*/
        // mocking is activated.

        this.kill()
        Game.checkIfKingInDanger(this.color)
        val isDefendingKing = Game.board.getArmy(this).king().isInDanger()

        // reset
        Game.board.getArmy(this).king().setIsInDanger(false)
        this.revive()
        this.setIsDefendingKing(isDefendingKing, false)
    }

    fun addEscapeMove(square: Square){
        this.escapeMoves.add(square)
    }

    fun freeEscapeMoves(){
        this.escapeMoves.clear()
    }

    fun getEscapeMoves() : List<Square>{
        return this.escapeMoves
    }




}