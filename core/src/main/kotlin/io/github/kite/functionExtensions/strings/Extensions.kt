package io.github.kite.functionExtensions.strings
import com.badlogic.gdx.utils.Disposable
import io.github.kite.classes.OldDisposableManager
import io.github.kite.classes.enums.DisposableType

fun String.asDisposable(type: DisposableType, fileName: String, disposableManager: OldDisposableManager): Disposable? {
    return disposableManager.add(type, this, fileName)
}

fun String.toTitleCase(): String {
    return this
        .split(' ')
        .joinToString(" ") { it.replaceFirstChar(Char::uppercaseChar) }
}
