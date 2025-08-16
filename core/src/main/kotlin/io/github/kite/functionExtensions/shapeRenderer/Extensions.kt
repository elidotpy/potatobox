package io.github.kite.functionExtensions.shapeRenderer

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle

fun ShapeRenderer.setColorRGBA(r: Int, g: Int, b: Int, a: Int = 255) {
    this.setColor(r / 255f, g / 255f, b / 255f, a / 255f)
}

// Hear me out.

fun ShapeRenderer.rect(rect: Rectangle) {
    this.rect(rect.x, rect.y, rect.width, rect.height)
}
