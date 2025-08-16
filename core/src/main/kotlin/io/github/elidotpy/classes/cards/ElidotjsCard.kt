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

data class ElidotjsCard(val game: Main) : Card(
    Cards.ELIDOTPY,
    tri,
    CardInfo(
        game.dm.get("${characterCardIDPrefix}/elidotjs") as Texture,
        "elidotjs",
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
        15000f,
        20000f
    )
)
