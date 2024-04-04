package com.education.lbjt.game.actors.practical_settings

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.education.lbjt.R
import com.education.lbjt.game.actors.practical_settings.actors.AProgressPractical
import com.education.lbjt.game.utils.Layout
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import kotlinx.coroutines.launch

class APracticalSettings_JGear(_screen: AdvancedScreen): AAbstractPracticalSettings(_screen) {

    companion object {
        var ratioValue: Float = 1f

        private fun reset() {
            ratioValue = 1f
        }
    }

    // Actor
    private var ratioLbl      = Label("", valueLabelStyle)
    private var ratioProgress = AProgressPractical(screen)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()
        addLabel(R.string.ratio, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 720f, 85f, 42f))

        addProgresses()
        addValueLbls()
        addPoints()
        addValues()

        collectProgresses()
    }

    override fun reinitialize() {
        ratioLbl      = Label("", valueLabelStyle)
        ratioProgress = AProgressPractical(screen)
    }

    override fun reset() {
        disposeChildren()

        APracticalSettings_JGear.reset()

        reinitialize()
        addActorsOnGroup()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedGroup.addProgresses() {
        addActors(ratioProgress)
        ratioProgress.setBounds(34f, 633f, 631f, 76f)
    }

    private fun AdvancedGroup.addValueLbls() {
        addActors(ratioLbl)
        ratioLbl.setBounds(139f, 720f, 100f, 42f)
    }

    private fun AdvancedGroup.addPoints() {
        val x9 = listOf(
            34f, 113f, 189f, 268f, 347f,
            426f, 505f, 584f, 660f
        )
        x9.onEach { nx -> addPointImg(nx, 657f) }
    }

    private fun AdvancedGroup.addValues() {
        val values_m2_2 = listOf(
            "-2", "-1.5", "-1", "-0.5", "0",
            "0.5", "1", "1.5", "2"
        )

        val x9 = listOf(
            22f, 95f, 180f, 248f, 343f,
            414f, 502f, 571f, 653f
        )

        listOf(
            /*ratio*/ Static.ValuesData(633f, x9, values_m2_2),
        ).onEach { data ->
            data.listValue.onEachIndexed { index, value -> addLabelValue(value, data.listX[index], data.y) }
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun collectProgresses() {
        coroutine?.launch {
            launch { collectProgress_m2_2(ratioValue, ratioProgress, ratioLbl) { ratioValue = it } }
        }
    }

}