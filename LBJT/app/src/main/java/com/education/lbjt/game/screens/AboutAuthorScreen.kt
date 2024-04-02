package com.education.lbjt.game.screens

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.education.lbjt.game.LibGDXGame
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.bodies.BSeparator
import com.education.lbjt.game.box2d.bodiesGroup.BGBorders
import com.education.lbjt.game.box2d.bodiesGroup.BGDescriptionPanel
import com.education.lbjt.game.box2d.bodiesGroup.BGThanksFrame
import com.education.lbjt.game.box2d.destroyAll
import com.education.lbjt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.actor.setOnClickListener
import com.education.lbjt.game.utils.advanced.AdvancedMouseScreen
import com.education.lbjt.game.utils.advanced.AdvancedStage
import com.education.lbjt.game.utils.region
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch


class AboutAuthorScreen(override val game: LibGDXGame): AdvancedMouseScreen(game) {

    // Actor
    private val aHandVImg    = Image(game.themeUtil.assets.HAND_V)
    private val aEldanImg    = Image(game.themeUtil.assets.EL_DAN)
    private val aPSImg       = Image(game.themeUtil.assets.PS)
    private val aTelegramImg = Image(game.themeUtil.assets.TELEGRAM)

    // Body
    private val bSeparator = BSeparator(this)

    // BodyGroup
    private val bgBorders          = BGBorders(this)
    private val bgDescriptionPanel = BGDescriptionPanel(this)
    private val bgThanksFrame      = BGThanksFrame(this)
//    private val bgMonetization     = BGMonetization(this)

    override fun show() {
        stageUI.root.animHide()
        setUIBackground(game.themeUtil.assets.BACKGROUND.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        initB_Separator()

        addV()
        addTelegramImg()

        createBG_Borders()
        createB_Separator()
        createBG_DescriptionPanel()
        createBG_ThanksFrame()
        createBG_Monetization()

        coroutine?.launch { animV() }
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }


    override fun dispose() {
        listOf(bgBorders, bgDescriptionPanel, bgThanksFrame /*bgMonetization*/).destroyAll()
        super.dispose()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------
    private fun AdvancedStage.addV() {
        addActors(aHandVImg, aEldanImg, aPSImg)
        aHandVImg.apply {
            setBounds(-112f, 1188f, 112f, 203f)
            animHide()
        }
        aEldanImg.apply {
            setBounds(192f, 1400f, 254f, 91f)
            animHide()
        }
        aPSImg.apply {
            setBounds(700f, 1189f, 411f, 64f)
            animHide()
        }
    }

    private fun AdvancedStage.addTelegramImg() {
        addActor(aTelegramImg)
        aTelegramImg.apply {
            setBounds(222f, 321f, 257f, 74f)
            setOnClickListener {
                game.activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Vel_daN")))
            }
        }
    }

    // ---------------------------------------------------
    // Init Body
    // ---------------------------------------------------

    private fun initB_Separator() {
        bSeparator.apply {
            id = BodyId.SEPARATOR
            collisionList.addAll(arrayOf(BodyId.AboutAuthor.ITEM))
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Separator() {
        bSeparator.create(0f, 1179f, 700f, 10f)
    }

    // ------------------------------------------------------------------------
    // Create Body Group
    // ------------------------------------------------------------------------
    private fun createBG_Borders() {
        bgBorders.create(0f,0f,700f,1400f)
    }

    private fun createBG_DescriptionPanel() {
        bgDescriptionPanel.create(53f,650f,595f,529f)
    }

    private fun createBG_ThanksFrame() {
        bgThanksFrame.create(0f,534f,700f,72f)
    }

    private fun createBG_Monetization() {
        //bgMonetization.create(39f,0f,622f,507f)
    }

    // ---------------------------------------------------
    // Anim
    // ---------------------------------------------------

    private suspend fun animV() = CompletableDeferred<Unit>().also { continuation ->
        val moveTime  = 0.7f
        val alphaTime = 0.5f

        aHandVImg.addAction(Actions.parallel(
            Actions.moveTo(107f, 1188f, moveTime, Interpolation.sineOut),
            Actions.fadeIn(alphaTime),
        ))
        aEldanImg.addAction(Actions.parallel(
            Actions.moveTo(192f, 1290f, moveTime, Interpolation.sineOut),
            Actions.fadeIn(alphaTime),
        ))
        aPSImg.addAction(Actions.sequence(
            Actions.parallel(
                Actions.moveTo(240f, 1189f, moveTime, Interpolation.sineOut),
                Actions.fadeIn(alphaTime),
            ),
            Actions.run { continuation.complete(Unit) }
        ))
    }.await()

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

}