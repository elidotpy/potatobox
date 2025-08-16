package io.github.elidotpy.classes.cards

import io.github.elidotpy.Main
import io.github.elidotpy.classes.Card
import io.github.elidotpy.classes.CardInfo
import io.github.elidotpy.classes.CardStatus
import io.github.elidotpy.classes.enums.CardTypes
import io.github.elidotpy.classes.enums.Cards

data class EscudoCard(val game: Main) : Card(
    Cards.ESCUDO,
    tri,
    CardInfo(
        null,
        "Escudo",
        "Dá mais 10k de vida.",
        CardTypes.Cure
    ),
    CardStatus(
        0f,
        0f
    )
)
