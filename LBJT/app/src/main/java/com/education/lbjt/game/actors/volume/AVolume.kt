package com.education.lbjt.game.actors.volume

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.education.lbjt.R
import com.education.lbjt.game.actors.label.AutoLanguageSpinningLabel
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.font.FontParameter.CharType
import com.education.lbjt.game.utils.runGDX

class AVolume(
    override val screen: AdvancedScreen,
    val type: Static.Type
): AdvancedGroup() {

    override var standartW = 149f

    private val themeUtil = screen.game.themeUtil
    private val parameter = FontParameter()

    private val nameLbl       = AutoLanguageSpinningLabel(screen, type.nameResId, Label.LabelStyle(screen.fontGeneratorInter_Medium.generateFont(parameter.setCharacters(CharType.LATIN_CYRILLIC).setSize(30)), GameColor.textGreen))
    private val percentLbl    = Label("0%", Label.LabelStyle(screen.fontGeneratorInter_Black.generateFont(parameter.setCharacters(CharType.NUMBERS.chars+"%").setSize(25)), GameColor.textGreen))
    private val backgroundImg = Image(getBackgroundRegionByType(type))

    override fun addActorsOnGroup() {
        addBackgroundImg()
        addNameLbl()
        addPercentLbl()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addBackgroundImg() {
        addActor(backgroundImg)
        backgroundImg.setBoundsStandart(0f,0f,149f,102f)
    }

    private fun addNameLbl() {
        addActor(nameLbl)
        nameLbl.apply {
            setBoundsStandart(10f,51f,129f,41f)
        }
    }

    private fun addPercentLbl() {
        addActor(percentLbl)
        percentLbl.apply {
            setBoundsStandart(10f,10f,129f,41f)
            setAlignment(Align.center)
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun getBackgroundRegionByType(type: Static.Type) = when(type) {
        Static.Type.QUIET       -> themeUtil.assets.VOLUME_QUIET
        Static.Type.LOUDER -> themeUtil.assets.VOLUME_LOUDER
    }

    fun updatePercent(percent: Int) {
        runGDX { percentLbl.setText("$percent%") }
    }

    object Static {
        enum class Type(val nameResId: Int) {
            QUIET(R.string.quiet), LOUDER(R.string.louder),
        }
    }

}