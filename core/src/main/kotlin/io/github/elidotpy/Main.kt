package io.github.elidotpy

import com.badlogic.gdx.Game
import io.github.elidotpy.kiteEngineFeatures.DisposableManager
import io.github.elidotpy.kiteEngineFeatures.KiteLogger
import io.github.elidotpy.kiteEngineFeatures.KiteLoggerMode
import io.github.elidotpy.kiteEngineFeatures.VerbosityLevels

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class Main : Game() {
    // "wow, i wish i could just have one disposableManager for all my classes."
    val disposableManager = DisposableManager(
        KiteLogger(VerbosityLevels.LOW, KiteLoggerMode.PRINTLN)
    )
    val logger = KiteLogger(VerbosityLevels.HIGH, KiteLoggerMode.PRINTLN)

    override fun create() {
        setScreen(FirstScreen(this))
    }

    override fun dispose() {
        disposableManager.dispose()
    }
}
