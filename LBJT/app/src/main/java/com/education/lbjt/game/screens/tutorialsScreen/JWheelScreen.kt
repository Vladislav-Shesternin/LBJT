package com.education.lbjt.game.screens.tutorialsScreen

import com.education.lbjt.game.LibGDXGame
import com.education.lbjt.game.actors.scroll.tutorial.AScrollPanel_JWheel
import com.education.lbjt.game.manager.SpriteManager
import com.education.lbjt.game.manager.util.SpriteUtil
import com.education.lbjt.game.screens.practicalScreen.Practical_JWheelScreen
import com.education.lbjt.game.utils.advanced.AdvancedTutorialScreen

class JWheelScreen(override val game: LibGDXGame): AdvancedTutorialScreen(game) {

    override val title               = "Wheel Joint"
    override val practicalScreenName = Practical_JWheelScreen::class.java.name

    override var loadableAtlasList   = SpriteManager.EnumAtlas_JointWheel.values().map { it.data }.toMutableList()
    override val loadableTextureList = SpriteManager.EnumTexture_JointWheel.values().map { it.data }.toMutableList()

    override fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (assetManager.update(16)) {
                isFinishLoading = true
                initAssets()

                spriteUtil  = SpriteUtil.JointWheel()
                aScrollPane = AScrollPanel_JWheel(this, spriteUtil as SpriteUtil.JointWheel)

                superShow()
            }
        }
    }

}