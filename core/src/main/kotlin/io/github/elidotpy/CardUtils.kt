package io.github.elidotpy

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import io.github.elidotpy.kiteEngineFeatures.DisposableType

class CardUtils(val game: Main) {
    val usedIDs = mutableListOf<Int>()
    var tri: Int = 0
    fun getCard(card: Cards, rect: Rectangle): Card {
        do {
            tri += 1
        } while (usedIDs.contains(tri))
        usedIDs.add(tri)

        game.logger.info("Getting the card assigning it ID $tri...")

        return when (card) {
            Cards.SANS -> {
                Card(
                    CardTypes.Normal,
                    game.disposableManager.getDisposable(DisposableType.TEXTURE, "card/sans") as Texture,
                    10_000f,
                    20_000f,
                    "Sans",
                    "funny skeleton boy lol",
                    tri,
                    rect
                )
            }
            Cards.KRIS -> Card(
                CardTypes.Normal,
                game.disposableManager.getDisposable(DisposableType.TEXTURE, "card/kris") as Texture,
                20_000f,
                20_000f,
                "Kris",
                "filho da toriel, ralsei provavelmente gosta dele kakak",
                tri,
                rect
            )
            Cards.SCP1471 -> Card(
                CardTypes.Normal,
                game.disposableManager.getDisposable(DisposableType.TEXTURE, "card/scp1471") as Texture,
                15_000f,
                20_000f,
                "SCP 1471 (Mal0)",
                "todo mundo sabe oq aparece quando tu pesquisa mal0 no google...",
                tri,
                rect
            )
        }
    }
}
