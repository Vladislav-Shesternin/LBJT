package com.education.lbjt.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.education.lbjt.game.LibGDXGame
import com.education.lbjt.game.manager.MusicManager
import com.education.lbjt.game.manager.SoundManager
import com.education.lbjt.game.manager.SpriteManager
import com.education.lbjt.game.screens.practicalScreen.Practical_JGearScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JPulleyScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JRopeScreen
import com.education.lbjt.game.screens.tutorialsScreen.JGearScreen
import com.education.lbjt.game.screens.tutorialsScreen.JPulleyScreen
import com.education.lbjt.game.screens.tutorialsScreen.JRopeScreen
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.setBounds
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.advanced.AdvancedStage
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.font.FontParameter.CharType
import com.education.lbjt.game.utils.runGDX
import com.education.lbjt.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.education.lbjt.game.utils.Layout.Splash as LS

class LoaderScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false
    private var isFinishAnim     = false

    private val parameter = FontParameter()

    private val progressLabel by lazy { Label("", Label.LabelStyle(fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.NUMBERS.chars+"%").setSize(100)), GameColor.textGreen)) }

    override fun show() {
        loadSplashAssets()
        super.show()
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addProgress()
        isFinishAnim = true
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addProgress() {
        addActor(progressLabel)
        progressLabel.apply {
            setBounds(LS.progress)
            setAlignment(Align.center)
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun loadSplashAssets() {
//        with(game.fontTTFManager) {
//            loadableFontList = mutableListOf(game.fontTTFUtil.font_Inter_ExtraBold_100)
//            load()
//        }
//        game.assetManager.finishLoading()
//        game.fontTTFManager.init()
    }

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.values().map { it.data }.toMutableList()
            loadAtlas()
            loadableTextureList = SpriteManager.EnumTexture.values().map { it.data }.toMutableList()
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.values().map { it.data }.toMutableList()
            load()
        }
        with(game.soundManager) {
            loadableSoundList = SoundManager.EnumSound.values().map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        game.spriteManager.initAtlasAndTexture()
        game.musicManager.init()
        game.soundManager.init()
    }

    private fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (game.assetManager.update(16)) {
                isFinishLoading = true
                initAssets()
            }
            progressFlow.value = game.assetManager.progress
        }
    }

    private fun collectProgress() {
        coroutine?.launch {
            var progress = 0
            progressFlow.collect { p ->
                while (progress < (p * 100)) {
                    progress += 1
                    runGDX { progressLabel.setText("$progress%") }
                    if (progress % 25 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    //delay(16)
                    //delay((2..5).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && isFinishAnim) {
            isFinishAnim = false

            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                game.activity.lottie.hideLoader()
                game.navigationManager.navigate(/*Practical_JMotorScreen::class.java.name)*/ MenuScreen::class.java.name)
            }
        }
    }


}