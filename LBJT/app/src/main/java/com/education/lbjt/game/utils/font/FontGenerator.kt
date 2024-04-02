package com.education.lbjt.game.utils.font

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.utils.Disposable
import com.education.lbjt.game.utils.disposeAll

class FontGenerator(fontPath: FontPath): FreeTypeFontGenerator(Gdx.files.internal(fontPath.path)) {

    private val disposableSet = mutableSetOf<Disposable>()

    override fun generateFont(parameter: FreeTypeFontParameter?): BitmapFont {
        val font = super.generateFont(parameter)
        disposableSet.add(font)
        return font
    }

    override fun dispose() {
        super.dispose()
        disposableSet.disposeAll()
        disposableSet.clear()
    }

    companion object {
        enum class FontPath(val path: String) {
            Inter_Black("font/Inter-Black.ttf"),
            Inter_ExtraBold("font/Inter-ExtraBold.ttf"),
            Inter_Bold("font/Inter-Bold.ttf"),
            Inter_Regular("font/Inter-Regular.ttf"),
            Inter_Medium("font/Inter-Medium.ttf"),
            Inter_Light("font/Inter-Light.ttf"),
        }
    }

}