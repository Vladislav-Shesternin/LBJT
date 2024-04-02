package com.education.lbjt.game.utils.advanced

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.Viewport
import com.education.lbjt.game.utils.actor.setFillParent

open class AdvancedStage(viewport: Viewport) : Stage(viewport) {

    fun addActors(vararg actors: Actor) {
        actors.forEach { addActor(it) }
    }

    fun addActors(actors: List<Actor>) {
        actors.forEach { addActor(it) }
    }

    fun addAndFillActor(actor: Actor) {
        addActor(actor)
        when (actor) {
            is Widget      -> actor.setFillParent(true)
            is WidgetGroup -> actor.setFillParent(true)
        }
    }

    fun addAndFillActors(actors: List<Actor>) {
        actors.forEach { addActor(it) }
        actors.setFillParent()
    }

    fun addAndFillActors(vararg actors: Actor) {
        actors.forEach { addActor(it) }
        actors.toList().setFillParent()
    }

    fun render() {
        viewport.apply()
        act()
        draw()
    }

    override fun dispose() {
        actors.onEach { actor -> if (actor is Disposable) actor.dispose() }
        super.dispose()
    }

    fun unfocusAndHideKeyboard() {
        unfocusAll()
        Gdx.input.setOnscreenKeyboardVisible(false)
    }
}