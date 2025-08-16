package io.github.kite.classes

import com.badlogic.gdx.Gdx
import io.github.kite.classes.enums.KiteLoggerMode
import io.github.kite.classes.enums.VerbosityLevels

class KiteLogger(val verbosity: VerbosityLevels, val mode: KiteLoggerMode = KiteLoggerMode.GDX, val name: String = "KITE-LOG") {
    fun error(message: String) {
        if (verbosity >= VerbosityLevels.LOW) {
            when (mode) {
                KiteLoggerMode.PRINTLN -> println("[$name:ERROR]: $message")
                KiteLoggerMode.GDX -> Gdx.app.error(name, message)
            }
        }
    }

    fun warn(message: String) {
        if (verbosity >= VerbosityLevels.MEDIUM) {
            when (mode) {
                KiteLoggerMode.PRINTLN -> println("[$name:WARNING]: $message")
                KiteLoggerMode.GDX -> Gdx.app.debug(name, message)
            }
        }
    }

    fun info(message: String) {
        if (verbosity >= VerbosityLevels.HIGH) {
            when (mode) {
                KiteLoggerMode.PRINTLN -> println("[$name:INFO]: $message")
                KiteLoggerMode.GDX -> Gdx.app.log(name, message)
            }
        }
    }

    fun debug(message: String) {
        when (mode) {
            KiteLoggerMode.PRINTLN -> println("[$name:DEBUG]: $message")
            KiteLoggerMode.GDX -> Gdx.app.debug(name, message)
        }
    }
}
