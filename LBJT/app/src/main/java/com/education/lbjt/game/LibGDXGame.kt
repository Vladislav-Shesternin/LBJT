package com.education.lbjt.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.education.lbjt.MainActivity
import com.education.lbjt.game.manager.MusicManager
import com.education.lbjt.game.manager.NavigationManager
import com.education.lbjt.game.manager.SoundManager
import com.education.lbjt.game.manager.SpriteManager
import com.education.lbjt.game.manager.util.MusicUtil
import com.education.lbjt.game.manager.util.SoundUtil
import com.education.lbjt.game.screens.LoaderScreen
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.advanced.AdvancedGame
import com.education.lbjt.game.utils.disposeAll
import com.education.lbjt.game.utils.language.LanguageUtil
import com.education.lbjt.game.utils.theme.ThemeUtil
import com.education.lbjt.util.log

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var assetManagerLocal: AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val musicUtil    by lazy { MusicUtil()    }
    val soundUtil    by lazy { SoundUtil()    }
    val themeUtil    by lazy { ThemeUtil()    }
    val languageUtil by lazy { LanguageUtil(activity) }

    var backgroundColor = GameColor.background
    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        assetManagerLocal = AssetManager(LocalFileHandleResolver())
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)
    }

    override fun render() {
        ScreenUtils.clear(backgroundColor)
        super.render()
    }

    override fun dispose() {
        try {
            log("dispose LibGDXGame")
            disposableSet.disposeAll()
            disposeAll(musicUtil, assetManager, assetManagerLocal)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

}