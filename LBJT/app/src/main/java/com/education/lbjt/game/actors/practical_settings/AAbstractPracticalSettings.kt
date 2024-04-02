package com.education.lbjt.game.actors.practical_settings

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.education.lbjt.game.actors.practical_settings.actors.AProgressPractical
import com.education.lbjt.game.utils.DEGTORAD
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.Layout
import com.education.lbjt.game.utils.RADTODEG
import com.education.lbjt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.actor.disable
import com.education.lbjt.game.utils.actor.enable
import com.education.lbjt.game.utils.actor.setBounds
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.runGDX
import kotlin.math.absoluteValue
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

abstract class AAbstractPracticalSettings(final override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        private const val ONE_PERCENT_0_10k = 10_000f / 100f

        private const val ONE_PERCENT_0_1k = 1_000f / 100f

        private const val ONE_PERCENT_0_2 = 2f / 100f

        private const val ONE_PERCENT_0_5 = 5f / 100f

        private const val ONE_PERCENT_0_10 = 10f / 100f

        private const val ONE_PERCENT_0_20 = 20f / 100f

        private const val NEGATIVE_m5_10             = 30
        private const val POSITIVE_ONE_PERCENT_m5_10 = 10f / 70f
        private const val NEGATIVE_ONE_PERCENT_m5_10 = 5f / 30f

        private const val START_PERCENT_0_1_10     = 30
        private const val RIGHT_ONE_PERCENT_0_1_10 = 9f / 70f
        private const val LEFT_ONE_PERCENT_0_1_10  = 1f / 30f

        private const val START_PERCENT_0_250_5000     = 25
        private const val RIGHT_ONE_PERCENT_0_250_5000 = 4750f / 75f
        private const val LEFT_ONE_PERCENT_0_250_5000  = 250f / 25f

        private const val START_PERCENT_m720_0 = 50
        private const val ONE_PERCENT_m720_720 = 720f / 50f

        private const val START_PERCENT_m5_0 = 50
        private const val ONE_PERCENT_m5_5 = 5f / 50f

        private const val START_PERCENT_m100_0 = 50
        private const val ONE_PERCENT_m100_100 = 100f / 50f

        private const val ONE_PERCENT_0_360 = 360f / 100f

    }

    // Font
    private val parameter = FontParameter().setCharacters(FontParameter.CharType.ALL)

    private val fontInter_Black_35   = screen.fontGeneratorInter_Black.generateFont(parameter.setSize(35))
    private val fontInter_Regular_35 = screen.fontGeneratorInter_Regular.generateFont(parameter.setSize(35))
    private val fontInter_Regular_20 = screen.fontGeneratorInter_Regular.generateFont(parameter.setSize(20))

    // Actor
    val valueLabelStyle = Label.LabelStyle(fontInter_Black_35, GameColor.textRed)

    // Field
    protected val assets = screen.game.themeUtil.assets

    var isOpen = false
        private set


    override fun addActorsOnGroup() {
        disable()
        color.a = 0f
    }

    abstract fun reinitialize()

    abstract fun reset()

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun showAndEnabled(block: () -> Unit = {}) {
        animShow(TIME_ANIM_SCREEN_ALPHA) {
            isOpen = true
            enable()
            block()
        }
    }

    fun hideAndDisabled(block: () -> Unit = {}) {
        animHide(TIME_ANIM_SCREEN_ALPHA) {
            isOpen = false
            disable()
            block()
        }
    }

    // ---------------------------------------------------
    // Tools Actor
    // ---------------------------------------------------

    fun AdvancedGroup.addLabel(
        stringId  : Int,
        labelFont : Static.LabelFont,
        layoutData: Layout.LayoutData,
        color     : Color = GameColor.textGreen,
        align     : Int = Align.left,
    ) {
        addActor(Label(screen.game.languageUtil.getStringResource(stringId), Label.LabelStyle(labelFont.getFont(), color)).also {
            it.setBounds(layoutData)
            it.setAlignment(align)
        })
    }

    fun AdvancedGroup.addLabelValue(
        string: String,
        x: Float, y: Float,
    ) {
        addActor(Label(string, Label.LabelStyle(fontInter_Regular_20, GameColor.textGreen)).also {
            it.disable()
            it.setPosition(x, y)
        })
    }

    fun AdvancedGroup.addPointImg(x: Float, y: Float) {
        addActor(Image(screen.game.themeUtil.assets.PRACTICAL_PROGRESS_POINT).apply {
            disable()
            setBounds(x, y, 5f, 5f)
        })
    }

    // ---------------------------------------------------
    // Collect
    // ---------------------------------------------------

    suspend fun collectProgress_0_2(currentValue: Float, progress: AProgressPractical, label: Label, block: (Float) -> Unit) {
        var tmpValue = 0f

        progress.apply {
            setProgressPercent(currentValue / ONE_PERCENT_0_2)
            progressPercentFlow.collect { progress ->
                runGDX {
                    tmpValue = progress * ONE_PERCENT_0_2
                    label.setText(String.format("%.2f", tmpValue).replace(',', '.'))
                    block(tmpValue)
                }
            }
        }
    }

    suspend fun collectProgress_0_10(currentValue: Float, progress: AProgressPractical, label: Label, block: (Float) -> Unit) {
        var tmpValue = 0f

        progress.apply {
            setProgressPercent(currentValue / ONE_PERCENT_0_10)
            progressPercentFlow.collect { progress ->
                runGDX {
                    tmpValue = progress * ONE_PERCENT_0_10
                    label.setText(String.format("%.1f", tmpValue).replace(',', '.'))
                    block(tmpValue)
                }
            }
        }
    }

    suspend fun collectProgress_0_5(currentValue: Float, progress: AProgressPractical, label: Label, block: (Float) -> Unit) {
        var tmpValue = 0f

        progress.apply {
            setProgressPercent(currentValue / ONE_PERCENT_0_5)
            progressPercentFlow.collect { progress ->
                runGDX {
                    tmpValue = progress * ONE_PERCENT_0_5
                    label.setText(String.format("%.1f", tmpValue).replace(',', '.'))
                    block(tmpValue)
                }
            }
        }
    }

    suspend fun collectProgress_0_20(currentValue: Float, progress: AProgressPractical, label: Label, block: (Float) -> Unit) {
        var tmpValue = 0f

        progress.apply {
            setProgressPercent(currentValue / ONE_PERCENT_0_20)
            progressPercentFlow.collect { progress ->
                runGDX {
                    tmpValue = progress * ONE_PERCENT_0_20
                    label.setText("${tmpValue.roundToInt()}")
                    block(tmpValue)
                }
            }
        }
    }

    suspend fun collectProgress_0_1_10(currentValue: Float, progress: AProgressPractical, label: Label, block: (Float) -> Unit) {
        var tmpValue = 0f

        progress.apply {
            tmpValue = if (currentValue >= 1) {
                START_PERCENT_0_1_10 + ((currentValue - 1) / RIGHT_ONE_PERCENT_0_1_10)
            } else {
                (currentValue / LEFT_ONE_PERCENT_0_1_10)
            }
            setProgressPercent(tmpValue)

            progressPercentFlow.collect { progress ->
                runGDX {
                    if (progress >= START_PERCENT_0_1_10) {
                        tmpValue = ((progress - START_PERCENT_0_1_10) * RIGHT_ONE_PERCENT_0_1_10) + 1
                        label.setText(String.format("%.1f", tmpValue).replace(',', '.'))
                        block(tmpValue)
                    } else {
                        tmpValue = progress * LEFT_ONE_PERCENT_0_1_10
                        label.setText(String.format("%.1f", tmpValue).replace(',', '.'))
                        block(tmpValue)
                    }
                }
            }
        }
    }

    suspend fun collectProgress_0_250_5000(currentValue: Float, progress: AProgressPractical, label: Label, block: (Float) -> Unit) {
        var tmpValue = 0f

        progress.apply {
            tmpValue = if (currentValue >= 250) {
                START_PERCENT_0_250_5000 + ((currentValue - 250) / RIGHT_ONE_PERCENT_0_250_5000)
            } else {
                (currentValue / LEFT_ONE_PERCENT_0_250_5000)
            }
            setProgressPercent(tmpValue)

            progressPercentFlow.collect { progress ->
                runGDX {
                    if (progress >= START_PERCENT_0_250_5000) {
                        tmpValue = ((progress - START_PERCENT_0_250_5000) * RIGHT_ONE_PERCENT_0_250_5000) + 250
                        label.setText(tmpValue.roundToInt())
                        block(tmpValue)
                    } else {
                        tmpValue = progress * LEFT_ONE_PERCENT_0_250_5000
                        label.setText(tmpValue.roundToInt())
                        block(tmpValue)
                    }
                }
            }
        }
    }

    suspend fun collectProgress_0_10k(currentValue: Float, progress: AProgressPractical, label: Label, block: (Float) -> Unit) {
        var tmpValue = 0f

        progress.apply {
            setProgressPercent(currentValue / 100f)
            progressPercentFlow.collect { progress ->
                runGDX {
                    tmpValue = progress * ONE_PERCENT_0_10k
                    label.setText("${tmpValue.roundToInt()}")
                    block(tmpValue)
                }
            }
        }
    }

    suspend fun collectProgress_m5_10(currentValue: Float, progress: AProgressPractical, label: Label, block: (Float) -> Unit) {
        var tmpValue = 0f

        progress.apply {
            tmpValue = if (currentValue >= 0) {
                NEGATIVE_m5_10 + (currentValue / POSITIVE_ONE_PERCENT_m5_10)
            } else {
                NEGATIVE_m5_10 - (currentValue.absoluteValue / NEGATIVE_ONE_PERCENT_m5_10)
            }
            setProgressPercent(tmpValue)

            progressPercentFlow.collect { progress ->
                runGDX {
                    if (progress >= NEGATIVE_m5_10) {
                        tmpValue = (progress - NEGATIVE_m5_10) * POSITIVE_ONE_PERCENT_m5_10
                        label.setText(String.format("%.1f", tmpValue).replace(',', '.'))
                        block(tmpValue)
                    } else {
                        tmpValue = (NEGATIVE_m5_10 - progress) * NEGATIVE_ONE_PERCENT_m5_10
                        label.setText("-" + String.format("%.1f", tmpValue).replace(',', '.'))
                        block(-tmpValue)
                    }
                }
            }
        }
    }

    suspend fun collectProgress_0_1k(currentValue: Float, progress: AProgressPractical, label: Label, block: (Float) -> Unit) {
        var tmpValue = 0f

        progress.apply {
            setProgressPercent(currentValue / 100f)
            progressPercentFlow.collect { progress ->
                runGDX {
                    tmpValue = progress * ONE_PERCENT_0_1k
                    label.setText("${tmpValue.roundToInt()}")
                    block(tmpValue)
                }
            }
        }
    }

    suspend fun collectProgress_m5_5(currentValue: Float, progress: AProgressPractical, label: Label, block: (Float) -> Unit) {
        var tmpValue = 0f

        progress.apply {
            tmpValue = if (currentValue >= 0) {
                START_PERCENT_m5_0 + (currentValue / ONE_PERCENT_m5_5)
            } else {
                START_PERCENT_m5_0 - (currentValue.absoluteValue / ONE_PERCENT_m5_5)
            }
            setProgressPercent(tmpValue)

            progressPercentFlow.collect { progress ->
                runGDX {
                    if (progress >= START_PERCENT_m5_0) {
                        tmpValue = (progress - START_PERCENT_m5_0) * ONE_PERCENT_m5_5
                        label.setText(String.format("%.1f", tmpValue).replace(',', '.'))
                        block(tmpValue)
                    } else {
                        tmpValue = (START_PERCENT_m5_0 - progress) * ONE_PERCENT_m5_5
                        label.setText("-" + String.format("%.1f", tmpValue).replace(',', '.'))
                        block(-tmpValue)
                    }
                }
            }
        }
    }

    suspend fun collectProgress_m100_100(currentValue: Float, progress: AProgressPractical, label: Label, block: (Float) -> Unit) {
        var tmpValue = 0f

        progress.apply {
            tmpValue = if (currentValue >= 0) {
                START_PERCENT_m100_0 + (currentValue / ONE_PERCENT_m100_100)
            } else {
                START_PERCENT_m100_0 - (currentValue.absoluteValue / ONE_PERCENT_m100_100)
            }
            setProgressPercent(tmpValue)

            progressPercentFlow.collect { progress ->
                runGDX {
                    if (progress >= START_PERCENT_m100_0) {
                        tmpValue = (progress - START_PERCENT_m100_0) * ONE_PERCENT_m100_100
                        label.setText(tmpValue.roundToInt())
                        block(tmpValue)
                    } else {
                        tmpValue = (START_PERCENT_m100_0 - progress) * ONE_PERCENT_m100_100
                        label.setText("-" + tmpValue.roundToInt())
                        block(-tmpValue)
                    }
                }
            }
        }
    }

    suspend fun collectProgress_m720_720_degree(currentValue: Float, progress: AProgressPractical, label: Label, block: (Float) -> Unit) {
        var tmpValue = 0f

        progress.apply {
            tmpValue = if (currentValue >= 0) {
                START_PERCENT_m720_0 + (currentValue / ONE_PERCENT_m720_720)
            } else {
                START_PERCENT_m720_0 - (currentValue.absoluteValue / ONE_PERCENT_m720_720)
            }
            setProgressPercent(tmpValue)

            progressPercentFlow.collect { progress ->
                runGDX {
                    if (progress >= START_PERCENT_m720_0) {
                        tmpValue = (progress - START_PERCENT_m720_0) * ONE_PERCENT_m720_720
                        label.setText(String.format("%.1f", tmpValue).replace(',', '.') + "°")
                        block(tmpValue)
                    } else {
                        tmpValue = (START_PERCENT_m720_0 - progress) * ONE_PERCENT_m720_720
                        label.setText("-" + String.format("%.1f", tmpValue).replace(',', '.') + "°")
                        block(-tmpValue)
                    }
                }
            }
        }
    }

    suspend fun collectProgress_localAxisA_0_360(currentValue: Vector2, progress: AProgressPractical, label: Label, block: (Vector2) -> Unit) {
        var tmpValue = 0f
        var strX     = ""
        var strY     = ""

        progress.apply {
            val currentDegree = acos(currentValue.x) * RADTODEG
            setProgressPercent(currentDegree / 100f)
            progressPercentFlow.collect { progress ->
                runGDX {
                    tmpValue = (progress * ONE_PERCENT_0_360) * DEGTORAD

                    strX = String.format("%.2f", cos(tmpValue)).replace(',', '.')
                    strY = String.format("%.2f", sin(tmpValue)).replace(',', '.')

                    label.setText("${(tmpValue * RADTODEG).roundToInt()}° (x=$strX,y=$strY)")
                    block(Vector2(strX.toFloat(), strY.toFloat()))
                }
            }
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun Static.LabelFont.getFont(): BitmapFont = when(this) {
        Static.LabelFont.Inter_Regular_35 -> fontInter_Regular_35
        Static.LabelFont.Inter_Regular_20 -> fontInter_Regular_20
    }

    // ---------------------------------------------------
    // Static
    // ---------------------------------------------------

    object Static {
        data class ValuesData(val y: Float, val listX: List<Float>, val listValue: List<String>)
        enum class LabelFont {
            Inter_Regular_35,
            Inter_Regular_20,
        }
    }

}