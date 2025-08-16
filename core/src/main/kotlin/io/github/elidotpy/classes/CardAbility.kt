package io.github.elidotpy.classes

import io.github.elidotpy.gameState.GameState

data class CardAbility(val name: String, val description: String, val lambda: (state: GameState) -> Unit)
