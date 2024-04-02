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

class APracticalSettings_JPrismatic(_screen: AdvancedScreen): AAbstractPracticalSettings(_screen) {

    companion object {
        val localAnchorAValue: Vector2 = Vector2(25f, 25f).toB2
        val localAnchorBValue: Vector2 = Vector2(85f, 85f).toB2
        var localAxisAValue  : Vector2 = Vector2(1f, 0f)
        var referenceAngleValue  : Float = 0f
        var lowerTranslationValue: Float = 0f
        var upperTranslationValue: Float = 0f
        var enableLimitValue: Boolean = false
        var motorSpeedValue   : Float = 0f
        var maxMotorForceValue: Float = 0f
        var enableMotorValue: Boolean = false

        private fun reset() {
            localAnchorAValue.set(Vector2(25f, 25f).toB2)
            localAnchorBValue.set(Vector2(85f, 85f).toB2)
            localAxisAValue.set(Vector2(1f, 0f))
            referenceAngleValue   = 0f
            lowerTranslationValue = 0f
            upperTranslationValue = 0f
            enableLimitValue = false
            motorSpeedValue    = 0f
            maxMotorForceValue = 0f
            enableMotorValue = false
        }
    }

    // Actor
    private var scrollGroup  = AScrollGroup(screen).apply { setSize(700f, 2076f) }
    private var scrollPane   = ScrollPane(scrollGroup)
    private var localAnchorA = APracticalSelectAnchorPoint(screen, assets.C_STATIC, Vector2(100f, 100f), 50f, localAnchorAValue)
    private var localAnchorB = APracticalSelectAnchorPoint(screen, assets.C_DYNAMIC, Vector2(100f, 100f), 170f, localAnchorBValue)

    private var localAxisALbl       = Label("", valueLabelStyle)
    private var referenceAngleLbl   = Label("", valueLabelStyle)
    private var lowerTranslationLbl = Label("", valueLabelStyle)
    private var upperTranslationLbl = Label("", valueLabelStyle)
    private var enableLimitLbl      = Label("", valueLabelStyle)
    private var motorSpeedLbl       = Label("", valueLabelStyle)
    private var maxMotorForceLbl    = Label("", valueLabelStyle)
    private var enableMotorLbl      = Label("", valueLabelStyle)

    private var localAxisAProgress       = AProgressPractical(screen)
    private var referenceAngleProgress   = AProgressPractical(screen)
    private var lowerTranslationProgress = AProgressPractical(screen)
    private var upperTranslationProgress = AProgressPractical(screen)
    private var motorSpeedProgress       = AProgressPractical(screen)
    private var maxMotorForceProgress    = AProgressPractical(screen)

    private var enableLimitCB = ACheckBox(screen, ACheckBox.Static.Type.PRACTICAL)
    private var enableMotorCB = ACheckBox(screen, ACheckBox.Static.Type.PRACTICAL)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()

        addScrollPane()

        scrollGroup.apply {
            addLabel(R.string.localAnchorA, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1938f, 227f, 42f))
            addLabel(R.string.localAnchorB, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1802f, 227f, 42f))
            addLabel(R.string.localAxisA, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1680f, 180f, 42f))
            addLabel(R.string.referenceAngle, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1471f, 262f, 42f))
            addLabel(R.string.lowerTranslation, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1262f, 280f, 42f))
            addLabel(R.string.upperTranslation, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 1053f, 287f, 42f))
            addLabel(R.string.enableLimit, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 844f, 201f, 42f))
            addLabel(R.string.motorSpeed, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 722f, 214f, 42f))
            addLabel(R.string.maxMotorForce, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 513f, 270f, 42f))
            addLabel(R.string.enableMotor, Static.LabelFont.Inter_Regular_35, Layout.LayoutData(34f, 304f, 220f, 42f))

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
        scrollGroup  = AScrollGroup(screen).apply { setSize(700f, 2076f) }
        scrollPane   = ScrollPane(scrollGroup)
        localAnchorA = APracticalSelectAnchorPoint(screen, assets.C_STATIC, Vector2(100f, 100f), 50f, localAnchorAValue)
        localAnchorB = APracticalSelectAnchorPoint(screen, assets.C_DYNAMIC, Vector2(100f, 100f), 170f, localAnchorBValue)

        localAxisALbl       = Label("", valueLabelStyle)
        referenceAngleLbl   = Label("", valueLabelStyle)
        lowerTranslationLbl = Label("", valueLabelStyle)
        upperTranslationLbl = Label("", valueLabelStyle)
        enableLimitLbl      = Label("", valueLabelStyle)
        motorSpeedLbl       = Label("", valueLabelStyle)
        maxMotorForceLbl    = Label("", valueLabelStyle)
        enableMotorLbl      = Label("", valueLabelStyle)

        localAxisAProgress       = AProgressPractical(screen)
        referenceAngleProgress   = AProgressPractical(screen)
        lowerTranslationProgress = AProgressPractical(screen)
        upperTranslationProgress = AProgressPractical(screen)
        motorSpeedProgress       = AProgressPractical(screen)
        maxMotorForceProgress    = AProgressPractical(screen)

        enableLimitCB = ACheckBox(screen, ACheckBox.Static.Type.PRACTICAL)
        enableMotorCB = ACheckBox(screen, ACheckBox.Static.Type.PRACTICAL)
    }

    override fun reset() {
        scrollGroup.dispose()
        disposeChildren()

        APracticalSettings_JPrismatic.reset()

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
        localAnchorA.setBounds(325f, 1893f, 300f, 132f)
        localAnchorB.setBounds(325f, 1757f, 300f, 132f)
    }

    private fun AdvancedGroup.addProgresses() {
        addActors(localAxisAProgress, referenceAngleProgress, lowerTranslationProgress, upperTranslationProgress, motorSpeedProgress, maxMotorForceProgress)
        localAxisAProgress.setBounds(34f, 1593f, 631f, 76f)
        referenceAngleProgress.setBounds(34f, 1384f, 631f, 76f)
        lowerTranslationProgress.setBounds(34f, 1175f, 631f, 76f)
        upperTranslationProgress.setBounds(34f, 966f, 631f, 76f)
        motorSpeedProgress.setBounds(34f, 635f, 631f, 76f)
        maxMotorForceProgress.setBounds(34f, 426f, 631f, 76f)
    }

    private fun AdvancedGroup.addCheckBox() {
        addActors(enableLimitCB, enableMotorCB)
        enableLimitCB.setBounds(447f, 841f, 218f, 48f)
        enableMotorCB.setBounds(447f, 301f, 218f, 48f)
    }

    private fun AdvancedGroup.addValueLbls() {
        addActors(localAxisALbl, referenceAngleLbl, lowerTranslationLbl, upperTranslationLbl, enableLimitLbl, motorSpeedLbl, maxMotorForceLbl, enableMotorLbl)
        localAxisALbl.setBounds(234f, 1680f, 380f, 42f)
        referenceAngleLbl.setBounds(316f, 1471f, 200f, 42f)
        lowerTranslationLbl.setBounds(334f, 1262f, 200f, 42f)
        upperTranslationLbl.setBounds(341f, 1053f, 200f, 42f)
        enableLimitLbl.setBounds(255f, 844f, 100f, 42f)
        motorSpeedLbl.setBounds(268f, 722f, 100f, 42f)
        maxMotorForceLbl.setBounds(324f, 513f, 200f, 42f)
        enableMotorLbl.setBounds(274f, 304f, 100f, 42f)
    }

    private fun AdvancedGroup.addPoints() {
        val x4 = listOf(189f, 347f, 505f, 660f)
        val x5 = listOf(189f, 263f, 413f, 562f, 660f)

        listOf(
            /*localAxisA*/ 1617f,
            /*referenceAngle*/ 1408f,
            /*lowerTranslation*/ 1199f,
            /*upperTranslation*/ 990f,
            /*motorSpeed*/ 659f,
        ).onEach { ny -> x4.onEach { nx -> addPointImg(nx, ny) } }

        /*maxMotorForce*/
        x5.onEach { nx -> addPointImg(nx, 450f) }
    }

    private fun AdvancedGroup.addValues() {
        val values_0_360    = listOf("0°", "90°", "180°", "270°", "360°")
        val values_m720_720 = listOf("-720°", "-360°", "0°", "360°", "720°")
        val values_m5_5     = listOf("-5", "-2.5", "0", "2.5", "5")
        val values_m100_100 = listOf("-100", "-50", "0", "50", "100")
        val values_0_5000   = listOf("0", "250", "1000", "2500", "4000", "5000")

        val x_0_360    = listOf(34f, 177f, 332f, 490f, 630f)
        val x_m720_720 = listOf(24f, 162f, 343f, 487f, 630f)
        val x_m5_5     = listOf(34f, 169f, 343f, 493f, 653f)
        val x_m100_100 = listOf(25f, 169f, 343f, 495f, 632f)
        val x_0_5000   = listOf(34f, 172f, 243f, 391f, 538f, 618f)

        listOf(
            /*localAxisA*/ Static.ValuesData(1593f, x_0_360, values_0_360),
            /*referenceAngle*/ Static.ValuesData(1384f, x_m720_720, values_m720_720),
            /*lowerTranslation*/ Static.ValuesData(1175f, x_m5_5, values_m5_5),
            /*upperTranslation*/ Static.ValuesData(966f, x_m5_5, values_m5_5),
            /*motorSpeed*/ Static.ValuesData(635f, x_m100_100, values_m100_100),
            /*maxMotorForce*/ Static.ValuesData(426f, x_0_5000, values_0_5000),
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
            /*referenceAngle*/ launch { collectProgress_m720_720_degree(referenceAngleValue, referenceAngleProgress, referenceAngleLbl) { referenceAngleValue = it } }
            /*lowerTranslation*/ launch { collectProgress_m5_5(lowerTranslationValue, lowerTranslationProgress, lowerTranslationLbl) { lowerTranslationValue = it } }
            /*upperTranslation*/ launch { collectProgress_m5_5(upperTranslationValue, upperTranslationProgress, upperTranslationLbl) { upperTranslationValue = it } }
            /*motorSpeed*/ launch { collectProgress_m100_100(motorSpeedValue, motorSpeedProgress, motorSpeedLbl) { motorSpeedValue = it } }
            /*maxMotorForce*/ launch { collectProgress_0_250_5000(maxMotorForceValue, maxMotorForceProgress, maxMotorForceLbl) { maxMotorForceValue = it } }
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