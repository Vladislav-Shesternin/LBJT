package com.education.lbjt.game.actors.practical_settings

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.education.lbjt.R
import com.education.lbjt.game.actors.practical_settings.actors.AProgressPractical
import com.education.lbjt.game.utils.Layout
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import kotlinx.coroutines.launch

class APracticalSettings_JMouse(_screen: AdvancedScreen): AAbstractPracticalSettings(_screen) {

    companion object {
        var maxForceValue    : Float = 1000f
        var frequencyHzValue : Float = 5.0f
        var dampingRatioValue: Float = 0.7f

        private fun reset() {
            maxForceValue     = 1000f
            frequencyHzValue  = 5.0f
            dampingRatioValue = 0.7f
        }
    }

    // Actor
    private var maxForceLbl          = Label("", valueLabelStyle)
    private var frequencyHzLbl       = Label("", valueLabelStyle)
    private var dampingRatioLbl      = Label("", valueLabelStyle)
    private var maxForceProgress     = AProgressPractical(screen)
    private var frequencyHzProgress  = AProgressPractical(screen)
    private var dampingRatioProgress = AProgressPractical(screen)


    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addLabel(R.string.maxForce, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1049f, 172f, 42f))
        addLabel(R.string.frequencyHz, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 840f, 222f, 42f))
        addLabel(R.string.dampingRatio, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 631f, 237f, 42f))

        addValueLbls()
        addProgresses()
        addPoints()
        addValues()

        collectProgresses()
    }

    override fun reinitialize() {
        maxForceLbl          = Label("", valueLabelStyle)
        frequencyHzLbl       = Label("", valueLabelStyle)
        dampingRatioLbl      = Label("", valueLabelStyle)
        maxForceProgress     = AProgressPractical(screen)
        frequencyHzProgress  = AProgressPractical(screen)
        dampingRatioProgress = AProgressPractical(screen)
    }

    override fun reset() {
        disposeChildren()

        APracticalSettings_JMouse.reset()

        reinitialize()
        addActorsOnGroup()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addValueLbls() {
        addActors(maxForceLbl, frequencyHzLbl, dampingRatioLbl)
        maxForceLbl.setBounds(226f, 1049f, 200f, 42f)
        frequencyHzLbl.setBounds(276f, 840f, 100f, 42f)
        dampingRatioLbl.setBounds(291f, 631f, 100f, 42f)
    }

    private fun addProgresses() {
        addActors(maxForceProgress,frequencyHzProgress,dampingRatioProgress)
        maxForceProgress.setBounds(34f, 962f, 631f, 76f)
        frequencyHzProgress.setBounds(34f, 753f, 631f, 76f)
        dampingRatioProgress.setBounds(34f, 544f, 631f, 76f)
    }

    private fun addPoints() {
        val listMaxForceX = listOf(96f, 347f, 660f)
        repeat(3) { addPointImg(listMaxForceX[it], 986f) }

        val listFrDmX = listOf(221f, 442f, 660f)
        repeat(3) {
            addPointImg(listFrDmX[it], 777f)
            addPointImg(listFrDmX[it], 568f)
        }
    }

    private fun addValues() {
        val listMaxForceX     = listOf(34f, 97f, 327f, 601f)
        val listMaxForceValue = listOf("0", "1.000", "5.000", "10.000")

        listMaxForceValue.onEachIndexed { index, value -> addLabelValue(value, listMaxForceX[index], 962f) }

        val listFrDmX     = listOf(34f, 209f, 429f, 643f)
        val listFrDmValue = listOf("-5", "0.0", "5.0", "10")

        listFrDmValue.onEachIndexed { index, value ->
            addLabelValue(value, listFrDmX[index], 753f)
            addLabelValue(value, listFrDmX[index], 544f)
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun collectProgresses() {
        coroutine?.launch {
            // maxForce
            launch { collectProgress_0_10k(maxForceValue, maxForceProgress, maxForceLbl) { maxForceValue = it } }
            // frequencyHz
            launch { collectProgress_m5_10(frequencyHzValue, frequencyHzProgress, frequencyHzLbl) { frequencyHzValue = it } }
            // dampingRatio
            launch { collectProgress_m5_10(dampingRatioValue, dampingRatioProgress, dampingRatioLbl) { dampingRatioValue = it } }
        }
    }

}