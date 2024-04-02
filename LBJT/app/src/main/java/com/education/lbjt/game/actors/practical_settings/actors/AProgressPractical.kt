package com.education.lbjt.game.actors.practical_settings.actors

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.education.lbjt.game.actors.masks.Mask
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgressPractical(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 631f

    private val assets = screen.game.themeUtil.assets

    private val backgroundImage = Image(assets.PRACTICAL_PROGRESS_BACKGROUND)
    private val progressImage   = Image(assets.PRACTICAL_PROGRESS)
    private val armImage        = Image(assets.PRACTICAL_PROGRESS_ARM)
    private val mask            = Mask(screen, assets.PRACTICAL_PROGRESS_MASK, alphaWidth = 100)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addBackground()
        addMask()
        addArm()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    progressImage.x = percent * onePercentX - LENGTH
                    armImage.x      = (progressImage.x + LENGTH) - (armImage.width / 2f)
                }
            }
        }

        addListener(inputListener())
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addBackground() {
        addActor(backgroundImage)
        backgroundImage.setBounds(0f, 24f, LENGTH, 5f)
    }

    private fun AdvancedGroup.addMask() {
        addActor(mask)
        mask.setBounds(0f, 24f, LENGTH, 5f)
        mask.addProgress()
    }

    private fun AdvancedGroup.addProgress() {
        addActor(progressImage)
        progressImage.setBounds(-LENGTH, 0f, LENGTH, 5f)
    }

    private fun AdvancedGroup.addArm() {
        addActor(armImage)
        armImage.setBounds(0f, 27f, 39f, 49f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun inputListener() = object : InputListener() {
        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchDragged(event, x, y, pointer)
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            progressPercentFlow.value = when {
                x <= 0 -> 0f
                x >= LENGTH -> 100f
                else -> x / onePercentX
            }

            event?.stop()
        }
    }

    fun setProgressPercent(percent: Float) {
        progressPercentFlow.value = percent
    }


}