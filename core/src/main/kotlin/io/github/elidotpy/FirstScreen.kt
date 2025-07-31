package io.github.elidotpy

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import io.github.elidotpy.kiteEngineFeatures.DisposableManager
import io.github.elidotpy.kiteEngineFeatures.DisposableType
import io.github.elidotpy.kiteEngineFeatures.MouseProcessor
import kotlin.math.roundToInt

class FirstScreen(game: Main) : Screen {
    private val disposableManager = game.disposableManager
    private var camera: OrthographicCamera = OrthographicCamera()
    private val viewport = FitViewport(1920f, 1008f, camera)
    private val mouse = Rectangle(0f, 0f, 1f, 1f)

    private val inputProcessor = MouseProcessor(viewport)
    private val batch = disposableManager.hold(SpriteBatch()) as SpriteBatch
    private val shapeRenderer = disposableManager.hold(ShapeRenderer()) as ShapeRenderer
    private val font = disposableManager.hold(BitmapFont()) as BitmapFont

    private val cards: MutableList<Card> = mutableListOf()
    private var cardSelected: Int? = null
    private val cardUtils = CardUtils(game)

    override fun show() {
        viewport.apply()
        font.data.scale(2f)

        disposableManager.addDisposables(
            DisposableType.TEXTURE,
            arrayOf("cardBorder",
                "cardBorderSelected",
                "cardBorderHover"),
            arrayOf(
                "cards/cardBorder.png",
                "cards/cardBorder_selected.png",
                "cards/cardBorder_hover.png"
            )
        )
            for (card in arrayOf("kris", "sans", "scp1471", "defaultFallback")) {
                "card/$card".asDisposable(DisposableType.TEXTURE, "cards/$card.png", disposableManager)
            }

        disposableManager.setDefaultFallback(DisposableType.TEXTURE, "card/defaultFallback")

        // no cards are having existencial crisis now yay
        for (i in 1..100) {
            cards.addAll(
                arrayOf(
                    cardUtils.getCard(Cards.KRIS, Rectangle(0f, viewport.worldHeight / 2, viewport.worldHeight / 6, viewport.worldHeight / 6)),
                )
            )
        }


        Gdx.graphics.gL20.glClearColor(0.2f, 0.3f, 0.4f, 0.5f)
        inputProcessor.onTouchDown = {screenX, screenY, pointer, button ->
            if (button == 0) {
                for (card in cards) {
                    if (mouse.overlaps(card.rect)) {
                        cardSelected = if (cardSelected != card.thisRealID) {
                            card.thisRealID
                        } else {
                            null
                        }

                    }
                }
            }
            false
        }
        Gdx.input.inputProcessor = inputProcessor
    }

    override fun render(delta: Float) {
        camera.update()

        Gdx.graphics.gL20.glClear(GL30.GL_COLOR_BUFFER_BIT)
        batch.projectionMatrix = camera.combined
        shapeRenderer.projectionMatrix = camera.combined
        batch.begin()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        mouse.setCenter(inputProcessor.mousePos.x, inputProcessor.mousePos.y)

        println("${viewport.screenWidth}, ${viewport.screenHeight}")

        for (card in cards) {
            batch.draw(if (card.rect.overlaps(mouse)) {
                font.draw(batch,
                    "${card.name}: ${card.description}\n\nATK: ${card.atk.roundToInt()}, DEF: ${card.hp.roundToInt()}",
                    card.rect.getCenter(
                        Vector2(
                            card.rect.width,
                            card.rect.height)).x + 100f,
                    card.rect.y + card.rect.height,
                    viewport.worldWidth - card.rect.x * 3,
                    Align.left,
                    true
                )
                disposableManager.getDisposable(DisposableType.TEXTURE, "cardBorderHover") as Texture
            } else if (card.thisRealID == cardSelected) {
                disposableManager.getDisposable(DisposableType.TEXTURE, "cardBorderSelected") as Texture
            } else {
                disposableManager.getDisposable(DisposableType.TEXTURE, "cardBorder") as Texture
            }, card.rect.x, card.rect.y, card.rect.width, card.rect.height)
            batch.draw(card.image, card.rect.x, card.rect.y, card.rect.width, card.rect.height)
        }

        shapeRenderer.rect(mouse.x, mouse.y, mouse.width, mouse.height)

        batch.end()
        shapeRenderer.end()
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

fun String.asDisposable(type: DisposableType, fileName: String, disposableManager: DisposableManager) {
    disposableManager.addDisposable(type, this, fileName)
}

fun DisposableManager.addDisposables(type: DisposableType, identifiers: Array<String>, fileNames: Array<String>) {
    if (identifiers.size == fileNames.size) {
        for ((index, identifier) in identifiers.withIndex()) {
            this.addDisposable(type, identifier, fileNames[index])
        }
    }
}
