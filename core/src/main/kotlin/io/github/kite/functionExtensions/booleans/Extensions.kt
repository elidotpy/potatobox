package io.github.kite.functionExtensions.booleans

fun Boolean.toInt(): Int {
    return if (this) 1 else 0
}

fun Boolean.toFloat(): Float {
    return if (this) 1f else 0f
}

fun Boolean.toDouble(): Double {
    return if (this) 1.0 else 0.0
}
