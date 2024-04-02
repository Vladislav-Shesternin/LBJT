package com.education.lbjt.game.actors.image

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class AImageFrame(
    override val screen: AdvancedScreen,
    val region: TextureRegion,
): AdvancedGroup() {

    override fun addActorsOnGroup() {
        addAndFillActor(Image(region))
        addAndFillActor(Image(screen.game.themeUtil.assets.BORDERS_BLUE))
    }

}