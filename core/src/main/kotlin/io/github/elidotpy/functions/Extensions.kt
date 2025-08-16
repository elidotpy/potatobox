package io.github.elidotpy.functions

import io.github.elidotpy.classes.Card
import io.github.elidotpy.classes.enums.Cards
import kotlin.collections.remove

fun MutableList<Card>.getCard(T: Cards): Card? {
    for (card in this) {
        if (card.enumEntry == T) {
            return card
        }
    }
    return null
}

fun MutableList<Card>.remove(element: Cards): Boolean {
    return this.remove(this.getCard(element))
}
@JvmName("MutableCardContainID")
operator fun MutableList<Card>.contains(id: Int): Boolean {
    for (card in this) {
        if (card.thisRealID == id) {
            return true
        }
    }
    return false
}

@JvmName("MutableCardContainCards")
operator fun MutableList<Card>.contains(T: Cards): Boolean {
    for (card in this) {
        if (card.enumEntry == T) {
            return true
        }
    }
    return false
}

@JvmName("ImmutableCardContainID")
operator fun List<Card>.contains(id: Int): Boolean {
    for (card in this) {
        if (card.thisRealID == id) {
            return true
        }
    }
    return false
}


@JvmName("ImmutableCardContainCards")
operator fun List<Card>.contains(T: Cards): Boolean {
    for (card in this) {
        if (card.enumEntry == T) {
            return true
        }
    }
    return false
}
