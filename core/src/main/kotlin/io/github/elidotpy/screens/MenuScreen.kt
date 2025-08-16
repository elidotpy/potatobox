package io.github.elidotpy.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.viewport.FitViewport
import io.github.elidotpy.Main
import io.github.elidotpy.backgroundIDPrefix
import io.github.elidotpy.classes.Button
import io.github.elidotpy.uiIDPrefix
import io.github.kite.processors.MouseProcessor
import io.github.kite.functionExtensions.spriteBatch.*

class MenuScreen(val game: Main) : Screen {
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(1920f, 1008f, camera)
    private val dm = game.dm
    private val shapeRenderer = dm.get("shaperenderer") as ShapeRenderer
    private val batch = dm.get("spritebatch") as SpriteBatch
    private val font = dm.get("bitmapfont") as BitmapFont
    private val inputProcessor = MouseProcessor(viewport)
    private val playButton = Button(100f, 200f, 400f, 200f)

    override fun show() {
        viewport.apply()
        Gdx.input.inputProcessor = inputProcessor
        font.data.scale(3f)
        font.setColor(100f / 255, 200f / 255, 100f / 255, 255f / 255)

        inputProcessor.onTouchDown = { _,_,_, button ->
            if (button == 0) {
                if (playButton.rect.overlaps(inputProcessor.mouseRect)) {
                    playButton.onClick()
                }
            }
            false
        }

        playButton.onClick = {
            game.screen = FirstScreen(game)
        }

        Gdx.graphics.gL20.glClearColor(1f,1f,1f,1f)
    }

    override fun render(delta: Float) {
        camera.update()

        Gdx.graphics.gL20.glClear(GL30.GL_COLOR_BUFFER_BIT)
        batch.projectionMatrix = camera.combined
        shapeRenderer.projectionMatrix = camera.combined

        batch.begin()

        batch.draw(game.dm.get("${backgroundIDPrefix}/menuBackground") as Texture, Rectangle(
            -100f + inputProcessor.mouseRect.x * 0.03f, -100f + inputProcessor.mouseRect.y * 0.03f, viewport.worldWidth + 200f, viewport.worldHeight + 200f
        ))

        batch.draw(game.dm.get("${uiIDPrefix}/potatoCardLogo") as Texture,
            Rectangle(0f, 350f, viewport.worldWidth, viewport.worldHeight)
        )

        batch.draw(game.dm.get("${uiIDPrefix}/buttons/play${
            if (playButton.rect.overlaps(inputProcessor.mouseRect)) "_hover" else ""
        }") as Texture, playButton.rect)
        font.draw(batch, "V0.0.1 - Alpha\n" +
            "- 'Wow, Menu!'", 1300f, 300f)
        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun dispose() {

    }
}
