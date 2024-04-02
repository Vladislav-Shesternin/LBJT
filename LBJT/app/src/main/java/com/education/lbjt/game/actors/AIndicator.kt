package com.education.lbjt.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class AIndicator(override val screen: AdvancedScreen): AdvancedGroup() {

    private val aIndicatorImg = Image()

    override fun addActorsOnGroup() {
        addActor(aIndicatorImg)
        aIndicatorImg.apply {
            setSize(this@AIndicator.width, this@AIndicator.height)
            setOrigin(Align.center)
            animHide()
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun animShowLoader() {
        animHideNoWifi {
            val timeAlpha  = 0.3f
            val timeRotate = 1f

            aIndicatorImg.apply {
                drawable = TextureRegionDrawable(screen.game.themeUtil.assets.LOADER)
                clearActions()
                addAction(Actions.parallel(
                    Actions.fadeIn(timeAlpha),
                    Actions.forever(Actions.rotateBy(-360f, timeRotate, Interpolation.circle))
                ))
            }
        }
    }

    fun animShowNoWifi(block: () -> Unit = {}) {
        animHideLoader {
            val timeAlpha  = 0.3f
            val timeScale  = 0.3f
            aIndicatorImg.apply {
                drawable = TextureRegionDrawable(screen.game.themeUtil.assets.NO_WIFI)
                clearActions()
                addAction(Actions.sequence(
                    Actions.fadeIn(timeAlpha),
                    Actions.repeat(5, Actions.sequence(
                        Actions.scaleTo(0.5f, 0.5f, timeScale, Interpolation.sine),
                        Actions.scaleTo(1f, 1f, timeScale, Interpolation.sine),
                    )),
                    Actions.fadeOut(timeAlpha),
                    Actions.run(block)
                ))
            }
        }
    }

    fun animHideLoader(block: () -> Unit = {}) {
        aIndicatorImg.apply {
            clearActions()
            addAction(Actions.sequence(
                Actions.fadeOut(0.3f),
                Actions.rotateTo(0f),
                Actions.run(block)
            ))
        }
    }

    fun animHideNoWifi(block: () -> Unit = {}) {
        aIndicatorImg.apply {
            clearActions()
            addAction(Actions.sequence(
                Actions.fadeOut(0.3f),
                Actions.scaleTo(1f, 1f),
                Actions.run(block)
            ))
        }
    }


}