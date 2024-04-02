package com.education.lbjt.game.actors.update

import android.content.Intent
import android.net.Uri
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.github.tommyettinger.textra.TypingLabel
import com.education.lbjt.R
import com.education.lbjt.game.actors.button.AButton
import com.education.lbjt.game.actors.button.AButtonTextTyping
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.TIME_ANIM_DIALOG_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.font.FontParameter

class AUpdateAvailablePanel(override val screen: AdvancedScreen): AdvancedGroup() {

    private val themeUtil = screen.game.themeUtil

    private val textTitle  = screen.game.languageUtil.getStringResource(R.string.update_available)
    private val textButton = screen.game.languageUtil.getStringResource(R.string.update_now)

    private val parameter  = FontParameter()
    private val fontTitle  = screen.fontGeneratorInter_Black.generateFont(parameter.setCharacters(textTitle).setSize(45))
    private val fontButton = screen.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(textButton).setSize(35))

    // Actor
    private val panelImg   = Image(themeUtil.assets.UPDATE_PANEL)
    private val titleLbl   = TypingLabel(textTitle, Label.LabelStyle(fontTitle, GameColor.textRed))
    private val updateBtn  = AButtonTextTyping(screen, textButton, AButton.Static.Type.UPDATE, Label.LabelStyle(fontButton, GameColor.textGreen))
    private val updateXBtn = AButton(screen, AButton.Static.Type.UPDATE_X)
    private val lightImg   = Image(themeUtil.assets.UPDATE_LIGHT)

    // Field
    var isShow = false
        private set

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.drawerUtil.getRegion(Color.BLACK.apply { a = 0.6f })))
        addPanelImg()
        addTitleLbl()
        addLight()
        addUpdateBtn()
        addUpdateXBtn()

        isShow = true

        addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                event?.stop()
                return true
            }
        })
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addPanelImg() {
        addActor(panelImg)
        panelImg.setBounds(0f,1018f,700f,380f)
    }

    private fun addTitleLbl() {
        addActor(titleLbl)
        titleLbl.setBounds(35f,1208f,630f,73f)
        titleLbl.alignment = Align.center
        titleLbl.wrap      = true
    }

    private fun addUpdateBtn() {
        addActor(updateBtn)
        updateBtn.setBounds(181f,1061f,337f,91f)

        updateBtn.setOnClickListener { screen.game.activity.apply {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
        } }
    }

    private fun addUpdateXBtn() {
        addActor(updateXBtn)
        updateXBtn.setBounds(630f,1320f,38f,38f)

        updateXBtn.setOnClickListener { close() }
    }

    private fun addLight() {
        addActor(lightImg)
        lightImg.apply {
            setBounds(139f,1023f,422f,167f)
            setOrigin(Align.center)

            val scale = 0.1f
            addAction(Actions.forever(Actions.sequence(
                Actions.scaleBy(scale, scale, 0.5f),
                Actions.scaleBy(-scale, -scale, 0.5f),
            )))
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun close() {
        isShow = false
        animHide(TIME_ANIM_DIALOG_ALPHA) { dispose() }
    }

}