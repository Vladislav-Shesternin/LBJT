package com.education.lbjt.game.screens.tutorialsScreen

import com.education.lbjt.game.LibGDXGame
import com.education.lbjt.game.actors.scroll.tutorial.AScrollPanel_JWeld
import com.education.lbjt.game.manager.SpriteManager
import com.education.lbjt.game.manager.util.SpriteUtil
import com.education.lbjt.game.screens.practicalScreen.Practical_JWeldScreen
import com.education.lbjt.game.utils.advanced.AdvancedTutorialScreen

class JWeldScreen(override val game: LibGDXGame): AdvancedTutorialScreen(game) {

    override val title               = "Weld Joint"
    override val practicalScreenName = Practical_JWeldScreen::class.java.name

    override var loadableAtlasList   = SpriteManager.EnumAtlas_JointWeld.values().map { it.data }.toMutableList()
    override val loadableTextureList = SpriteManager.EnumTexture_JointWeld.values().map { it.data }.toMutableList()

    override fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (assetManager.update(16)) {
                isFinishLoading = true
                initAssets()

                spriteUtil  = SpriteUtil.JointWeld()
                aScrollPane = AScrollPanel_JWeld(this, spriteUtil as SpriteUtil.JointWeld)

                superShow()
            }
        }
    }

}