package io.github.kite.classes

import com.badlogic.gdx.utils.Disposable
import io.github.kite.classes.enums.VerbosityLevels

class DisposableManager(val logger: KiteLogger = KiteLogger(VerbosityLevels.NONE)) {
    val disposables: HashMap<String, Disposable> = HashMap()

    fun add(identifier: String, disposable: Disposable): Disposable {
        logger.info("adding $identifier (which is a ${disposable::class.simpleName})")
        disposables[identifier] = disposable
        return disposable
    }

    fun get(identifier: String?, fallback: String? = null): Disposable? {
        logger.info("getting $identifier")
        return disposables[identifier] ?: disposables[fallback]
    }

    fun dispose() {
        logger.info("Now disposing.")
        for ((key, disposable) in disposables.entries) {
            logger.info("Disposing $key (which is a ${disposable::class.simpleName})...")
            disposable.dispose()
        }
        disposables.clear()
    }
}
