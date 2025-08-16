package io.github.elidotpy.classes

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle

data class Button(val x: Float, val y: Float, val width: Float, val height: Float, val image: Texture? = null) {
    val rect = Rectangle(x, y, width, height)
    var onClick: () -> Unit = {}
}
