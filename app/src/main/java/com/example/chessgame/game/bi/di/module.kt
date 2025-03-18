package com.example.chessgame.game.bi.di

import com.example.chessgame.game.bi.viewmodel.GameViewModel
import org.koin.dsl.module

val module = module {
    single {
        GameViewModel()
    }
}