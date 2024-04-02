package com.education.lbjt.game.actors.practical_settings

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.education.lbjt.R
import com.education.lbjt.game.actors.practical_settings.actors.APracticalSelectAnchorPoint
import com.education.lbjt.game.actors.practical_settings.actors.AProgressPractical
import com.education.lbjt.game.utils.Layout
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.toB2
import kotlinx.coroutines.launch

class APracticalSettings_JRope(_screen: AdvancedScreen): AAbstractPracticalSettings(_screen) {

    companion object {
        val localAnchorAValue: Vector2 = Vector2(25f, 0f).toB2
        val localAnchorBValue: Vector2 = Vector2(24f, 116f).toB2
        var maxLength: Float = 2f

        private fun reset() {
            localAnchorAValue.set(Vector2(25f, 0f).toB2)
            localAnchorBValue.set(Vector2(24f, 116f).toB2)
            maxLength = 2f
        }
    }

    // Actor
    private var localAnchorA = APracticalSelectAnchorPoint(screen, assets.C_STATIC, Vector2(100f, 100f), 50f, localAnchorAValue)
    private var localAnchorB = APracticalSelectAnchorPoint(screen, assets.V_DYNAMIC, Vector2(52f, 128f), 48f, localAnchorBValue)

    private var maxLengthLbl = Label("", valueLabelStyle)

    private var maxLengthProgress = AProgressPractical(screen)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addLabel(R.string.localAnchorA, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1021f, 227f, 42f))
        addLabel(R.string.localAnchorB, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 885f, 227f, 42f))
        addLabel(R.string.maxLength, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 763f, 195f, 42f))

        addLocalAnchors()
        addProgresses()
        addValueLbls()
        addPoints()
        addValues()

        collectProgresses()
    }

    override fun reinitialize() {
        localAnchorA = APracticalSelectAnchorPoint(screen, assets.C_STATIC, Vector2(100f, 100f), 50f, localAnchorAValue)
        localAnchorB = APracticalSelectAnchorPoint(screen, assets.H_DYNAMIC, Vector2(52f, 128f), 48f, localAnchorBValue)

        maxLengthLbl = Label("", valueLabelStyle)

        maxLengthProgress = AProgressPractical(screen)
    }

    override fun reset() {
        disposeChildren()

        APracticalSettings_JRope.reset()

        reinitialize()
        addActorsOnGroup()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedGroup.addLocalAnchors() {
        addActors(localAnchorA, localAnchorB)
        localAnchorA.setBounds(325f, 976f, 300f, 132f)
        localAnchorB.setBounds(325f, 840f, 300f, 132f)
    }

    private fun AdvancedGroup.addProgresses() {
        addActors(maxLengthProgress)
        maxLengthProgress.setBounds(34f, 676f, 631f, 76f)
    }

    private fun AdvancedGroup.addValueLbls() {
        addActors(maxLengthLbl)
        maxLengthLbl.setBounds(249f, 763f, 50f, 42f)
    }

    private fun AdvancedGroup.addPoints() {
        val x5 = listOf(158f, 283f, 410f, 536f, 660f)
        x5.onEach { nx -> addPointImg(nx, 700f) }
    }

    private fun AdvancedGroup.addValues() {
        val values_0_5 = listOf("0", "1", "2", "3", "4", "5")

        val x_0_5 = listOf(34f, 156f, 279f, 406f, 532f, 653f)

        val data = Static.ValuesData(676f, x_0_5, values_0_5)
        data.listValue.onEachIndexed { index, value -> addLabelValue(value, data.listX[index], data.y) }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun collectProgresses() {
        coroutine?.launch {
            collectProgress_0_5(maxLength, maxLengthProgress, maxLengthLbl) { maxLength = it }
        }
    }

}