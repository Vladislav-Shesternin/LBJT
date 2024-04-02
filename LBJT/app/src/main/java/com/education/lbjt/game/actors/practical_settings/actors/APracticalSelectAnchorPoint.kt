package com.education.lbjt.game.actors.practical_settings.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.education.lbjt.game.utils.SizeStandardizer
import com.education.lbjt.game.utils.actor.disable
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.toB2
import com.education.lbjt.game.utils.toUI

class APracticalSelectAnchorPoint(
    override val screen: AdvancedScreen,
    val bodyRegion    : TextureRegion,
    val bodySize      : Vector2,
    val standartBodyW : Float,
    val anchorPointPos: Vector2
): AdvancedGroup() {

    private val assets       = screen.game.themeUtil.assets
    private val standardizer = SizeStandardizer().apply { standardize(standartBodyW, bodySize.x) }

    // Actor
    private val anchorPointImg = Image(assets.ANCHOR_POINT)
    private val bodyImg        = Image(bodyRegion)

    // Field
    private var startX = 0f
    private var startY = 0f
    private val anchorPointPosUI = standardizer.scope { anchorPointPos.cpy().toUI.toStandart }

    override fun addActorsOnGroup() {
        startX = (width / 2) - (bodySize.x / 2)
        startY = (height / 2) - (bodySize.y / 2)

        addAndFillActor(Image(assets.PRACTICAL_FRAME_WHITE).apply { disable() })
        addBodyImg()
        addAnchorPointImg()

        addListener(getInputListener())
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addBodyImg() {
        addActor(bodyImg)
        bodyImg.apply {
            disable()
            setBounds(startX, startY, bodySize.x, bodySize.y)
        }
    }

    private fun addAnchorPointImg() {
        addActor(anchorPointImg)
        anchorPointImg.apply {
            disable()
            setBounds(startX + anchorPointPosUI.x - 16, startY + anchorPointPosUI.y - 16f,32f, 32f)
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun getInputListener() = object : InputListener() {
        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchDragged(event, x, y, pointer)
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            if (x.toInt() in (0..width.toInt())) {
                anchorPointImg.x = x - 16
                anchorPointPos.x = standardizer.scope { (x - startX).toNew }.toB2
            }
            if (y.toInt() in (0..height.toInt())) {
                anchorPointImg.y = y - 16
                anchorPointPos.y = standardizer.scope { (y - startY).toNew }.toB2
            }
            event?.stop()
        }
    }

}