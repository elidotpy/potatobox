package io.github.kite.processors

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
