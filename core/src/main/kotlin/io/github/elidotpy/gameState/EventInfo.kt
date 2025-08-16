package io.github.elidotpy.gameState

import io.github.elidotpy.classes.Card
import io.github.elidotpy.classes.CardAbility

class EventInfo() {
    var damage: Float? = null
    var cardBeingAttacked: Card? = null
    var cardAttacking: Card? = null
    var isAbility: Boolean = false
    var abilityUsed: CardAbility? = null
    var cardBeingPlayed: Card? = null

    fun clear() {
        damage = null
        cardAttacking = null
        cardBeingAttacked = null
        isAbility = false
        abilityUsed = null
        cardBeingPlayed = null
    }
}
