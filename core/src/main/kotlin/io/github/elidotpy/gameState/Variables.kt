package io.github.elidotpy.gameState

class Variables {
    private val variables: HashMap<String, Any> = HashMap()

    fun set(name: String, value: Any) {
        variables[name] = value
    }

    fun get(name: String): Any? {
        return variables[name]
    }
}
