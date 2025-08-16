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

data class ElidotpyCard(val game: Main) : Card(
    Cards.ELIDOTPY,
    tri,
    CardInfo(
        game.dm.get("${characterCardIDPrefix}/elidotpy") as Texture,
        "elidotpy",
        "literal self insert kakakakak",
        CardTypes.Normal,
        arrayOf(
            CreditEntry(
                "Eli (creator of this game)",
                "creating this character"
            )
        )
    ),
    CardStatus(
        10_000f,
        10_000f,
    ),
    arrayOf(
        CardAbility(
            "Python",
            "Chance de 1/3 para dar 5 mil de dano, quando falhar para",
        ) { state ->
            var finalDamage = 0f
            do {
                finalDamage += 5000
            } while ((1..3).random() == 1)

            state.attackingCard!!.status.hp -= finalDamage
        }
    )
)
