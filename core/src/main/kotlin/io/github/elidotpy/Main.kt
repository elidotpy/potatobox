package io.github.elidotpy

import io.github.kite.classes.DisposableManager
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import io.github.elidotpy.screens.LoadingScreen
import io.github.kite.classes.KiteLogger
import io.github.kite.classes.enums.KiteLoggerMode
import io.github.kite.classes.enums.VerbosityLevels

class Main : Game() {
    val logger = KiteLogger(VerbosityLevels.HIGH, KiteLoggerMode.PRINTLN)
    val dm = DisposableManager(
        logger
    )

    override fun create() {
        setScreen(LoadingScreen(this))
        proportions()
    }

    fun proportions() {
        Gdx.graphics.setWindowedMode(1920 / 2, 1008 / 2)
    }

    override fun dispose() {
        dm.dispose()
    }
}
