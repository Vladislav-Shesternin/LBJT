package com.education.lbjt.game.screens.tutorialsScreen

import com.education.lbjt.game.LibGDXGame
import com.education.lbjt.game.actors.scroll.tutorial.AScrollPanel_JFriction
import com.education.lbjt.game.actors.scroll.tutorial.AScrollPanel_JGear
import com.education.lbjt.game.actors.scroll.tutorial.AScrollPanel_JPulley
import com.education.lbjt.game.actors.scroll.tutorial.AScrollPanel_JRope
import com.education.lbjt.game.manager.SpriteManager
import com.education.lbjt.game.manager.util.SpriteUtil
import com.education.lbjt.game.screens.practicalScreen.Practical_JFrictionScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JPulleyScreen
import com.education.lbjt.game.utils.advanced.AdvancedTutorialScreen

class JPulleyScreen(override val game: LibGDXGame): AdvancedTutorialScreen(game) {

    override val title               = "Pulley Joint"
    override val practicalScreenName = Practical_JPulleyScreen::class.java.name

    override var loadableAtlasList   = SpriteManager.EnumAtlas_JointPulley.values().map { it.data }.toMutableList()
    override val loadableTextureList = SpriteManager.EnumTexture_JointPulley.values().map { it.data }.toMutableList()

    override fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (assetManager.update(16)) {
                isFinishLoading = true
                initAssets()

                spriteUtil  = SpriteUtil.JointPulley()
                aScrollPane = AScrollPanel_JPulley(this, spriteUtil as SpriteUtil.JointPulley)

                superShow()
            }
        }
    }

}