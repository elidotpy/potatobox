package io.github.elidotpy.classes.cards

import com.badlogic.gdx.graphics.Texture
import io.github.elidotpy.Main
import io.github.elidotpy.characterCardIDPrefix
import io.github.elidotpy.classes.Card
import io.github.elidotpy.classes.CardAbility
import io.github.elidotpy.classes.CardInfo
import io.github.elidotpy.classes.CardStatus
import io.github.elidotpy.classes.CreditEntry
import io.github.elidotpy.classes.enums.CardTypes
import io.github.elidotpy.classes.enums.Cards
import io.github.elidotpy.functions.contains
import io.github.elidotpy.functions.getCard
import io.github.elidotpy.functions.remove

data class KrisCard(val game: Main) : Card(
    Cards.KRIS,
    tri,
    CardInfo(
        game.dm.get("${characterCardIDPrefix}/kris") as Texture,
        "Kris",
        "filho da toriel, ralsei provavelmente gosta dele kakak",
        CardTypes.Normal,
        arrayOf(
            CreditEntry(
                "Toby Fox (creator of deltarune)",
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
            "Espada",
            "Dá 24k de ATK.",
        ) { state ->
            state.attackingCard!!.status.hp -= 24000
        },
        CardAbility(
            "Escudo",
            "Usavel apenas com a carta escudo. Dá 10k a mais de vida."
        ) { state ->
            if (Cards.ESCUDO in state.player.cards) {
                state.player.cards.remove(Cards.ESCUDO)
                state.player.cards.getCard(Cards.KRIS)!!.status.hp += 10000
            }
        }
    )
)
