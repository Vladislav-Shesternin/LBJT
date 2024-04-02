package com.education.lbjt.game.utils.advanced

import com.badlogic.gdx.utils.viewport.FitViewport
import com.education.lbjt.game.box2d.WorldUtil
import com.education.lbjt.game.utils.HEIGHT_BOX2D
import com.education.lbjt.game.utils.HEIGHT_UI
import com.education.lbjt.game.utils.WIDTH_BOX2D
import com.education.lbjt.game.utils.WIDTH_UI
import com.education.lbjt.util.log

abstract class AdvancedBox2dScreen(
    val worldUtil: WorldUtil,
    val uiW  : Float = WIDTH_UI,
    val uiH  : Float = HEIGHT_UI,
    val boxW : Float = WIDTH_BOX2D,
    val boxH : Float = HEIGHT_BOX2D,
): AdvancedScreen(uiW, uiH) {

    private val viewportBox2d by lazy { FitViewport(boxW, boxH) }


    override fun resize(width: Int, height: Int) {
        viewportBox2d.update(width, height, true)
        super.resize(width, height)
    }

    override fun render(delta: Float) {
        worldUtil.update(delta)
        super.render(delta)
        worldUtil.debug(viewportBox2d.camera.combined)
    }

    override fun dispose() {
        log("dispose AdvancedBox2dScreen: ${this::class.java.name.substringAfterLast('.')}")
        worldUtil.dispose()
        super.dispose()
    }

}