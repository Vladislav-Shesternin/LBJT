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

class APracticalSettings_JPulley(_screen: AdvancedScreen): AAbstractPracticalSettings(_screen) {

    companion object {
        val localAnchorAValue: Vector2 = Vector2(50f, 50f).toB2
        val localAnchorBValue: Vector2 = Vector2(50f, 50f).toB2

        var lengthAValue: Float = 4f
        var lengthBValue: Float = 4f
        var ratioValue  : Float = 1f

        private fun reset() {
            localAnchorAValue.set(Vector2(50f, 50f).toB2)
            localAnchorBValue.set(Vector2(50f, 50f).toB2)

            lengthAValue = 4f
            lengthBValue = 4f
            ratioValue   = 1f
        }
    }

    // Actor
    private var localAnchorA = APracticalSelectAnchorPoint(screen, assets.C_DYNAMIC, Vector2(100f, 100f), 100f, localAnchorAValue)
    private var localAnchorB = APracticalSelectAnchorPoint(screen, assets.C_DYNAMIC, Vector2(100f, 100f), 100f, localAnchorBValue)

    private var lengthALbl = Label("", valueLabelStyle)
    private var lengthBLbl = Label("", valueLabelStyle)
    private var ratioLbl   = Label("", valueLabelStyle)

    private var lengthAProgress = AProgressPractical(screen)
    private var lengthBProgress = AProgressPractical(screen)
    private var ratioProgress   = AProgressPractical(screen)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addLabel(R.string.localAnchorA, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1021f, 227f, 42f))
        addLabel(R.string.localAnchorB, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 885f, 227f, 42f))
        addLabel(R.string.lengthA, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 763f, 138f, 42f))
        addLabel(R.string.lengthB, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 554f, 137f, 42f))
        addLabel(R.string.ratio, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 345f, 85f, 42f))

        addLocalAnchors()
        addProgresses()
        addValueLbls()
        addPoints()
        addValues()

        collectProgresses()
    }

    override fun reinitialize() {
        localAnchorA = APracticalSelectAnchorPoint(screen, assets.C_DYNAMIC, Vector2(100f, 100f), 100f, localAnchorAValue)
        localAnchorB = APracticalSelectAnchorPoint(screen, assets.C_DYNAMIC, Vector2(100f, 100f), 100f, localAnchorBValue)

        lengthALbl = Label("", valueLabelStyle)
        lengthBLbl = Label("", valueLabelStyle)
        ratioLbl   = Label("", valueLabelStyle)

        lengthAProgress = AProgressPractical(screen)
        lengthBProgress = AProgressPractical(screen)
        ratioProgress   = AProgressPractical(screen)
    }

    override fun reset() {
        disposeChildren()

        APracticalSettings_JPulley.reset()

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
        addActors(lengthAProgress, lengthBProgress, ratioProgress)
        lengthAProgress.setBounds(34f, 676f, 631f, 76f)
        lengthBProgress.setBounds(34f, 467f, 631f, 76f)
        ratioProgress.setBounds(34f, 258f, 631f, 76f)
    }

    private fun AdvancedGroup.addValueLbls() {
        addActors(lengthALbl, lengthBLbl, ratioLbl)
        lengthALbl.setBounds(192f, 763f, 100f, 42f)
        lengthBLbl.setBounds(191f, 554f, 100f, 42f)
        ratioLbl.setBounds(139f, 345f, 100f, 42f)
    }

    private fun AdvancedGroup.addPoints() {
        val x4 = listOf(189f, 347f, 505f, 660f)

        listOf(
            /*lengthA*/ 700f,
            /*lengthB*/ 491f,
            /*ratio*/ 282f,
        ).onEach { ny -> x4.onEach { nx -> addPointImg(nx, ny) } }
    }

    private fun AdvancedGroup.addValues() {
        val values_0_10 = listOf("0", "2.5", "5", "7.5", "10")
        val values_0_2  = listOf("0", "0.5", "1", "1.5", "2")

        val x_0_10 = listOf(34f, 176f, 343f, 494f, 643f)
        val x_0_2  = listOf(34f, 176f, 345f, 495f, 653f)

        listOf(
            /*lengthA*/ Static.ValuesData(676f, x_0_10, values_0_10),
            /*lengthB*/ Static.ValuesData(467f, x_0_10, values_0_10),
            /*ratio*/ Static.ValuesData(258f, x_0_2, values_0_2),
        ).onEach { data ->
            data.listValue.onEachIndexed { index, value -> addLabelValue(value, data.listX[index], data.y) }
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun collectProgresses() {
        coroutine?.launch {
            launch { collectProgress_0_10(lengthAValue, lengthAProgress, lengthALbl) { lengthAValue = it } }
            launch { collectProgress_0_10(lengthBValue, lengthBProgress, lengthBLbl) { lengthBValue = it } }
            launch { collectProgress_0_2(ratioValue, ratioProgress, ratioLbl) { ratioValue = it } }
        }
    }

}