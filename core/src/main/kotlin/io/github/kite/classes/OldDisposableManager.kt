package io.github.kite.classes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.Disposable
import io.github.kite.classes.enums.DisposableType
import io.github.kite.classes.enums.VerbosityLevels
import kotlin.collections.get

class OldDisposableManager(val logger: KiteLogger = KiteLogger(VerbosityLevels.NONE)) {
    val textures: HashMap<String, Texture> = HashMap()
    val sounds: HashMap<String, Sound> = HashMap()
    val holding: MutableList<Disposable> = mutableListOf()
    val fonts: HashMap<String, BitmapFont> = HashMap()
    var defFallback: Array<String?> = arrayOfNulls(2)

    fun add(type: DisposableType, identifier: String, filePath: String): Disposable? {
        return when (type) {
            DisposableType.TEXTURE -> {
                logger.info("Adding texture $identifier from the path $filePath...")
                logger.debug("Texture $identifier is being added.")
                textures[identifier] = Texture(filePath)
                logger.debug("Texture $identifier has been added.")
                textures[identifier]
            }
            DisposableType.SOUND -> {
                logger.info("Adding sound $identifier from the path $filePath...")
                logger.debug("Sound $identifier is being added.")
                sounds[identifier] = Gdx.audio.newSound(Gdx.files.internal(filePath))
                logger.debug("Sound $identifier has been added.")
                sounds[identifier]
            }
        }
    }

    fun get(type: DisposableType, identifier: String, fallback: String? = null): Disposable? {
        return when (type) {
            DisposableType.SOUND -> {
                logger.info("Getting sound $identifier")
                sounds[identifier] ?: sounds[fallback] ?: sounds[defFallback[0]]
            }
            DisposableType.TEXTURE -> {
                textures[identifier] ?: textures[fallback] ?: textures[defFallback[1]]
            }
        }
    }

    fun add(type: DisposableType, identifiers: Array<String>, fileNames: Array<String>) {
        if (identifiers.size == fileNames.size) {
            for ((index, identifier) in identifiers.withIndex()) {
                this.add(type, identifier, fileNames[index])
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
        for ((key, texture) in textures.entries) {
            logger.info("Disposing $key...")
            texture.dispose()
        }
        logger.info("Now disposing the sounds.")
        for ((key, sound) in sounds.entries) {
            logger.info("Disposing $key...")
            sound.dispose()
        }
        logger.info("Now disposing what's being held.")
        for (held in holding) {
            logger.info("Disposing $held...")
            held.dispose()
        }

        textures.clear()
        sounds.clear()
        holding.clear()
    }
}
