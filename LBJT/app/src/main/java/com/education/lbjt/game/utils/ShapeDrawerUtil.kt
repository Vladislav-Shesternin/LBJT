package com.education.lbjt.game.utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import space.earlygrey.shapedrawer.ShapeDrawer

class ShapeDrawerUtil(batch: Batch): Disposable {

    private val disposableSet = mutableSetOf<Disposable>()

    val drawer = ShapeDrawer(batch, getRegion())

    override fun dispose() {
        disposableSet.disposeAll()
    }

    fun update() {
        drawer.update()
    }

    fun getRegion(color: Color = Color.WHITE): TextureRegion {
        val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
        pixmap.setColor(color)
        pixmap.drawPixel(0, 0)

        val texture = Texture(pixmap)
        disposableSet.add(texture)

        pixmap.dispose()
        return TextureRegion(texture, 0, 0, 1, 1)
    }

}