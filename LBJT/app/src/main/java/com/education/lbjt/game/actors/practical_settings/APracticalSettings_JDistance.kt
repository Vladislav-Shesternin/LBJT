package com.education.lbjt.game.actors.practical_settings

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.education.lbjt.R
import com.education.lbjt.game.actors.practical_settings.actors.APracticalSelectAnchorPoint
import com.education.lbjt.game.actors.practical_settings.actors.AProgressPractical
import com.education.lbjt.game.utils.Layout
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.toB2
import kotlinx.coroutines.launch

class APracticalSettings_JDistance(_screen: AdvancedScreen): AAbstractPracticalSettings(_screen) {

    companion object {
        val localAnchorAValue: Vector2 = Vector2(85f, 35f).toB2
        val localAnchorBValue: Vector2 = Vector2(85f, 85f).toB2
        var lengthValue      : Float = 10f
        var frequencyHzValue : Float = 0.7f
        var dampingRatioValue: Float = 0.2f

        private fun reset() {
            localAnchorAValue.set(Vector2(85f, 35f).toB2)
            localAnchorBValue.set(Vector2(85f, 85f).toB2)
            lengthValue       = 10f
            frequencyHzValue  = 0.5f
            dampingRatioValue = 0.2f
        }
    }

    // Actor
    private var localAnchorA         = APracticalSelectAnchorPoint(screen, assets.H_STATIC, Vector2(170f, 70f), 170f, localAnchorAValue)
    private var localAnchorB         = APracticalSelectAnchorPoint(screen, assets.C_DYNAMIC, Vector2(128f, 128f), 170f, localAnchorBValue)
    private var lengthLbl            = Label("", valueLabelStyle)
    private var frequencyHzLbl       = Label("", valueLabelStyle)
    private var dampingRatioLbl      = Label("", valueLabelStyle)
    private var lengthProgress       = AProgressPractical(screen)
    private var frequencyHzProgress  = AProgressPractical(screen)
    private var dampingRatioProgress = AProgressPractical(screen)


    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addLabel(R.string.localAnchorA, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1021f, 227f, 42f))
        addLabel(R.string.localAnchorB, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 885f, 227f, 42f))
        addLabel(R.string.length, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 763f, 114f, 42f))
        addLabel(R.string.frequencyHz, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 554f, 222f, 42f))
        addLabel(R.string.dampingRatio, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 345f, 237f, 42f))

        addLocalAnchors()
        addValueLbls()
        addProgresses()
        addPoints()
        addValues()

        collectProgresses()
    }

    override fun reinitialize() {
        localAnchorA         = APracticalSelectAnchorPoint(screen, assets.H_STATIC, Vector2(170f, 70f), 170f, localAnchorAValue)
        localAnchorB         = APracticalSelectAnchorPoint(screen, assets.C_DYNAMIC, Vector2(128f, 128f), 170f, localAnchorBValue)
        lengthLbl            = Label("", valueLabelStyle)
        frequencyHzLbl       = Label("", valueLabelStyle)
        dampingRatioLbl      = Label("", valueLabelStyle)
        lengthProgress       = AProgressPractical(screen)
        frequencyHzProgress  = AProgressPractical(screen)
        dampingRatioProgress = AProgressPractical(screen)
    }

    override fun reset() {
        disposeChildren()

        APracticalSettings_JDistance.reset()

        reinitialize()
        addActorsOnGroup()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addLocalAnchors() {
        addActors(localAnchorA, localAnchorB)
        localAnchorA.setBounds(374f, 991f, 202f, 102f)
        localAnchorB.setBounds(374f, 826f, 202f, 160f)
    }

    private fun addValueLbls() {
        addActors(lengthLbl, frequencyHzLbl, dampingRatioLbl)
        lengthLbl.setBounds(168f, 763f, 100f, 42f)
        frequencyHzLbl.setBounds(276f, 554f, 100f, 42f)
        dampingRatioLbl.setBounds(291f, 345f, 100f, 42f)
    }

    private fun addProgresses() {
        addActors(lengthProgress,frequencyHzProgress,dampingRatioProgress)
        lengthProgress.setBounds(34f, 676f, 631f, 76f)
        frequencyHzProgress.setBounds(34f, 467f, 631f, 76f)
        dampingRatioProgress.setBounds(34f, 258f, 631f, 76f)
    }

    private fun addPoints() {
        // lengthX
        listOf(188f, 347f, 507f, 660f).onEach { addPointImg(it, 700f) }
        // frequencyX | dampingX
        listOf(221f, 418f, 660f).onEach {
            addPointImg(it, 491f)
            addPointImg(it, 282f)
        }
    }

    private fun addValues() {
        val listLengthX     = listOf(34f, 179f, 329f, 489f, 643f)
        val listLengthValue = listOf("0", "5.0", "10.0", "15.0", "20")

        listLengthValue.onEachIndexed { index, value -> addLabelValue(value, listLengthX[index], 676f) }

        val listFrDmX     = listOf(34f, 209f, 407f, 643f)
        val listFrDmValue = listOf("0", "1.0", "5.0", "10")

        listFrDmValue.onEachIndexed { index, value ->
            addLabelValue(value, listFrDmX[index], 467f)
            addLabelValue(value, listFrDmX[index], 258f)
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun collectProgresses() {
        coroutine?.launch {
            // maxForce
            launch { collectProgress_0_20(lengthValue, lengthProgress, lengthLbl) { lengthValue = it } }
            // frequencyHz
            launch { collectProgress_0_1_10(frequencyHzValue, frequencyHzProgress, frequencyHzLbl) { frequencyHzValue = it } }
            // dampingRatio
            launch { collectProgress_0_1_10(dampingRatioValue, dampingRatioProgress, dampingRatioLbl) { dampingRatioValue = it } }
        }
    }

}