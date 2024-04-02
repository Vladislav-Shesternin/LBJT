package com.education.lbjt.game.actors.label

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.education.lbjt.game.actors.masks.Mask
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.toMS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class ASpinningLabel(
    override val screen: AdvancedScreen,
    var text: String,
    private val labelStyle: Label.LabelStyle,
    private var timeDelay : Float = TIME_DELAY,
    private var timeRoll  : Float = TIME_ROLL_CURRENT,
    private val alignment : Int   = Align.center,
    private val alphaWidth: Int   = 1000
): AdvancedGroup() {

    companion object {
        const val LABEL_SPACE_PERCENT = 10

        // seconds
        const val TIME_ROLL_CURRENT = 5f
        const val TIME_DELAY        = 2f
    }

    private val mask         = Mask(screen, alphaWidth = alphaWidth)
    private var labelCurrent = Label(text, labelStyle)
    private var labelNext    = Label(text, labelStyle)

    private val glyphLayout = GlyphLayout()

    private val labelSpace get() = (getTextWidth() / 100) * LABEL_SPACE_PERCENT

    private val timeRollNext = timeRoll + ((timeRoll / 100) * LABEL_SPACE_PERCENT)


    override fun addActorsOnGroup() {
        addMask()
        addCurrentLabel()
    }

    private fun addMask() {
        addActor(mask)
        mask.setBounds(0f, 0f, width, height)
    }

    private fun addCurrentLabel() {
        mask.addActor(labelCurrent)

        labelCurrent.setSize(width,height)
        labelCurrent.setPosition(0f, 0f)

        if (getTextWidth() > labelCurrent.width) {
            labelCurrent.setAlignment(Align.left)
            addNextLabel()
        } else labelCurrent.setAlignment(alignment)
    }

    private fun addNextLabel() {
        mask.addAndFillActor(labelNext)

        labelNext.setSize(width, height)
        labelNext.setAlignment(Align.left)

        setPositionLabelNext()
        spin()
    }


    private fun getTextWidth() = glyphLayout.run {
        setText(labelStyle.font, text)
        width
    }


    private fun swapLabel() {
        Gdx.app.postRunnable {
            labelCurrent = labelNext.apply { labelNext = labelCurrent }
            setPositionLabelNext()
        }
    }

    private fun setPositionLabelNext() {
        Gdx.app.postRunnable { labelNext.setPosition(getTextWidth() + labelSpace, 0f) }
    }

    private fun spin() {
        val finishFlow = MutableSharedFlow<Boolean>(replay = 2)

        coroutine?.launch(Dispatchers.Default) {
            while (true) {
                delay(timeDelay.toMS)
                Gdx.app.postRunnable {
                    labelCurrent.addAction(
                        Actions.sequence(
                            Actions.moveBy(-getTextWidth(), 0f, timeRoll),
                            Actions.run { finishFlow.tryEmit(true) }
                        ))
                    labelNext.addAction(
                        Actions.sequence(
                            Actions.moveBy(-(getTextWidth() + labelSpace), 0f, timeRollNext),
                            Actions.run { finishFlow.tryEmit(true) }
                        ))
                }

                finishFlow.take(2).collectIndexed { index, _ ->
                    if (index == 1) {
                        finishFlow.resetReplayCache()
                        swapLabel()
                    }
                }
            }
        }
    }

    fun setText(text: CharSequence) {
        Gdx.app.postRunnable {
            this.text = text.toString()

            coroutine?.coroutineContext?.cancelChildren()

            listOf(labelCurrent, labelNext).onEach { it.clearActions() }

            labelCurrent.setText(text)
            labelNext.setText(text)

            if (mask.hasChildren()) {
                mask.clearChildren()
                addCurrentLabel()
            }
        }
    }



}