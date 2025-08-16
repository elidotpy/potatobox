package io.github.elidotpy.classes

class Layer(val lambda: () -> Unit) { // basically a glorified function
    fun draw() {
        lambda()
    }
}
