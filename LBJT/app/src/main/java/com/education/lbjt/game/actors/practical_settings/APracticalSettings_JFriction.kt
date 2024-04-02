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

class APracticalSettings_JFriction(_screen: AdvancedScreen): AAbstractPracticalSettings(_screen) {

    companion object {
        val localAnchorAValue: Vector2 = Vector2(25f, 25f).toB2
        val localAnchorBValue: Vector2 = Vector2(130f, 53f).toB2
        var maxForceValue : Float = 500f
        var maxTorqueValue: Float = 500f

        private fun reset() {
            localAnchorAValue.set(Vector2(25f, 25f).toB2)
            localAnchorBValue.set(Vector2(130f, 53f).toB2)
            maxForceValue  = 500f
            maxTorqueValue = 500f
        }
    }

    // Actor
    private var localAnchorA = APracticalSelectAnchorPoint(screen, assets.C_STATIC, Vector2(100f, 100f), 50f, localAnchorAValue)
    private var localAnchorB = APracticalSelectAnchorPoint(screen, assets.H_DYNAMIC, Vector2(128f, 52f), 260f, localAnchorBValue)

    private var maxForceLbl  = Label("", valueLabelStyle)
    private var maxTorqueLbl = Label("", valueLabelStyle)

    private var maxForceProgress  = AProgressPractical(screen)
    private var maxTorqueProgress = AProgressPractical(screen)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addLabel(R.string.localAnchorA, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1021f, 227f, 42f))
        addLabel(R.string.localAnchorB, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 885f, 227f, 42f))
        addLabel(R.string.maxForce, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 763f, 172f, 42f))
        addLabel(R.string.maxTorque, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 554f, 192f, 42f))

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

        maxForceLbl  = Label("", valueLabelStyle)
        maxTorqueLbl = Label("", valueLabelStyle)

        maxForceProgress  = AProgressPractical(screen)
        maxTorqueProgress = AProgressPractical(screen)
    }

    override fun reset() {
        disposeChildren()

        APracticalSettings_JFriction.reset()

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
        addActors(maxForceProgress, maxTorqueProgress)
        maxForceProgress.setBounds(34f, 676f, 631f, 76f)
        maxTorqueProgress.setBounds(34f, 467f, 631f, 76f)
    }

    private fun AdvancedGroup.addValueLbls() {
        addActors(maxForceLbl, maxTorqueLbl)
        maxForceLbl.setBounds(226f, 763f, 200f, 42f)
        maxTorqueLbl.setBounds(246f, 554f, 200f, 42f)
    }

    private fun AdvancedGroup.addPoints() {
        val x5 = listOf(189f, 263f, 413f, 562f, 660f)
        listOf(
            /*maxForce*/ 700f,
            /*maxTorque*/ 491f,
        ).onEach { ny -> x5.onEach { nx -> addPointImg(nx, ny) } }
    }

    private fun AdvancedGroup.addValues() {
        val values_0_5000 = listOf("0", "250", "1000", "2500", "4000", "5000")

        val x_0_5000 = listOf(34f, 172f, 243f, 391f, 538f, 618f)

        listOf(
            /*maxForce*/ Static.ValuesData(676f, x_0_5000, values_0_5000),
            /*maxTorque*/ Static.ValuesData(467f, x_0_5000, values_0_5000),
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
            /*maxForce*/ launch { collectProgress_0_250_5000(maxForceValue, maxForceProgress, maxForceLbl) { maxForceValue = it } }
            /*maxTorque*/ launch { collectProgress_0_250_5000(maxTorqueValue, maxTorqueProgress, maxTorqueLbl) { maxTorqueValue = it } }
        }
    }

}