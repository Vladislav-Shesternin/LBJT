package com.education.lbjt.game.actors.scroll.tutorial.actors

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class ACodePanel(
    override val screen : AdvancedScreen,
    textCode: String,
    font    : BitmapFont,
): AdvancedGroup() {

    private val panelImg   = Image(screen.game.themeUtil.assets.PANEL_CODE)
    private val codeLbl    = Label(textCode, Label.LabelStyle(font, GameColor.textGreen))
    private val scrollPane = ScrollPane(codeLbl)

    override fun addActorsOnGroup() {
        addAndFillActor(panelImg)
        addActor(scrollPane)
        scrollPane.setBounds(10f, 15f, width-20f, height-30f)
    }

}