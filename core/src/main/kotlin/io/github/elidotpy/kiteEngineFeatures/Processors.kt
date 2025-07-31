package io.github.elidotpy.kiteEngineFeatures

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.Viewport

open class DefaultProcessor : InputProcessor {
    override fun keyDown(keycode: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(
        screenX: Int,
        screenY: Int,
        pointer: Int,
        button: Int
    ): Boolean {
        return false
    }

    override fun touchUp(
        screenX: Int,
        screenY: Int,
        pointer: Int,
        button: Int
    ): Boolean {
        return false
    }

    override fun touchCancelled(
        screenX: Int,
        screenY: Int,
        pointer: Int,
        button: Int
    ): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {

        return false
    }

} // just to delete the useless functions
open class KeyboardProcessor : DefaultProcessor() {
    var typed = ""
    val keysPressed: MutableSet<Int> = mutableSetOf()
    var onKeyTyped: (character: Char) -> Boolean = {false}
    var onKeyDown: (keycode: Int) -> Boolean = {false}
    var onKeyUp: (keycode: Int) -> Boolean = {false}

    override fun keyDown(keycode: Int): Boolean {
        keysPressed.add(keycode)
        return onKeyDown(keycode)
    }

    override fun keyUp(keycode: Int): Boolean {
        keysPressed.remove(keycode)
        return onKeyUp(keycode)
    }

    override fun keyTyped(character: Char): Boolean {
        if (character == '\b') {
            typed = typed.dropLast(1)
            return onKeyTyped(character)
        }
        typed += character
        return onKeyTyped(character)
    }
}
class MouseProcessor(val viewport: Viewport? = null) : DefaultProcessor() {
    var onMouseMoved: (screenX: Int, screenY: Int) -> Boolean = { screenX, screenY -> false }
    var onTouchDown: (screenX: Int, screenY: Int, pointer: Int, button: Int) -> Boolean = { screenX, screenY, pointer, button -> false }
    var onTouchUp: (screenX: Int, screenY: Int, pointer: Int, button: Int) -> Boolean = { screenX, screenY, pointer, button -> false }
    var onTouchDragged: (screenX: Int, screenY: Int, pointer: Int) -> Boolean = { screenX, screenY, pointer -> false }
    var onScrolled: (amountX: Float, amountY: Float) -> Boolean = {amountX, amountY -> false}
    var onTouchCancelled: (screenX: Int, screenY: Int, pointer: Int, button: Int) -> Boolean = {screenX, screenY, pointer, button -> false}
    var mousePos = Vector2()
    private var touchPoint = Vector3()

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        if (viewport != null) {
            touchPoint.set(screenX * 1f, screenY * 1f, 0f)

            viewport.unproject(touchPoint)

            mousePos.set(touchPoint.x, touchPoint.y)
        } else {

            mousePos.set(screenX * 1f, -screenY.toFloat() + Gdx.graphics.height)
        }
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
class CommandProcessor : KeyboardProcessor() {
    val commands: HashMap<String, () -> Unit> = HashMap()


    fun registerCommand(identifier: String, lambda: () -> Unit = { }) {
        commands.put(identifier, lambda)
    }

    override fun keyTyped(character: Char): Boolean {
        super.keyTyped(character)

        if (commands.filter {predicate -> predicate.key.startsWith(typed)}.isEmpty()) {
            typed = ""
        }
        if (commands[typed] != null) {
            commands[typed]?.invoke()
            typed = ""
        }
        return false
    }
}
