package io.github.kite.processors


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
