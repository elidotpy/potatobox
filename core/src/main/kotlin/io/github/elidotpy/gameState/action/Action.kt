package io.github.elidotpy.gameState.action

import io.github.elidotpy.classes.Card

data class Action(val type: ActionType, val card: Card)
