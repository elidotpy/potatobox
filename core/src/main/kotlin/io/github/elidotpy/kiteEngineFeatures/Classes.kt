package io.github.elidotpy.kiteEngineFeatures

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.Disposable

enum class DisposableType {
    TEXTURE, SOUND
}

enum class VerbosityLevels {
    NONE, LOW, MEDIUM, HIGH
}

enum class KiteLoggerMode { // i personally prefer PRINTLN mode
    PRINTLN, GDX
}

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
}

class DisposableManager(val logger: KiteLogger = KiteLogger(VerbosityLevels.NONE)) {
    val textures: HashMap<String, Texture> = HashMap()
    val sounds: HashMap<String, Sound> = HashMap()
    val holding: MutableList<Disposable> = mutableListOf()
    var defFallback: Array<String?> = arrayOfNulls(2)

    fun addDisposable(type: DisposableType, identifier: String, filePath: String) {
        when (type) {
            DisposableType.TEXTURE -> {
                logger.info("Adding texture $identifier from the path $filePath...")
                textures.put(identifier, Texture(filePath))
            }
            DisposableType.SOUND -> {
                logger.info("Adding sound $identifier from the path $filePath...")
                sounds.put(identifier, Gdx.audio.newSound(Gdx.files.internal(filePath)))
            }
        }
    }

    fun getDisposable(type: DisposableType, identifier: String, fallback: String? = null): Disposable? {
        return when (type) {
            DisposableType.SOUND -> {
                if (sounds.containsKey(identifier)) {
                    logger.info("Getting sound $identifier...")
                    sounds[identifier]
                } else if (sounds.containsKey(fallback)) {
                    logger.info("Sound $identifier doesn't exist. Trying the fallback...")
                    sounds[fallback]
                } else {
                    logger.info("Sound $identifier doesn't exist and the fallback neither. Trying the default fallback...")
                    sounds[defFallback[0]]
                }
            }
            DisposableType.TEXTURE -> {
                if (textures.containsKey(identifier)) {
                    logger.info("Getting Texture $identifier...")
                    textures[identifier]
                } else if (textures.containsKey(fallback)) {
                    logger.warn("Texture $identifier doesn't exist. Trying the fallback...")
                    textures[fallback]
                } else {
                    logger.warn("Texture $identifier doesn't exist and the fallback neither. Trying the default fallback...")
                    textures[defFallback[1]]
                    // if its not set, then fuck u
                }
            }
        }
    }

    fun setDefaultFallback(type: DisposableType, identifier: String) {
        when (type) {
                DisposableType.SOUND -> {
                    defFallback[0] = identifier
                    logger.info("Set the default fallback for sounds to $identifier")
                }
                DisposableType.TEXTURE -> {
                    defFallback[1] = identifier
                    logger.info("Set the default fallback for textures to $identifier")
                }
            }
        }

    fun hold(what: Disposable): Disposable {
        logger.info("Now holding $what.")
        holding.add(what)
        return what
    }

    fun dispose() {
        logger.info("Now disposing the textures.")
        for (texture in textures.values) {
            logger.info("Disposing $texture...")
            texture.dispose()
        }
        logger.info("Now disposing the sounds.")
        for (sound in sounds.values) {
            logger.info("Disposing $sound...")
            sound.dispose()
        }
        logger.info("Now disposing what's being held.")
        for (held in holding) {
            logger.info("Disposing $held...")
            held.dispose()
        }
    }
}
