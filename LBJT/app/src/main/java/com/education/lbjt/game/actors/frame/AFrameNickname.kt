package com.education.lbjt.game.actors.frame

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.education.lbjt.R
import com.education.lbjt.game.actors.label.ASpinningLabel
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.font.FontParameter.CharType

class AFrameNickname(override val screen: AdvancedScreen): AdvancedGroup() {

    override var standartW = 501f

    private val themeUtil   = screen.game.themeUtil

    private val parameter = FontParameter()

    private val frameImg = Image(themeUtil.assets.PANEL)
    private val textLbl  = ASpinningLabel(screen, screen.game.languageUtil.getStringResource(R.string.nickname), Label.LabelStyle(screen.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.ALL).setSize(35)), GameColor.textGreen))

    override fun addActorsOnGroup() {
        addFrameImg()
        addTextLbl()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addFrameImg() {
        addActor(frameImg)
        frameImg.setBoundsStandart(0f,0f,501f,101f)
    }

    private fun addTextLbl() {
        addActor(textLbl)
        textLbl.setBoundsStandart(10f,10f,481f,81f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun updateNickname(nickname: String) {
        textLbl.setText(nickname)
    }

    fun getNickname() = if (textLbl.text != screen.game.languageUtil.getStringResource(R.string.nickname)) textLbl.text else ""


}