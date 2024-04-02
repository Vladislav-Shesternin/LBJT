package com.education.lbjt.game.actors.practical_settings.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.education.lbjt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.actor.disable
import com.education.lbjt.game.utils.actor.enable
import com.education.lbjt.game.utils.actor.setOnTouchListener
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class AButtonPracticalSettings(override val screen: AdvancedScreen): AdvancedGroup() {

    override var standartW = 144f

    private val assets = screen.game.themeUtil.assets

    // Actor
    private val buttonImg = Image(assets.PRACTICAL_BTN)
    private val stateImg  = Image(assets.PRACTICAL_SETTINGS)

    // Field
    private var state = State.SETTINGS

    var settingsBlock: () -> Unit = {}
    var doneBlock    : () -> Unit = {}

    override fun addActorsOnGroup() {
        addButtonImg()
        addStateImg()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addButtonImg() {
        addAndFillActor(buttonImg)
        buttonImg.apply {
            setOnTouchListener {
                when(state) {
                    State.SETTINGS -> runSettingsBlock()
                    State.DONE     -> runDoneBlock()
                }
            }
        }
    }

    private fun addStateImg() {
        addActor(stateImg)
        stateImg.apply {
            disable()
            setBoundsStandart(27f,27f,90f,90f)
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun Actor.changeState(region: TextureRegion) {
        disable()
        stateImg.apply {
            animHide(TIME_ANIM_SCREEN_ALPHA) {
                drawable = TextureRegionDrawable(region)
                animShow(TIME_ANIM_SCREEN_ALPHA) { this@changeState.enable() }
            }
        }
    }

    fun runDoneBlock() {
        doneBlock()
        changeState(assets.PRACTICAL_SETTINGS)
        state = State.SETTINGS
    }

    fun runSettingsBlock() {
        settingsBlock()
        changeState(assets.PRACTICAL_DONE)
        state = State.DONE
    }

    private enum class State {
        SETTINGS, DONE
    }

}