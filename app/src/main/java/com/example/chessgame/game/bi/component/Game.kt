package com.example.chessgame.game.bi.component

import android.util.Log
import co.touchlab.stately.concurrency.synchronize
import com.example.chessgame.game.Color

object Game  {
    var board = Board()
    private var isMocking = false
    private var checkIfKingInDanger = false // this attribute will work as an argument to square.checkMove() , if (true) we then want to
    private var checkIfEscapeMove = false
    private var checkIsSafeMove = false

    fun logCaller() {
        val stackTrace = Throwable().stackTrace
        for (s in stackTrace)
            println(s)
    }

    fun restart(){
        this.board.freeAndResetSquares()
        this.board.whiteArmy.resetPieces()
        this.board.blackArmy.resetPieces()
    }


    fun activateMocking(){
        //this.logCaller()
        this.isMocking = true
        this.board.activateMocking()
    }

    fun deactivateMocking(){
        this.isMocking = false
        this.board.deactivateMocking()

    }

    fun isMocking():Boolean{
        return this.isMocking
    }

    fun checkIfKingInDanger(color: Color){
        this.activateCheckKingInDanger()
        for (p in this.board.getArmy(color, true).pieces)
            if (!p.isDead())
                p.getPossibleMoves()
        this.deactivateCheckKingInDanger()
    }

    private fun activateCheckKingInDanger(){
        this.checkIfKingInDanger = true
    }

    private fun deactivateCheckKingInDanger(){
        this.checkIfKingInDanger = false
    }

    fun activateCheckEscapeMove(){
        this.checkIfEscapeMove = true
    }

    fun deactivateCheckEscapeMove(){
        this.checkIfEscapeMove = false
    }

    fun activateCheckSafeMove(){
        this.checkIsSafeMove = true
    }

    fun deactivateCheckSafeMove(){
        this.checkIsSafeMove = false
    }

    fun checkIfKingInDanger() : Boolean{
        return this.checkIfKingInDanger
    }

    fun checkIfEscapeMove() : Boolean{
        return this.checkIfEscapeMove
    }

    fun checkIfSaveMove() : Boolean{
        return this.checkIsSafeMove
    }



}