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

class APracticalSettings_JWheel(_screen: AdvancedScreen): AAbstractPracticalSettings(_screen) {

    companion object {
        val localAnchorAValue: Vector2 = Vector2(25f, 25f).toB2
        val localAnchorBValue: Vector2 = Vector2(85f, 85f).toB2
        var localAxisAValue  : Vector2 = Vector2(1f, 0f)
        var motorSpeedValue    : Float = 0f
        var maxMotorTorqueValue: Float = 0f
        var enableMotorValue: Boolean = false
        var frequencyHzValue : Float = 2f
        var dampingRatioValue: Float = 0.7f

        private fun reset() {
            localAnchorAValue.set(Vector2(25f, 25f).toB2)
            localAnchorBValue.set(Vector2(85f, 85f).toB2)
            localAxisAValue.set(Vector2(1f, 0f))
            motorSpeedValue     = 0f
            maxMotorTorqueValue = 0f
            enableMotorValue = false
            frequencyHzValue  = 2f
            dampingRatioValue = 0.7f
        }
    }

    // Actor
    private var scrollGroup  = AScrollGroup(screen).apply { setSize(700f, 1689f) }
    private var scrollPane   = ScrollPane(scrollGroup)
    private var localAnchorA = APracticalSelectAnchorPoint(screen, assets.C_STATIC, Vector2(100f, 100f), 50f, localAnchorAValue)
    private var localAnchorB = APracticalSelectAnchorPoint(screen, assets.C_DYNAMIC, Vector2(100f, 100f), 170f, localAnchorBValue)

    private var localAxisALbl     = Label("", valueLabelStyle)
    private var motorSpeedLbl     = Label("", valueLabelStyle)
    private var maxMotorTorqueLbl = Label("", valueLabelStyle)
    private var enableMotorLbl    = Label("", valueLabelStyle)
    private var frequencyHzLbl    = Label("", valueLabelStyle)
    private var dampingRatioLbl   = Label("", valueLabelStyle)

    private var localAxisAProgress     = AProgressPractical(screen)
    private var motorSpeedProgress     = AProgressPractical(screen)
    private var maxMotorTorqueProgress = AProgressPractical(screen)
    private var frequencyHzProgress    = AProgressPractical(screen)
    private var dampingRatioProgress   = AProgressPractical(screen)

    private var enableMotorCB = ACheckBox(screen, ACheckBox.Static.Type.PRACTICAL)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addScrollPane()

        scrollGroup.apply {
            addLabel(R.string.localAnchorA, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1551f, 227f, 42f))
            addLabel(R.string.localAnchorB, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1415f, 227f, 42f))
            addLabel(R.string.localAxisA, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1293f, 180f, 42f))
            addLabel(R.string.motorSpeed, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1084f, 214f, 42f))
            addLabel(R.string.maxMotorTorque, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 875f, 291f, 42f))
            addLabel(R.string.enableMotor, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 666f, 220f, 42f))
            addLabel(R.string.frequencyHz, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 544f, 222f, 42f))
            addLabel(R.string.dampingRatio, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 335f, 237f, 42f))

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
        scrollGroup  = AScrollGroup(screen).apply { setSize(700f, 1689f) }
        scrollPane   = ScrollPane(scrollGroup)
        localAnchorA = APracticalSelectAnchorPoint(screen, assets.C_STATIC, Vector2(100f, 100f), 50f, localAnchorAValue)
        localAnchorB = APracticalSelectAnchorPoint(screen, assets.C_DYNAMIC, Vector2(100f, 100f), 170f, localAnchorBValue)

        localAxisALbl     = Label("", valueLabelStyle)
        motorSpeedLbl     = Label("", valueLabelStyle)
        maxMotorTorqueLbl = Label("", valueLabelStyle)
        enableMotorLbl    = Label("", valueLabelStyle)
        frequencyHzLbl    = Label("", valueLabelStyle)
        dampingRatioLbl   = Label("", valueLabelStyle)

        localAxisAProgress     = AProgressPractical(screen)
        motorSpeedProgress     = AProgressPractical(screen)
        maxMotorTorqueProgress = AProgressPractical(screen)
        frequencyHzProgress    = AProgressPractical(screen)
        dampingRatioProgress   = AProgressPractical(screen)

        enableMotorCB = ACheckBox(screen, ACheckBox.Static.Type.PRACTICAL)
    }

    override fun reset() {
        scrollGroup.dispose()
        disposeChildren()

        APracticalSettings_JWheel.reset()

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
        localAnchorA.setBounds(325f, 1506f, 300f, 132f)
        localAnchorB.setBounds(325f, 1370f, 300f, 132f)
    }

    private fun AdvancedGroup.addProgresses() {
        addActors(localAxisAProgress, motorSpeedProgress, maxMotorTorqueProgress, frequencyHzProgress, dampingRatioProgress)
        localAxisAProgress.setBounds(34f, 1206f, 631f, 76f)
        motorSpeedProgress.setBounds(34f, 997f, 631f, 76f)
        maxMotorTorqueProgress.setBounds(34f, 788f, 631f, 76f)
        frequencyHzProgress.setBounds(34f, 457f, 631f, 76f)
        dampingRatioProgress.setBounds(34f, 248f, 631f, 76f)
    }

    private fun AdvancedGroup.addCheckBox() {
        addActors(enableMotorCB)
        enableMotorCB.setBounds(447f, 663f, 218f, 48f)
    }

    private fun AdvancedGroup.addValueLbls() {
        addActors(localAxisALbl, motorSpeedLbl, maxMotorTorqueLbl, enableMotorLbl, frequencyHzLbl, dampingRatioLbl)
        localAxisALbl.setBounds(234f, 1293f, 380f, 42f)
        motorSpeedLbl.setBounds(268f, 1084f, 100f, 42f)
        maxMotorTorqueLbl.setBounds(345f, 875f, 100f, 42f)
        enableMotorLbl.setBounds(274f, 666f, 100f, 42f)
        frequencyHzLbl.setBounds(276f, 544f, 100f, 42f)
        dampingRatioLbl.setBounds(291f, 335f, 100f, 42f)
    }

    private fun AdvancedGroup.addPoints() {
        val x4 = listOf(189f, 347f, 505f, 660f)
        listOf(
            /*localAxisA*/ 1230f,
            /*motorSpeed*/ 1021f,
            /*maxMotorTorque*/ 812f,
        ).onEach { ny -> x4.onEach { nx -> addPointImg(nx, ny) } }

        val x3 = listOf(221f, 418f, 660f)
        listOf(
            /*frequencyHz*/ 481f,
            /*dampingRatio*/ 272f,
        ).onEach { ny -> x3.onEach { nx -> addPointImg(nx, ny) } }
    }

    private fun AdvancedGroup.addValues() {
        val values_0_360    = listOf("0°", "90°", "180°", "270°", "360°")
        val values_m720_720 = listOf("-720°", "-360°", "0°", "360°", "720°")
        val values_0_1k     = listOf("0", "250", "500", "750", "1000")
        val values_0_10     = listOf("0", "1.0", "5.0", "10")

        val x_0_360    = listOf(34f, 177f, 332f, 490f, 630f)
        val x_m720_720 = listOf(24f, 162f, 343f, 487f, 630f)
        val x_0_1k     = listOf(34f, 172f, 331f, 491f, 618f)
        val x_0_10     = listOf(34f, 209f, 407f, 643f)

        listOf(
            /*localAxisA*/ Static.ValuesData(1206f, x_0_360, values_0_360),
            /*motorSpeed*/ Static.ValuesData(997f, x_m720_720, values_m720_720),
            /*maxMotorForce*/ Static.ValuesData(788f, x_0_1k, values_0_1k),
            /*frequencyHz*/ Static.ValuesData(457f, x_0_10, values_0_10),
            /*dampingRatio*/ Static.ValuesData(248f, x_0_10, values_0_10),
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
            /*localAnchorA*/ launch { collectProgress_localAxisA_0_360(localAxisAValue, localAxisAProgress, localAxisALbl) { localAxisAValue.set(it) } }
            /*motorSpeed*/ launch { collectProgress_m720_720_degree(motorSpeedValue, motorSpeedProgress, motorSpeedLbl) { motorSpeedValue = it } }
            /*maxMotorTorque*/ launch { collectProgress_0_1k(maxMotorTorqueValue, maxMotorTorqueProgress, maxMotorTorqueLbl) { maxMotorTorqueValue = it } }
            /*frequencyHz*/ launch { collectProgress_0_1_10(frequencyHzValue, frequencyHzProgress, frequencyHzLbl) { frequencyHzValue = it } }
            /*dampingRatio*/ launch { collectProgress_0_1_10(dampingRatioValue, dampingRatioProgress, dampingRatioLbl) { dampingRatioValue = it } }
        }
    }

    private fun listenCheckBox() {
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