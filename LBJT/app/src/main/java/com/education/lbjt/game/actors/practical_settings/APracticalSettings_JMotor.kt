package com.education.lbjt.game.actors.practical_settings

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.education.lbjt.R
import com.education.lbjt.game.actors.practical_settings.actors.AProgressPractical
import com.education.lbjt.game.utils.Layout
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import kotlinx.coroutines.launch

class APracticalSettings_JMotor(_screen: AdvancedScreen): AAbstractPracticalSettings(_screen) {

    companion object {
        var maxForceValue        : Float = 300f
        var maxTorqueValue       : Float = 300f
        var correctionFactorValue: Float = 0.3f

        private fun reset() {
            maxForceValue         = 300f
            maxTorqueValue        = 300f
            correctionFactorValue = 0.3f
        }
    }

    // Actor
    private var maxForceLbl         = Label("", valueLabelStyle)
    private var maxTorqueLbl        = Label("", valueLabelStyle)
    private var correctionFactorLbl = Label("", valueLabelStyle)
    private var maxForceProgress         = AProgressPractical(screen)
    private var maxTorqueProgress        = AProgressPractical(screen)
    private var correctionFactorProgress = AProgressPractical(screen)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()
        addLabel(R.string.maxForce, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(35f, 859f, 172f, 42f))
        addLabel(R.string.maxTorque, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(35f, 650f, 192f, 42f))
        addLabel(R.string.correctionFactor, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(35f, 441f, 285f, 42f))

        addProgresses()
        addValueLbls()
        addPoints()
        addValues()

        collectProgresses()
    }

    override fun reinitialize() {
        maxForceLbl         = Label("", valueLabelStyle)
        maxTorqueLbl        = Label("", valueLabelStyle)
        correctionFactorLbl = Label("", valueLabelStyle)
        maxForceProgress         = AProgressPractical(screen)
        maxTorqueProgress        = AProgressPractical(screen)
        correctionFactorProgress = AProgressPractical(screen)
    }

    override fun reset() {
        disposeChildren()

        APracticalSettings_JMotor.reset()

        reinitialize()
        addActorsOnGroup()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedGroup.addProgresses() {
        addActors(maxForceProgress, maxTorqueProgress, correctionFactorProgress)
        maxForceProgress.setBounds(35f, 772f, 631f, 76f)
        maxTorqueProgress.setBounds(35f, 563f, 631f, 76f)
        correctionFactorProgress.setBounds(35f, 354f, 631f, 76f)
    }

    private fun AdvancedGroup.addValueLbls() {
        addActors(maxForceLbl, maxTorqueLbl, correctionFactorLbl)
        maxForceLbl.setBounds(227f, 859f, 100f, 42f)
        maxTorqueLbl.setBounds(247f, 650f, 100f, 42f)
        correctionFactorLbl.setBounds(340f, 441f, 100f, 42f)
    }

    private fun AdvancedGroup.addPoints() {
        val x4 = listOf(190f, 348f, 506f, 661f)
        listOf(
            796f, 587f, 378f
        ).onEach { ny ->
            x4.onEach { nx -> addPointImg(nx, ny) }
        }
    }

    private fun AdvancedGroup.addValues() {
        val values_0_1000 = listOf("0", "250", "500", "750", "1000")
        val values_0_2    = listOf("0", "0.5", "1", "1.5", "2")

        val x_0_1000 = listOf(35f, 173f, 332f, 492f, 619f)
        val x_0_2    = listOf(35f, 177f, 346f, 496f, 654f)

        listOf(
            /*maxForce*/         Static.ValuesData(772f, x_0_1000, values_0_1000),
            /*maxTorque*/        Static.ValuesData(563f, x_0_1000, values_0_1000),
            /*correctionFactor*/ Static.ValuesData(354f, x_0_2, values_0_2),
        ).onEach { data ->
            data.listValue.onEachIndexed { index, value -> addLabelValue(value, data.listX[index], data.y) }
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun collectProgresses() {
        coroutine?.launch {
            launch { collectProgress_0_1k(maxForceValue, maxForceProgress, maxForceLbl) { maxForceValue = it } }
            launch { collectProgress_0_1k(maxTorqueValue, maxTorqueProgress, maxTorqueLbl) { maxTorqueValue = it } }
            launch { collectProgress_0_2(correctionFactorValue, correctionFactorProgress, correctionFactorLbl) { correctionFactorValue = it } }
        }
    }

}