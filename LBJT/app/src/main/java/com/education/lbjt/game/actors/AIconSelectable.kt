package com.education.lbjt.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.education.lbjt.game.actors.masks.Mask
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class AIconSelectable(override val screen: AdvancedScreen): AdvancedGroup() {

    override var standartW = 154f

    private val themeUtil   = screen.game.themeUtil

    private val frameImg      = Image(themeUtil.assets.FRAME_ICON)
    private val frameEmptyImg = Image(themeUtil.assets.FRAME_ICON_EMPTY)
    private val maskGroup     = Mask(screen, themeUtil.assets.MASK_ICON)
    private val iconImg       = Image()
    private val defaultImg    = Image(themeUtil.assets.ICON_DEF)
    private val indicator     = AIndicator(screen)

    private var isRemovedDefaultImg = false
    private var timeAnimAlpha       = 0.25f

    override fun addActorsOnGroup() {
        addFrameImg()
        addMaskGroup()
        addDefaultImg()
        addIndicator()
        addFrameEmptyImg()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addFrameImg() {
        addActor(frameImg)
        frameImg.setBoundsStandart(0f, 0f,154f, 154f)
    }

    private fun addMaskGroup() {
        addActor(maskGroup)
        maskGroup.apply {
            setBoundsStandart(10f, 10f,134f,134f)
            maskGroup.addAndFillActor(iconImg)
        }
    }

    private fun addDefaultImg() {
        addActor(defaultImg)
        defaultImg.setBoundsStandart(39f,22f,77f,92f)
    }

    private fun addIndicator() {
        addActor(indicator)
        indicator.setBoundsStandart(47f,47f,60f,60f)
    }

    private fun addFrameEmptyImg() {
        addActor(frameEmptyImg)
        frameEmptyImg.setBoundsStandart(0f, 0f,154f, 154f)
    }

    // ---------------------------------------------------
    // Anim
    // ---------------------------------------------------

    fun animShowLoader() {
        animHideIcons()
        indicator.animShowLoader()
    }

    fun animShowNoWifi() {
        animHideIcons()
        indicator.animShowNoWifi { animShowIcons() }
    }

    fun animHideLoader() {
        indicator.animHideLoader { animShowIcons() }
    }

    fun animHideNoWifi() {
        indicator.animHideNoWifi { animShowIcons() }
    }

    private fun animHideIcons() {
        if (isRemovedDefaultImg.not()) defaultImg.animHide(timeAnimAlpha)
        iconImg.animHide(timeAnimAlpha)
    }

    private fun animShowIcons() {
        if (isRemovedDefaultImg.not()) defaultImg.animShow(timeAnimAlpha)
        iconImg.animShow(timeAnimAlpha)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun updateIcon(textureRegion: TextureRegion) {
        if (isRemovedDefaultImg.not()) {
            isRemovedDefaultImg = true
            defaultImg.remove()
        }
        iconImg.drawable = TextureRegionDrawable(textureRegion)
    }



}