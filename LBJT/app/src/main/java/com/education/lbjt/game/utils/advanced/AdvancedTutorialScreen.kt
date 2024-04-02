package com.education.lbjt.game.utils.advanced

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.education.lbjt.R
import com.education.lbjt.game.LibGDXGame
import com.education.lbjt.game.actors.button.AButton_Regular
import com.education.lbjt.game.actors.scroll.ATutorialScrollPane
import com.education.lbjt.game.actors.scroll.tutorial.AAbstractTutorialScrollPanel
import com.education.lbjt.game.manager.SpriteManager
import com.education.lbjt.game.manager.util.SpriteUtil
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.region

abstract class AdvancedTutorialScreen(override val game: LibGDXGame): AdvancedScreen() {

    abstract val title              : String
    abstract val practicalScreenName: String

    private val textPlay by lazy { game.languageUtil.getStringResource(R.string.play) }

    private val parameter              by lazy { FontParameter().setCharacters(title + textPlay) }
    private val fontInter_ExtraBold_50 by lazy { fontGeneratorInter_ExtraBold.generateFont(parameter.setSize(50)) }

    // Assets
    open var spriteUtil : SpriteUtil.TutorialsAssets? = null

    open val loadableAtlasList   = mutableListOf<SpriteManager.AtlasData>()
    open val loadableTextureList = mutableListOf<SpriteManager.TextureData>()

    protected val assetManager    = AssetManager()
    protected var isFinishLoading = false

    // Actor
    open var aScrollPane: AAbstractTutorialScrollPanel? = null

    private val aPlayBtn  by lazy { AButton_Regular(this, textPlay, Label.LabelStyle(fontInter_ExtraBold_50, GameColor.textGreen)) }
    private val aTitleLbl by lazy { Label(title, Label.LabelStyle(fontInter_ExtraBold_50, GameColor.textRed)) }


    override fun show() {
        game.apply { backgroundColor = GameColor.background }
        game.activity.apply { setNavigationBarColor(R.color.background) }

        game.activity.lottie.showLoader()
        stageUI.root.animHide()
        setUIBackground(game.themeUtil.assets.BACKGROUND.region)
        loadAssets()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addTitleLbl()
        addPlayBtn()
        addScrollPanel()

        game.apply { backgroundColor = themeUtil.backgroundColor }
        game.activity.apply { setNavigationBarColor(game.themeUtil.navBarColorId) }

        game.activity.lottie.hideLoader()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun dispose() {
        super.dispose()
        assetManager.dispose()
        game.spriteManager.assetManager = game.assetManager
    }

    abstract fun loadingAssets()

    fun superShow() { super.show() }

    private fun loadAssets() {
        game.spriteManager.assetManager = assetManager

        with(game.spriteManager) {
            loadableAtlasList   = this@AdvancedTutorialScreen.loadableAtlasList
            loadAtlas()
            loadableTextureList = this@AdvancedTutorialScreen.loadableTextureList
            loadTexture()
        }
    }

    protected fun initAssets() {
        game.spriteManager.initAtlasAndTexture()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addTitleLbl() {
        addActor(aTitleLbl)
        aTitleLbl.setBounds(0f, 1315f, 700f, 61f)
        aTitleLbl.setAlignment(Align.center)
    }

    private fun AdvancedStage.addPlayBtn() {
        addActor(aPlayBtn)
        aPlayBtn.setBounds(225f, 1198f, 250f, 90f)
        aPlayBtn.setOnClickListener {
            stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                game.navigationManager.navigate(practicalScreenName)
            }
        }
    }

    private var isShow = true

    private fun AdvancedStage.addScrollPanel() {
        addActor(aScrollPane)
        aScrollPane?.setBounds(25f, -235f, 650f, 1400f)

        aScrollPane?.scrollPanel?.scrollNextBlock = ATutorialScrollPane.Static.ScrollBlock { isScrollNext ->
            if (isScrollNext && isShow) {
                    isShow = false
                    animHideTop()
            }
            if (isScrollNext.not() && isShow.not()) {
                isShow = true
                animShowTop()
            }
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun animHideTop() {
        aTitleLbl.apply {
            clearActions()
            animHide(0.3f)
        }
        aPlayBtn.apply {
            clearActions()
            animHide(0.3f)
        }
        aScrollPane?.apply {
            clearActions()
            addAction(Actions.moveTo(25f, 0f, 0.6f, Interpolation.sine))
        }
    }

    private fun animShowTop() {
        aTitleLbl.apply {
            clearActions()
            animShow(0.9f)
        }
        aPlayBtn.apply {
            clearActions()
            animShow(0.9f)
        }
        aScrollPane?.apply {
            clearActions()
            addAction(Actions.moveTo(25f, -235f, 0.6f, Interpolation.sine))
        }
    }

}