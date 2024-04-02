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

class APracticalSettings_JWeld(_screen: AdvancedScreen): AAbstractPracticalSettings(_screen) {

    companion object {
        val localAnchorAValue: Vector2 = Vector2(25f, 25f).toB2
        val localAnchorBValue: Vector2 = Vector2(130f, 53f).toB2
        var referenceAngleValue: Float = 0f
        var frequencyHzValue   : Float = 0.8f
        var dampingRatioValue  : Float = 0.5f

        private fun reset() {
            localAnchorAValue.set(Vector2(25f, 25f).toB2)
            localAnchorBValue.set(Vector2(130f, 53f).toB2)
            referenceAngleValue = 0f
            frequencyHzValue    = 0.8f
            dampingRatioValue   = 0.5f
        }
    }

    // Actor
    private var localAnchorA = APracticalSelectAnchorPoint(screen, assets.C_STATIC, Vector2(100f, 100f), 50f, localAnchorAValue)
    private var localAnchorB = APracticalSelectAnchorPoint(screen, assets.H_DYNAMIC, Vector2(128f, 52f), 260f, localAnchorBValue)

    private var referenceAngleLbl = Label("", valueLabelStyle)
    private var frequencyHzLbl    = Label("", valueLabelStyle)
    private var dampingRatioLbl   = Label("", valueLabelStyle)

    private var referenceAngleProgress = AProgressPractical(screen)
    private var frequencyHzProgress    = AProgressPractical(screen)
    private var dampingRatioProgress   = AProgressPractical(screen)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addLabel(R.string.localAnchorA, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1021f, 227f, 42f))
        addLabel(R.string.localAnchorB, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 885f, 227f, 42f))
        addLabel(R.string.referenceAngle, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 763f, 262f, 42f))
        addLabel(R.string.frequencyHz, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 554f, 222f, 42f))
        addLabel(R.string.dampingRatio, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 345f, 237f, 42f))

        addLocalAnchors()
        addProgresses()
        addValueLbls()
        addPoints()
        addValues()

        collectProgresses()
    }

    override fun reinitialize() {
        localAnchorA = APracticalSelectAnchorPoint(screen, assets.C_STATIC, Vector2(100f, 100f), 50f, localAnchorAValue)
        localAnchorB = APracticalSelectAnchorPoint(screen, assets.H_DYNAMIC, Vector2(128f, 52f), 260f, localAnchorBValue)

        referenceAngleLbl = Label("", valueLabelStyle)
        frequencyHzLbl    = Label("", valueLabelStyle)
        dampingRatioLbl   = Label("", valueLabelStyle)

        referenceAngleProgress = AProgressPractical(screen)
        frequencyHzProgress    = AProgressPractical(screen)
        dampingRatioProgress   = AProgressPractical(screen)
    }

    override fun reset() {
        disposeChildren()

        APracticalSettings_JWeld.reset()

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
        addActors(referenceAngleProgress, frequencyHzProgress, dampingRatioProgress)
        referenceAngleProgress.setBounds(34f, 676f, 631f, 76f)
        frequencyHzProgress.setBounds(34f, 467f, 631f, 76f)
        dampingRatioProgress.setBounds(34f, 258f, 631f, 76f)
    }

    private fun AdvancedGroup.addValueLbls() {
        addActors(referenceAngleLbl, frequencyHzLbl, dampingRatioLbl)
        referenceAngleLbl.setBounds(316f, 763f, 200f, 42f)
        frequencyHzLbl.setBounds(276f, 554f, 100f, 42f)
        dampingRatioLbl.setBounds(291f, 345f, 100f, 42f)
    }

    private fun AdvancedGroup.addPoints() {
        val x4 = listOf(189f, 347f, 505f, 660f)
        listOf(
            /*referenceAngle*/ 700f,
        ).onEach { ny -> x4.onEach { nx -> addPointImg(nx, ny) } }

        val x3 = listOf(221f, 418f, 660f)
        listOf(
            /*frequencyHz*/ 491f,
            /*dampingRatio*/ 282f,
        ).onEach { ny -> x3.onEach { nx -> addPointImg(nx, ny) } }
    }

    private fun AdvancedGroup.addValues() {
        val values_m720_720 = listOf("-720°", "-360°", "0°", "360°", "720°")
        val values_0_10     = listOf("0", "1.0", "5.0", "10")

        val x_m720_720 = listOf(24f, 162f, 343f, 487f, 630f)
        val x_0_10     = listOf(34f, 209f, 407f, 643f)

        listOf(
            /*referenceAngle*/ Static.ValuesData(676f, x_m720_720, values_m720_720),
            /*frequencyHz*/ Static.ValuesData(467f, x_0_10, values_0_10),
            /*dampingRatio*/ Static.ValuesData(258f, x_0_10, values_0_10),
        ).onEach { data ->
            data.listValue.onEachIndexed { index, value ->
                addLabelValue(value, data.listX[index], data.y)
            }
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun collectProgresses() {
        coroutine?.launch {
            /*referenceAngle*/ launch { collectProgress_m720_720_degree(referenceAngleValue, referenceAngleProgress, referenceAngleLbl) { referenceAngleValue = it } }
            /*frequencyHz*/ launch { collectProgress_0_1_10(frequencyHzValue, frequencyHzProgress, frequencyHzLbl) { frequencyHzValue = it } }
            /*dampingRatio*/ launch { collectProgress_0_1_10(dampingRatioValue, dampingRatioProgress, dampingRatioLbl) { dampingRatioValue = it } }
        }
    }

}