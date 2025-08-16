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
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import io.github.elidotpy.Main
import io.github.elidotpy.classes.Card
import io.github.elidotpy.classes.Layer
import io.github.elidotpy.classes.enums.CardTypes
import io.github.elidotpy.classes.enums.Cards
import io.github.elidotpy.gameState.GameState
import io.github.kite.processors.MouseProcessor
import io.github.elidotpy.functions.*
import io.github.elidotpy.*
import io.github.kite.functionExtensions.spriteBatch.draw
import kotlin.math.max

class FirstScreen(val game: Main) : Screen {
    private val dm = game.dm
    private var camera: OrthographicCamera = OrthographicCamera()
    private val viewport = FitViewport(1920f, 1008f, camera)
    private val inputProcessor = MouseProcessor(viewport)
    private var timeSinceLastClick = 0f

    private val shapeRenderer = dm.get("shaperenderer") as ShapeRenderer
    private val batch = dm.get("spritebatch") as SpriteBatch
    private val font = dm.get("bitmapfont") as BitmapFont

    private var cardSelected: Int? = null

    private var cardShowScrollOffset = 0f

    private val cardHeight = viewport.worldHeight / 6
    private val cardWidth = viewport.worldHeight / 6
    private val cardPos = Vector2(20f, viewport.worldHeight)
    private val cardGap = 10f
    private var maxCardsPixels: Int = 0
    private val maxCards = 5
    private var gameState = GameState()

    private var player = gameState.player
    private var enemy = gameState.enemy
    private var eventInfo = gameState.eventInfo

    val cardPanelLayer = Layer {
        batch.draw(dm.get("${backgroundIDPrefix}/cardBackground") as Texture,
            0f, 0f, viewport.worldWidth, viewport.worldHeight)

        batch.draw(dm.get("${uiIDPrefix}/cardGradient") as Texture,
            0f, 0f, viewport.worldWidth, viewport.worldHeight)

        batch.draw(dm.get("${uiIDPrefix}/cardPanel") as Texture,
            -75f, 0f, viewport.worldWidth, viewport.worldHeight)
    }

    val cardLayer = Layer {
        val rect = Rectangle(cardPos.x, cardPos.y + cardShowScrollOffset, cardWidth, cardHeight)

        for (card in player.cards) {
            rect.y -= rect.height + cardGap

            if (rect.y > viewport.worldHeight + cardWidth) {
                continue
            }

            if (rect.y < -cardWidth) {
                break
            }

            batch.draw(
                dm.get(when (card.info.type) {
                    CardTypes.Support -> "${cardBaseIDPrefix}/support"
                    CardTypes.Cure -> "${cardBaseIDPrefix}/cure"
                    CardTypes.Normal -> "${cardBaseIDPrefix}/normal"
                    CardTypes.Sacrifice -> "${cardBaseIDPrefix}/sacrifice"
                }) as Texture,
                rect
            )

            batch.draw(
                dm.get(if (rect.overlaps(inputProcessor.mouseRect)) {
                    "${cardBordersIDPrefix}/hover"
                } else if (card.thisRealID in player.playedCards) {
                    "${cardBordersIDPrefix}/played"
                } else if (card.thisRealID == cardSelected) {
                    "${cardBordersIDPrefix}/selected"
                } else {
                    null
                }) as Texture?, rect
            )

            if (card.info.image != null) {
                batch.draw(card.info.image, rect)
            }
        }
    }

    fun processCards() {
        val rect = Rectangle(cardPos.x, cardPos.y + cardShowScrollOffset, cardWidth, cardHeight)
        for (card in player.cards) {
            gameState.thisCard = card
            rect.y -= rect.height + 10f

            if (rect.y > viewport.worldHeight + cardWidth) {
                continue
            }
            if (rect.y < -cardWidth) {
                break
            }

            if (!inputProcessor.mouseRect.overlaps(rect)) {
                continue
            }

            cardSelected = card.thisRealID

            if (card.status.played) {
                gameState.unplayCard(card)
                return
            }

            if (timeSinceLastClick > 0.3) {
                return
            }

            if (player.playedCards.size > 3) {
                return
            }

            if (card.info.type == CardTypes.Support && player.playedCards.isEmpty()) {
                return
            }

            gameState.playCard(card)
        }
        gameState.process()

    }
    fun setupInputProcessorEvents() {
        inputProcessor.onTouchDown = onTouchDown@{ _, _, _, button ->
            if (button != 0) {
                return@onTouchDown false
            }
            processCards()
            timeSinceLastClick = 0f
            true
        }

        inputProcessor.onScrolled = {_, scrollY ->
            cardShowScrollOffset += scrollY * 50
            cardShowScrollOffset = cardShowScrollOffset.coerceIn(0f, maxCardsPixels * 1f)
            true
        }
    }

    override fun show() {
        viewport.apply()

        Gdx.graphics.gL20.glClearColor(0f, 0f, 0f, 0f)
        maxCardsPixels = 0

        font.data.scale(1.4f)

        setupInputProcessorEvents()

        // Add cards here
        gameState.addCard(
            Card.createCard(Cards.ELIDOTPY, game)
        )

        gameState.addCard(
            Card.createCard(Cards.KAUANIS, game)
        )

        gameState.process()
        // end of adding cards

        for (card in player.cards) {
            card.events.onGameStart(gameState)
        }

        Gdx.input.inputProcessor = inputProcessor
    }

    override fun render(delta: Float) {
        camera.update()

        timeSinceLastClick += delta

        maxCardsPixels =
            max(maxCards + ((cardHeight + cardGap) * player.cards.size).toInt() - viewport.worldHeight.toInt(), 0)

        Gdx.graphics.gL20.glClear(GL30.GL_COLOR_BUFFER_BIT)
        batch.projectionMatrix = camera.combined
        shapeRenderer.projectionMatrix = camera.combined

        batch.begin()

        cardPanelLayer.draw()
        cardLayer.draw()

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
