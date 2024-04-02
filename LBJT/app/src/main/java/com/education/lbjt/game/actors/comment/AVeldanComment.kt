package com.education.lbjt.game.actors.comment

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.education.lbjt.R
import com.education.lbjt.game.actors.AIcon
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.setBounds
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.font.FontParameter.CharType

class AVeldanComment(override val screen: AdvancedScreen): AdvancedGroup() {

    override var standartW = 700f

    private val parameter = FontParameter()

    private val red      = Image(screen.drawerUtil.getRegion(Color.RED.apply { a = 0.4f }))
    private val icon     = AIcon(screen)
    private val nickname = Label("Veldan", Label.LabelStyle(screen.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.ALL).setSize(35)), GameColor.textGreen))
    private val panel    = Image(screen.game.themeUtil.assets.PANEL)
    private val comment  = Label(screen.game.languageUtil.getStringResource(R.string.veldan_comment), Label.LabelStyle(screen.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.ALL).setSize(20)), GameColor.textGreen))

    private val TIME_ANIM_RED = 0.3f

    override fun addActorsOnGroup() {
        red.animHide()
        addAndFillActor(red)
        addIcon()
        addNickname()
        addPanel()
        addComment()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addIcon() {
        addActor(icon)
        icon.setBounds(Vector2(15f, height-154f).toStandart, Vector2(154f, 154f).toStandart)
        icon.updateIcon(screen.game.themeUtil.assets.ICON_VELDAN)
    }

    private fun addNickname() {
        addActor(nickname)
        nickname.setBounds(Vector2(194f, height-52f).toStandart, Vector2(491f, 52f).toStandart)
    }

    private fun addPanel() {
        addActor(panel)
        panel.setBounds(Vector2(184f, 0f).toStandart, Vector2(501f, height-53f).toStandart)
    }

    private fun addComment() {
        addActor(comment)
        comment.apply {
            setBounds(Vector2(199f, 10f).toStandart, Vector2(471f, this@AVeldanComment.height-73f).toStandart)
            wrap = true
            setAlignment(Align.topLeft)
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun animRed() {
        red.addAction(Actions.sequence(
            Actions.delay(0.5f),
            Actions.fadeIn(TIME_ANIM_RED),
            Actions.fadeOut(TIME_ANIM_RED),
            Actions.fadeIn(TIME_ANIM_RED),
            Actions.fadeOut(TIME_ANIM_RED),
            Actions.fadeIn(TIME_ANIM_RED),
            Actions.fadeOut(TIME_ANIM_RED),
        ))
    }
}