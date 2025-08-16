package io.github.kite.functionExtensions.spriteBatch

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import io.github.kite.classes.DisposableManager
import io.github.kite.classes.OldDisposableManager
import io.github.kite.classes.enums.DisposableType

fun SpriteBatch.draw(texture: Texture?, rect: Rectangle) {
    if (texture != null) {
        this.draw(texture, rect.x, rect.y, rect.width, rect.height)
    }
}

fun SpriteBatch.draw(texture: String?, rect: Rectangle, disposableManager: OldDisposableManager) {
    if (texture != null) {
        this.draw(disposableManager.get(DisposableType.TEXTURE, texture) as Texture,
            rect)
    }
}

fun SpriteBatch.draw(texture: String?, x: Float, y: Float, width: Float, height: Float, disposableManager: OldDisposableManager) {
    if (texture != null) {
        this.draw(disposableManager.get(DisposableType.TEXTURE, texture) as Texture,
            x, y, width, height)
    }
}
