package io.github.elidotpy.classes

import io.github.elidotpy.classes.enums.Cards
import io.github.elidotpy.Main
import io.github.elidotpy.classes.cards.*

open class Card (
    val enumEntry: Cards,
    var thisRealID: Int = 0,
    val info: CardInfo = CardInfo(),
    var status: CardStatus = CardStatus(),
    var abilities: Array<CardAbility>? = null,

    // events
    val events: CardEvents = CardEvents()

) {
    companion object {
        var tri: Int = 0
        val usedIDs = mutableListOf<Int>()
        fun createCard(card: Cards, game: Main): Card {
            do {
                tri += 1
            } while (usedIDs.contains(tri))
            usedIDs.add(tri)
            return when (card) {
                Cards.SANS -> SansCard(game)
                Cards.KRIS -> KrisCard(game)
                Cards.SCP1471 -> Scp1471Card(game)
                Cards.ELIDOTPY -> ElidotpyCard(game)
                Cards.ELIDOT_KAUANIS -> ElidotpyKauanisCard(game)
                Cards.ELIDOTJS -> ElidotjsCard(game)
                Cards.KAUANIS -> KauanisCard(game)
                else -> Card(Cards.NULO)
            }
        }
    }
}
