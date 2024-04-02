package com.education.lbjt.game.utils.advanced

import com.badlogic.gdx.Input
import com.education.lbjt.game.LibGDXGame
import com.education.lbjt.game.box2d.bodiesGroup.practical.AbstractBGPractical
import com.education.lbjt.game.box2d.destroyAll

abstract class AdvancedPracticalScreen(override val game: LibGDXGame): AdvancedMouseScreen(game) {

    abstract val bgPractical: AbstractBGPractical

    override fun AdvancedStage.addActorsOnStageUI() {
        createBG_Practical()
    }

    override fun keyDown(keycode: Int): Boolean {
        return if (keycode == Input.Keys.BACK && bgPractical.aPracticalSettings.isOpen) {
            bgPractical.bPracticalSettings.getActor()?.runDoneBlock()
            true
        } else super.keyDown(keycode)
    }

    override fun dispose() {
        listOf(bgPractical).destroyAll()
        super.dispose()
    }

    // ---------------------------------------------------
    // Create BodyGroup
    // ---------------------------------------------------

    private fun createBG_Practical() {
        bgPractical.create(0f, 145f, 700f, 1255f)
    }

}