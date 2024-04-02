package com.education.lbjt.game.screens.tutorialsScreen

import com.education.lbjt.game.LibGDXGame
import com.education.lbjt.game.actors.scroll.tutorial.AScrollPanel_JFriction
import com.education.lbjt.game.actors.scroll.tutorial.AScrollPanel_JRope
import com.education.lbjt.game.manager.SpriteManager
import com.education.lbjt.game.manager.util.SpriteUtil
import com.education.lbjt.game.screens.practicalScreen.Practical_JFrictionScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JRopeScreen
import com.education.lbjt.game.utils.advanced.AdvancedTutorialScreen

class JRopeScreen(override val game: LibGDXGame): AdvancedTutorialScreen(game) {

    override val title               = "Rope Joint"
    override val practicalScreenName = Practical_JRopeScreen::class.java.name

    override var loadableAtlasList   = SpriteManager.EnumAtlas_JointRope.values().map { it.data }.toMutableList()
    override val loadableTextureList = SpriteManager.EnumTexture_JointRope.values().map { it.data }.toMutableList()

    override fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (assetManager.update(16)) {
                isFinishLoading = true
                initAssets()

                spriteUtil  = SpriteUtil.JointRope()
                aScrollPane = AScrollPanel_JRope(this, spriteUtil as SpriteUtil.JointRope)

                superShow()
            }
        }
    }

}