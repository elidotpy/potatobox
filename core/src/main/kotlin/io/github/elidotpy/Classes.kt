package io.github.elidotpy

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle

enum class CardTypes {
    Suporte, Normal, Cura, Sacrificio
}

data class Card (
    val type: CardTypes = CardTypes.Normal,
    val image: Texture? = null,
    var atk: Float = 0f,
    var hp: Float = 0f,
    val name: String = "",
    val description: String = "",
    var thisRealID: Int = 0,
    val rect: Rectangle = Rectangle()
)

enum class Cards {
    SANS, KRIS, SCP1471
}

