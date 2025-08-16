package io.github.elidotpy.classes.cards

import com.badlogic.gdx.graphics.Texture
import io.github.elidotpy.Main
import io.github.elidotpy.classes.Card
import io.github.elidotpy.classes.CardInfo
import io.github.elidotpy.classes.CardStatus
import io.github.elidotpy.classes.CreditEntry
import io.github.elidotpy.classes.enums.CardTypes
import io.github.elidotpy.classes.enums.Cards

data class Scp1471Card(val game: Main) : Card(
    Cards.SCP1471,
    tri,
    CardInfo(
        game.dm.get("textures/card/character/scp1471") as Texture,
        "SCP 1471 (Mal0)",
        "todo mundo sabe oq aparece quando tu pesquisa mal0 no google...",
        CardTypes.Normal,
        arrayOf(
            CreditEntry(
                "SCP Foundation (containing the character)",
                "creating this character"
            )
        )
    ),
    CardStatus(
        15_000f,
        20_000f
    )
)
