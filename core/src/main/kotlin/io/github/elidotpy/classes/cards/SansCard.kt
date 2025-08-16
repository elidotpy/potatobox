package io.github.elidotpy.classes.cards

import com.badlogic.gdx.graphics.Texture
import io.github.elidotpy.Main
import io.github.elidotpy.classes.Card
import io.github.elidotpy.classes.CardAbility
import io.github.elidotpy.classes.CardEvents
import io.github.elidotpy.classes.CardInfo
import io.github.elidotpy.classes.CardStatus
import io.github.elidotpy.classes.CreditEntry
import io.github.elidotpy.classes.enums.CardTypes
import io.github.elidotpy.classes.enums.Cards

data class SansCard(val game: Main) : Card(
    Cards.SANS,
    tri,
    CardInfo(
        game.dm.get("textures/card/character/sans") as Texture,
        "Sans",
        "funny skeleton boy lol",
        CardTypes.Normal,
        arrayOf(
            CreditEntry(
                "Toby Fox (creator of deltarune/undertale)",
                "creating this character"
            )
        )
    ),
    CardStatus(
        10_000f,
        20_000f,
    ),
    arrayOf(
        CardAbility(
            "Ossos",
            "Conjura ossos no inimigo, causando 15k",
        ) { state ->
            state.attackingCard!!.status.hp -= 15000
        },
        CardAbility(
            "Blaster",
            "Conjura um blaster e joga um raio no inimigo, causando 20k",
        ) { state ->
            state.attackingCard!!.status.hp -= 20000
        }
    ),

    CardEvents(
        onDeath = { state ->
            if (state.variables.get("sans/dead") != null) {
                state.thisCard!!.status.hp = 1f
                state.variables.set("sans/dead", true)
            }
        }
    )
)
