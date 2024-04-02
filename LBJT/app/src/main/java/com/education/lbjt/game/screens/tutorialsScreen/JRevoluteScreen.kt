package com.education.lbjt.game.screens.tutorialsScreen

import com.education.lbjt.game.LibGDXGame
import com.education.lbjt.game.actors.scroll.tutorial.AScrollPanel_JRevolute
import com.education.lbjt.game.manager.SpriteManager
import com.education.lbjt.game.manager.util.SpriteUtil
import com.education.lbjt.game.screens.practicalScreen.Practical_JRevoluteScreen
import com.education.lbjt.game.utils.advanced.AdvancedTutorialScreen

class JRevoluteScreen(override val game: LibGDXGame): AdvancedTutorialScreen(game) {

    override val title               = "Revolute Joint"
    override val practicalScreenName = Practical_JRevoluteScreen::class.java.name

    override var loadableAtlasList   = SpriteManager.EnumAtlas_JointRevolute.values().map { it.data }.toMutableList()
    override val loadableTextureList = SpriteManager.EnumTexture_JointRevolute.values().map { it.data }.toMutableList()

    override fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (assetManager.update(16)) {
                isFinishLoading = true
                initAssets()

                spriteUtil  = SpriteUtil.JointRevolute()
                aScrollPane = AScrollPanel_JRevolute(this, spriteUtil as SpriteUtil.JointRevolute)

                superShow()
            }
        }
    }

}