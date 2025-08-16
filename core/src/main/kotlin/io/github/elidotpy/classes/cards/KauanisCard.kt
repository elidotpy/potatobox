package io.github.elidotpy.classes.cards

import com.badlogic.gdx.graphics.Texture
import io.github.elidotpy.Main
import io.github.elidotpy.characterCardIDPrefix
import io.github.elidotpy.classes.Card
import io.github.elidotpy.classes.CardAbility
import io.github.elidotpy.classes.CardEvents
import io.github.elidotpy.classes.CardInfo
import io.github.elidotpy.classes.CardStatus
import io.github.elidotpy.classes.CreditEntry
import io.github.elidotpy.classes.enums.CardTypes
import io.github.elidotpy.classes.enums.Cards
import io.github.elidotpy.functions.*

data class KauanisCard(val game: Main) : Card(
    Cards.KAUANIS,
    tri,
    CardInfo(
        game.dm.get("${characterCardIDPrefix}/kauanis") as Texture,
        "Kauanis",
        "literal self insert v2 (meu amigo)",
        CardTypes.Support,
        arrayOf(
            CreditEntry(
                "Kauanis",
                "creating this character"
            )
        )
    ),
    CardStatus(
        20_000f,
        20_000f,
    ),

    arrayOf(
        CardAbility(
            "Proteger",
            "Leva todo o dano",
        ) { state ->

            state.thisCard!!.status.hp -= state.eventInfo.damage as Float

        }
    ),
    CardEvents(
        onPlayedGlobal = {
            state ->
            // hey, game, am i, kauanis, played?
            if (state.thisCard!!.status.played) {
                state.thisCard!!.events.onPlayed(state)
            }
        },

        onPlayed = {
            state ->
            val evolved = createCard(Cards.ELIDOT_KAUANIS, game)
            if (Cards.ELIDOTPY in state.player.playedCards) {
                state.removeCard(Cards.ELIDOTPY)
                state.removeCard(state.thisCard!!)
                state.addCard(
                    evolved
                )
            } else if (Cards.ELIDOTJS in state.player.playedCards) {
                state.removeCard(Cards.ELIDOTJS)
                state.removeCard(state.thisCard!!)

                evolved.status.hp += 30000
                evolved.status.atk += 15000
                state.addCard(
                    evolved
                )
            }
        },

        onHitGlobal = {
            state ->
            state.eventInfo.cardBeingAttacked!!.status.hp += state.eventInfo.damage!!
            state.thisCard!!.status.hp -= state.eventInfo.damage!!
        }
    )
)
