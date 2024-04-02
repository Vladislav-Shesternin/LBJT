package com.education.lbjt.game.actors.button

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.education.lbjt.game.utils.actor.disable
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class AButtonText(
    override val screen: AdvancedScreen,
    text: String, type: Static.Type? = null, labelStyle: Label.LabelStyle, val alignment: Int = Align.center
): AButton(screen, type) {

    val label = Label(text, labelStyle)
    override fun addActorsOnGroup() {
        super.addActorsOnGroup()
        addLabel()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addLabel() {
        addAndFillActor(label)
        label.apply {
            disable()
            setAlignment(alignment)
            wrap = true
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    fun setText(text: String) {
        label.setText(text)
    }

}