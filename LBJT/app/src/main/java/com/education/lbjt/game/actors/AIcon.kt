package com.education.lbjt.game.actors

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.education.lbjt.game.actors.masks.Mask
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class AIcon(override val screen: AdvancedScreen): AdvancedGroup() {

    override var standartW = 154f

    private val themeUtil   = screen.game.themeUtil

    private val frameImg      = Image(themeUtil.assets.FRAME_ICON)
    private val frameEmptyImg = Image(themeUtil.assets.FRAME_ICON_EMPTY)
    private val maskGroup     = Mask(screen, themeUtil.assets.MASK_ICON)
    private val iconImg       = Image()

    override fun addActorsOnGroup() {
        addFrameImg()
        addMaskGroup()
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

    private fun addFrameEmptyImg() {
        addActor(frameEmptyImg)
        frameEmptyImg.setBoundsStandart(0f, 0f,154f, 154f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun updateIcon(texture: Texture) {
        iconImg.drawable = TextureRegionDrawable(texture)
    }

}