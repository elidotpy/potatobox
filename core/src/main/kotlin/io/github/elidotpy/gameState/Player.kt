package io.github.elidotpy.gameState

import io.github.elidotpy.classes.Card

class Player {
    val cards = mutableListOf<Card>()
    val playedCards: List<Card>
        get() {
            return cards.filter { it.status.played }
        }
}
