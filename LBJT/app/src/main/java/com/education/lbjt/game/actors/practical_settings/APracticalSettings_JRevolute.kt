package com.education.lbjt.game.actors.practical_settings

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.education.lbjt.R
import com.education.lbjt.game.actors.checkbox.ACheckBox
import com.education.lbjt.game.actors.practical_settings.actors.APracticalSelectAnchorPoint
import com.education.lbjt.game.actors.practical_settings.actors.AProgressPractical
import com.education.lbjt.game.actors.scroll.AScrollGroup
import com.education.lbjt.game.utils.Layout
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.toB2
import kotlinx.coroutines.launch

class APracticalSettings_JRevolute(_screen: AdvancedScreen): AAbstractPracticalSettings(_screen) {

    companion object {
        val localAnchorAValue: Vector2 = Vector2(35f, 85f).toB2
        val localAnchorBValue: Vector2 = Vector2(85f, 35f).toB2
        var referenceAngleValue: Float = 0f
        var lowerAngleValue    : Float = 0f
        var upperAngleValue    : Float = 0f
        var enableLimitValue   : Boolean = false
        var motorSpeedValue    : Float = 0f
        var maxMotorTorqueValue: Float = 0f
        var enableMotorValue   : Boolean = false

        private fun reset() {
            localAnchorAValue.set(Vector2(35f, 85f).toB2)
            localAnchorBValue.set(Vector2(85f, 35f).toB2)
            referenceAngleValue = 0f
            lowerAngleValue     = 0f
            upperAngleValue     = 0f
            enableLimitValue    = false
            motorSpeedValue     = 0f
            maxMotorTorqueValue = 0f
            enableMotorValue    = false
        }
    }

    // Actor
    private var scrollGroup  = AScrollGroup(screen).apply { setSize(700f, 1814f) }
    private var scrollPane   = ScrollPane(scrollGroup)
    private var localAnchorA = APracticalSelectAnchorPoint(screen, assets.V_STATIC, Vector2(52f, 128f), 70f, localAnchorAValue)
    private var localAnchorB = APracticalSelectAnchorPoint(screen, assets.H_DYNAMIC, Vector2(128f, 52f), 170f, localAnchorBValue)

    private var referenceAngleLbl = Label("", valueLabelStyle)
    private var lowerAngleLbl     = Label("", valueLabelStyle)
    private var upperAngleLbl     = Label("", valueLabelStyle)
    private var enableLimitLbl    = Label("", valueLabelStyle)
    private var motorSpeedLbl     = Label("", valueLabelStyle)
    private var maxMotorTorqueLbl = Label("", valueLabelStyle)
    private var enableMotorLbl    = Label("", valueLabelStyle)

    private var referenceAngleProgress = AProgressPractical(screen)
    private var lowerAngleProgress     = AProgressPractical(screen)
    private var upperAngleProgress     = AProgressPractical(screen)
    private var motorSpeedProgress     = AProgressPractical(screen)
    private var maxMotorTorqueProgress = AProgressPractical(screen)

    private var enableLimitCB = ACheckBox(screen, ACheckBox.Static.Type.PRACTICAL)
    private var enableMotorCB = ACheckBox(screen, ACheckBox.Static.Type.PRACTICAL)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addScrollPane()

        scrollGroup.apply {
            addLabel(R.string.localAnchorA, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1676f, 227f, 42f))
            addLabel(R.string.localAnchorB, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1540f, 227f, 42f))
            addLabel(R.string.referenceAngle, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1418f, 262f, 42f))
            addLabel(R.string.lowerAngle, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1209f, 193f, 42f))
            addLabel(R.string.upperAngle, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1000f, 199f, 42f))
            addLabel(R.string.enableLimit, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 791f, 201f, 42f))
            addLabel(R.string.motorSpeed, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 669f, 214f, 42f))
            addLabel(R.string.maxMotorTorque, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 460f, 291f, 42f))
            addLabel(R.string.enableMotor, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 251f, 220f, 42f))

            addLocalAnchors()
            addProgresses()
            addCheckBox()
            addValueLbls()
            addPoints()
            addValues()
        }

        collectProgresses()
        listenCheckBox()
    }

    override fun reinitialize() {
        scrollGroup  = AScrollGroup(screen).apply { setSize(700f, 1814f) }
        scrollPane   = ScrollPane(scrollGroup)
        localAnchorA = APracticalSelectAnchorPoint(screen, assets.V_STATIC, Vector2(52f, 128f), 70f, localAnchorAValue)
        localAnchorB = APracticalSelectAnchorPoint(screen, assets.H_DYNAMIC, Vector2(128f, 52f), 170f, localAnchorBValue)

        referenceAngleLbl = Label("", valueLabelStyle)
        lowerAngleLbl     = Label("", valueLabelStyle)
        upperAngleLbl     = Label("", valueLabelStyle)
        enableLimitLbl    = Label("", valueLabelStyle)
        motorSpeedLbl     = Label("", valueLabelStyle)
        maxMotorTorqueLbl = Label("", valueLabelStyle)
        enableMotorLbl    = Label("", valueLabelStyle)

        referenceAngleProgress = AProgressPractical(screen)
        lowerAngleProgress     = AProgressPractical(screen)
        upperAngleProgress     = AProgressPractical(screen)
        motorSpeedProgress     = AProgressPractical(screen)
        maxMotorTorqueProgress = AProgressPractical(screen)

        enableLimitCB = ACheckBox(screen, ACheckBox.Static.Type.PRACTICAL)
        enableMotorCB = ACheckBox(screen, ACheckBox.Static.Type.PRACTICAL)
    }

    override fun reset() {
        scrollGroup.dispose()
        disposeChildren()

        APracticalSettings_JRevolute.reset()

        reinitialize()
        addActorsOnGroup()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addScrollPane() {
        addActor(scrollPane)
        scrollPane.setBounds(0f, 10f, 700f, 1149f)
    }

    private fun AdvancedGroup.addLocalAnchors() {
        addActors(localAnchorA, localAnchorB)
        localAnchorA.setBounds(350f, 1617f, 250f, 160f)
        localAnchorB.setBounds(350f, 1518f, 250f, 85f)
    }

    private fun AdvancedGroup.addProgresses() {
        addActors(referenceAngleProgress, lowerAngleProgress, upperAngleProgress, motorSpeedProgress, maxMotorTorqueProgress)
        referenceAngleProgress.setBounds(34f, 1331f, 631f, 76f)
        lowerAngleProgress.setBounds(34f, 1122f, 631f, 76f)
        upperAngleProgress.setBounds(34f, 913f, 631f, 76f)
        motorSpeedProgress.setBounds(34f, 582f, 631f, 76f)
        maxMotorTorqueProgress.setBounds(34f, 373f, 631f, 76f)
    }

    private fun AdvancedGroup.addCheckBox() {
        addActors(enableLimitCB, enableMotorCB)
        enableLimitCB.setBounds(447f, 778f, 218f, 48f)
        enableMotorCB.setBounds(447f, 248f, 218f, 48f)
    }

    private fun AdvancedGroup.addValueLbls() {
        addActors(referenceAngleLbl, lowerAngleLbl, upperAngleLbl, enableLimitLbl, motorSpeedLbl, maxMotorTorqueLbl, enableMotorLbl)
        referenceAngleLbl.setBounds(316f, 1418f, 200f, 42f)
        lowerAngleLbl.setBounds(247f, 1209f, 200f, 42f)
        upperAngleLbl.setBounds(253f, 1000f, 200f, 42f)
        enableLimitLbl.setBounds(255f, 791f, 100f, 42f)
        motorSpeedLbl.setBounds(268f, 669f, 100f, 42f)
        maxMotorTorqueLbl.setBounds(345f, 460f, 100f, 42f)
        enableMotorLbl.setBounds(274f, 251f, 100f, 42f)
    }

    private fun AdvancedGroup.addPoints() {
        val degreesX = listOf(189f, 347f, 505f, 660f)

        listOf(
            // referenceAngle
            1355f,
            // lowerAngle
            1146f,
            // upperAngle
            937f,
            // motorSpeed
            606f,
            // maxMotorTorque
            397f,
        ).onEach { ny -> degreesX.onEach { nx -> addPointImg(nx, ny) } }
    }

    private fun AdvancedGroup.addValues() {
        val degrees  = listOf("-720°", "-360°", "0°", "360°", "720°")
        val degreesX = listOf(24f, 162f, 343f, 487f, 630f)

        listOf(
            // referenceAngle
            1331f,
            // lowerAngle
            1122f,
            // upperAngle
            913f,
            // motorSpeed
            582f,
        ).onEach { ny ->
            repeat(degrees.size) { index ->
                addLabelValue(degrees[index], degreesX[index], ny)
            }
        }

        // maxMotorTorque
        val x_0_1k = listOf(34f, 172f, 331f, 491f, 618f)
        listOf("0", "250", "500", "750", "1000").onEachIndexed { index, value ->
            addLabelValue(value, x_0_1k[index], 373f)
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun collectProgresses() {
        coroutine?.launch {
            // referenceAngle
            launch { collectProgress_m720_720_degree(referenceAngleValue, referenceAngleProgress, referenceAngleLbl) { referenceAngleValue = it } }
            // lowerAngle
            launch { collectProgress_m720_720_degree(lowerAngleValue, lowerAngleProgress, lowerAngleLbl) { lowerAngleValue = it } }
            // upperAngle
            launch { collectProgress_m720_720_degree(upperAngleValue, upperAngleProgress, upperAngleLbl) { upperAngleValue = it } }
            // motorSpeed
            launch { collectProgress_m720_720_degree(motorSpeedValue, motorSpeedProgress, motorSpeedLbl) { motorSpeedValue = it } }
            // maxMotorTorque
            launch { collectProgress_0_1k(maxMotorTorqueValue, maxMotorTorqueProgress, maxMotorTorqueLbl) { maxMotorTorqueValue = it } }
        }
    }

    private fun listenCheckBox() {
        enableLimitCB.apply {
            enableLimitLbl.setText("$enableLimitValue")
            if (enableLimitValue) check(false)
            setOnCheckListener { isCheck ->
                enableLimitValue = isCheck
                enableLimitLbl.setText("$enableLimitValue")
            }
        }

        enableMotorCB.apply {
            enableMotorLbl.setText("$enableMotorValue")
            if (enableMotorValue) check(false)
            setOnCheckListener { isCheck ->
                enableMotorValue = isCheck
                enableMotorLbl.setText("$enableMotorValue")
            }
        }
    }

}