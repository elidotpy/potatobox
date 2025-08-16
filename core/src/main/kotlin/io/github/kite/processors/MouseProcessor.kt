package io.github.kite.processors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.Viewport


class MouseProcessor(val viewport: Viewport? = null) : DefaultProcessor() {
    private var touchPoint = Vector3()
    var onMouseMoved: (screenX: Int, screenY: Int) -> Boolean = { screenX, screenY -> false }
    var onTouchDown: (screenX: Int, screenY: Int, pointer: Int, button: Int) -> Boolean = { screenX, screenY, pointer, button -> false }
    var onTouchUp: (screenX: Int, screenY: Int, pointer: Int, button: Int) -> Boolean = { screenX, screenY, pointer, button -> false }
    var onTouchDragged: (screenX: Int, screenY: Int, pointer: Int) -> Boolean = { screenX, screenY, pointer -> false }
    var onScrolled: (amountX: Float, amountY: Float) -> Boolean = {amountX, amountY -> false}
    var onTouchCancelled: (screenX: Int, screenY: Int, pointer: Int, button: Int) -> Boolean = {screenX, screenY, pointer, button -> false}
    var mousePos = Vector2()
    val mouseRect = Rectangle(mousePos.x, mousePos.y, 10f, 10f)

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        if (viewport != null) {
            touchPoint.set(screenX * 1f, screenY * 1f, 0f)

            viewport.unproject(touchPoint)

            mousePos.set(touchPoint.x, touchPoint.y)
        } else {
            mousePos.set(screenX * 1f, -screenY.toFloat() + Gdx.graphics.height)
        }
        mouseRect.setCenter(mousePos.x, mousePos.y)
        return onMouseMoved(screenX, screenY)
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return onTouchDown(screenX, screenY, pointer, button)
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return onTouchUp(screenX, screenY, pointer, button)
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return onTouchDragged(screenX, screenY, pointer)
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean { // like, this is DEFINITELY mouse
        return onScrolled(amountX, amountY)
    }

    override fun touchCancelled(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return onTouchCancelled(screenX, screenY, pointer, button)
    }
}
