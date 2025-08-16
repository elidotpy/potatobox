package io.github.elidotpy.classes

import com.badlogic.gdx.graphics.Texture
import io.github.elidotpy.classes.enums.CardTypes

data class CardInfo(
    val image: Texture? = null,
    val name: String = "",
    val description: String = "",
    val type: CardTypes = CardTypes.Normal,
    val credits: Array<CreditEntry>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CardInfo

        if (image != other.image) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (type != other.type) return false
        if (!credits.contentEquals(other.credits)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = image?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + credits.contentHashCode()
        return result
    }
}
