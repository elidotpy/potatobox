package io.github.elidotpy.gameState

import io.github.elidotpy.classes.Card
import io.github.elidotpy.classes.enums.Cards
import io.github.elidotpy.gameState.action.*

class GameState {
    val player = Player()
    val enemy = Player()
    val variables = Variables()

    var thisCard: Card? = null // the card that the event is being executed on
    var attackingCard: Card? = null

    val eventInfo = EventInfo()

    private val actions: MutableList<Action> = mutableListOf()

    fun playCard(card: Card) {
        if (card.events.onPlayedTry(this)) {
            actions.add(
                Action(
                    ActionType.Play,
                    card
                )
            )

        }
    }

    fun unplayCard(card: Card) {
        actions.add(
            Action(
                ActionType.Unplay,
                card
            )
        )
    }

    fun removeCard(card: Card) {
        actions.add(
            Action(
                ActionType.Remove,
                card
            )
        )
    }

    fun removeCard(cardEnum: Cards) {
        for (card in player.cards) {
            if (card.enumEntry == cardEnum) {
                actions.add(
                    Action(
                        ActionType.Remove,
                        card
                    )
                )
                break
            }
        }
    }

    fun addCard(card: Card) {
        actions.add(
            Action(
                ActionType.Add,
                card
            )
        )
    }

    fun process() {
        eventInfo.clear()

        while (!actions.isEmpty()) {
            val actionsV = actions.toList()

            for (action in actionsV) {
                val acard = action.card

                for (card in player.cards) {
                    if (card != acard) {
                        card.events.onHitTryGlobal(this)
                    }
                }

                when (action.type) {
                    ActionType.Play -> {
                        acard.status.played = true

                        acard.events.onPlayed(this)

                        for (card in player.cards) {
                            if (card != acard) {
                                card.events.onPlayedGlobal(this)
                            }
                        }

                        eventInfo.cardBeingPlayed = acard
                    }
                    ActionType.Remove -> {
                        player.cards.remove(acard)
                    }

                    ActionType.Unplay -> {
                        acard.status.played = false
                    }

                    ActionType.Add -> {
                        player.cards.add(
                            acard
                        )
                        acard.events.onReady(this)
                    }
                }
                actions.remove(action)
            }
        }
    }
}
