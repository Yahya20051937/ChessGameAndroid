package com.example.chessgame.game.bi.component.piece

import com.example.chessgame.R
import com.example.chessgame.game.Color
import com.example.chessgame.game.bi.component.Army
import com.example.chessgame.game.bi.component.Board
import com.example.chessgame.game.bi.component.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class King(color : Color, x : Int, y : Int): Piece(color, x, y) {
    private val _isInDanger = MutableStateFlow(false)
    val isInDanger : StateFlow<Boolean> = _isInDanger
    private var isMockDanger = false

    private val _isTrapped = MutableStateFlow(false)
    val isTrapped : StateFlow<Boolean> = _isTrapped

    fun setIsTrapped(to: Boolean){
        this._isTrapped.value = to
    }

    override fun reset(){
        this._isTrapped.value = false
        this._isInDanger.value = false
    }

    //var isMocking = false

    fun setIsInDanger(to:Boolean){
        if(Game.isMocking()) this.isMockDanger = to else this._isInDanger.value = to;
    }

    fun isInDanger() : Boolean{
        return if (Game.isMocking()) this.isMockDanger else this._isInDanger.value
    }

    override fun deactivateMocking(){
       super.deactivateMocking()
        this.isMockDanger = false
    }

    override fun activateMocking(){
        super.activateMocking()
        this.isMockDanger = _isInDanger.value
    }



    override fun getPossibleMoves() {
        if (this.x + 1 <= 8) {
            this.getMove(1, 0,)
            if (this.y +  1<= 8)
                this.getMove(1, 1)
            if (this.y - 1 >= 1)
                this.getMove(1, -1)
        }

        if (this.x - 1 >= 1){
            this.getMove(-1, 0)
            if (this.y +  1<= 8)
                this.getMove(-1, 1)
            if (this.y - 1 >= 1)
                this.getMove(-1, -1)
        }

        if (this.y + 1 <= 8)
            this.getMove(0, 1)

        if (this.y -1 >= 1)
            this.getMove(0, -1)
    }

    private fun getMove(xDirection:Int, yDirection:Int){
        val square = Game.board.findSquare(this.x + xDirection, this.y + yDirection)
        square.checkMove(this)
    }

    override fun getImageID() : Int{
        return if (this.color == Color.WHITE)
            R.drawable.king_white
        else
            R.drawable.king_black
    }



}