package io.github.elidotpy.classes

import io.github.elidotpy.functions.remove
import io.github.elidotpy.gameState.GameState

data class CardEvents(

    // every time events

    var onDeath: (state: GameState) -> Unit = { state ->
        state.player.cards.remove(state.thisCard!!.enumEntry)
    },

    var onHit: (state: GameState) -> Unit = { state ->
        state.thisCard!!.status.hp -= state.eventInfo.damage!!
    },

    var onPlayed: (state: GameState) -> Unit = {},
    var onReady: (state: GameState) -> Unit = {},
    // global events (runs even if its not a you problem)
    var onHitGlobal: (state: GameState) -> Unit = {},
    var onPlayedGlobal: (state: GameState) -> Unit = {},
    var onHitTryGlobal: (state: GameState) -> Unit = {},

    // once events
    var onDeathOnce: (state: GameState) -> Unit = {},
    var onPlayedOnce: (state: GameState) -> Unit = {},
    var onGameStart: (state: GameState) -> Unit = {},

    // try events
    var onHitTry: (state: GameState) -> Boolean = { true }, // hey, should i take a hit?
    var onPlayedTry: (state: GameState) -> Boolean = { true }
)
