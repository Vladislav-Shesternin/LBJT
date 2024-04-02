package com.education.lbjt.game.actors.scroll.tutorial.actors

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.education.lbjt.R
import com.education.lbjt.game.actors.button.AButton_Regular
import com.education.lbjt.game.screens.AboutAuthorScreen
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class ABtnPanel(
    override val screen : AdvancedScreen,
    font    : BitmapFont,
    val practicalScreenName: String,
): AdvancedGroup() {

    private val btnThanks = AButton_Regular(screen, screen.game.languageUtil.getStringResource(R.string.give_thanks), Label.LabelStyle(font, GameColor.textGreen))
    private val btnPlay   = AButton_Regular(screen, screen.game.languageUtil.getStringResource(R.string.play), Label.LabelStyle(font, GameColor.textGreen))

    override fun addActorsOnGroup() {
        addActors(btnThanks, btnPlay)
        btnThanks.setBounds(25f, 0f, 250f, 90f)
        btnPlay.setBounds(375f, 0f, 250f, 90f)

        btnThanks.setOnClickListener {
            screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                screen.game.navigationManager.navigate(AboutAuthorScreen::class.java.name, screen::class.java.name)
            }
        }
        btnPlay.setOnClickListener {
            screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                screen.game.navigationManager.navigate(practicalScreenName)
            }
        }
    }

}