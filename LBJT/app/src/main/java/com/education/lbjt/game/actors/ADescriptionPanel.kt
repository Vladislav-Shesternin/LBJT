package com.education.lbjt.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.github.tommyettinger.textra.TypingLabel
import com.education.lbjt.R
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.font.FontParameter.CharType

class ADescriptionPanel(override val screen: AdvancedScreen): AdvancedGroup() {

    override var standartW = 595f

    private val themeUtil   = screen.game.themeUtil

    private val parameter = FontParameter()

    private val panelImg       = Image(themeUtil.assets.DESCRIPTION_PANEL)
    private val descriptionLbl = TypingLabel(screen.game.languageUtil.getStringResource(R.string.about_author_description), Label.LabelStyle(screen.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.ALL).setSize(23)), GameColor.textGreen))


    override fun addActorsOnGroup() {
        addPanelImg()
        addDescriptionScrollPane()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addPanelImg() {
        addActor(panelImg)
        panelImg.setBoundsStandart(0f, 0f, 595f, 484f)
    }

    private fun addDescriptionScrollPane() {
        addActor(descriptionLbl)
        descriptionLbl.setBoundsStandart(18f, 10f,559f, 445f)
        descriptionLbl.apply {
            alignment = Align.center
            wrap      = true
        }
    }

}