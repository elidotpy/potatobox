package io.github.elidotpy.classes.cards

import com.badlogic.gdx.graphics.Texture
import io.github.elidotpy.Main
import io.github.elidotpy.characterCardIDPrefix
import io.github.elidotpy.classes.Card
import io.github.elidotpy.classes.CardInfo
import io.github.elidotpy.classes.CardStatus
import io.github.elidotpy.classes.CreditEntry
import io.github.elidotpy.classes.enums.CardTypes
import io.github.elidotpy.classes.enums.Cards

data class ElidotpyKauanisCard(val game: Main) : Card(
    Cards.ELIDOT_KAUANIS,
    tri,
    CardInfo(
        game.dm.get("${characterCardIDPrefix}/elidot_kauanis") as Texture,
        "Kauanis e elidotpy",
        "wow os dois sao amigos!",
        CardTypes.Normal,
        arrayOf(
            CreditEntry(
                "Eli",
                "creating this character"
            ),
            CreditEntry(
                "Kauanis",
                "giving the idea of the character"
            )
        )
    ),
    CardStatus(
        60_000f,
        65_000f,
    ),
    null,
)

