package com.education.lbjt.game.utils.advanced

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx

open class AdvancedGame: ApplicationListener {

    private var screen: AdvancedScreen? = null

    // ---------------------------------------------------
    // Override
    // ---------------------------------------------------

    override fun create() {}

    override fun render() {
        screen?.render(Gdx.graphics.deltaTime)
    }

    override fun resize(width: Int, height: Int) {
        screen?.resize(width, height)
    }

    override fun pause() {
        screen?.pause()
    }

    override fun resume() {
        screen?.resume()
    }

    override fun dispose() {
        screen?.dispose()
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun updateScreen(screen: AdvancedScreen) {
        this.screen?.dispose()
        this.screen = screen.apply {
            resize(Gdx.graphics.width, Gdx.graphics.height)
            show()
        }
    }

}