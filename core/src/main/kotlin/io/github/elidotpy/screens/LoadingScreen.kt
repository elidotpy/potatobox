package io.github.elidotpy.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import io.github.elidotpy.Main
import io.github.elidotpy.*

class LoadingScreen(val game: Main) : Screen {
    val dm = game.dm

    override fun show() {
        dm.add("${backgroundIDPrefix}/cardBackground", Texture("backgrounds/cards1.png"))
        dm.add("${backgroundIDPrefix}/menuBackground", Texture("backgrounds/menu1.png"))

        dm.add("${cardIDPrefix}/defaultFallback", Texture("cards/defaultFallback.png"))

        for (border in Gdx.files.internal("cards/borders").list()) {
            dm.add("${cardBordersIDPrefix}/${border.nameWithoutExtension()}", Texture(border.path()))
        }

        for (base in Gdx.files.internal("cards/baseCards").list()) {
            dm.add("${cardBaseIDPrefix}/${base.nameWithoutExtension()}", Texture(base.path()))
        }

        for (character in Gdx.files.internal("cards/characters").list()) {
            dm.add("${characterCardIDPrefix}/${character.nameWithoutExtension()}", Texture(character.path()))
        }

        dm.add("${uiIDPrefix}/cardPanel", Texture("ui/cardpanel.png"))
        dm.add("${uiIDPrefix}/potatoCardLogo",Texture("ui/logo.png"))
        dm.add("${uiIDPrefix}/buttons/play", Texture("ui/buttons/play.png"))
        dm.add("${uiIDPrefix}/buttons/play_hover", Texture("ui/buttons/play_hover.png"))
        dm.add("${uiIDPrefix}/cardGradient", Texture("ui/cardgradient.png"))

        dm.add("shaperenderer", ShapeRenderer())
        dm.add("spritebatch", SpriteBatch())
        dm.add("bitmapfont", BitmapFont())

        game.screen = MenuScreen(game)
    }

    override fun render(delta: Float) {

    }

    override fun resize(width: Int, height: Int) {

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
