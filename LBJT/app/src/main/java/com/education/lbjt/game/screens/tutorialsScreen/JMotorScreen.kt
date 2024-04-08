package com.education.lbjt.game.screens.tutorialsScreen

import com.education.lbjt.game.LibGDXGame
import com.education.lbjt.game.actors.scroll.tutorial.AScrollPanel_JMotor
import com.education.lbjt.game.manager.SpriteManager
import com.education.lbjt.game.manager.util.SpriteUtil
import com.education.lbjt.game.screens.practicalScreen.Practical_JGearScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JMotorScreen
import com.education.lbjt.game.utils.advanced.AdvancedTutorialScreen

class JMotorScreen(override val game: LibGDXGame): AdvancedTutorialScreen(game) {

    override val title               = "Motor Joint"
    override val practicalScreenName = Practical_JMotorScreen::class.java.name

    override var loadableAtlasList   = SpriteManager.EnumAtlas_JointMotor.values().map { it.data }.toMutableList()
    override val loadableTextureList = SpriteManager.EnumTexture_JointMotor.values().map { it.data }.toMutableList()

    override fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (assetManager.update(16)) {
                isFinishLoading = true
                initAssets()

                spriteUtil  = SpriteUtil.JointMotor()
                aScrollPane = AScrollPanel_JMotor(this, spriteUtil as SpriteUtil.JointMotor)

                superShow()
            }
        }
    }

}