package com.education.lbjt.game.actors.checkbox

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class ACheckBoxTouch_YanYinTheme(override val screen: AdvancedScreen): AdvancedGroup() {

    override var standartW = 131f

    private val lightImg  = Image(screen.game.themeUtil.assets.YIN_YAN_LIGHT)
    private val yanYinBox = ACheckBoxTouch(screen, ACheckBox.Static.Type.YAN_YIN)


    override fun addActorsOnGroup() {
        addLight()
        addYanYinBox()
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun addLight() {
        addActor(lightImg)
        lightImg.apply {
            setBoundsStandart(-59f,-60f,251f,251f)
            setOrigin(Align.center)

            val scale = 0.2f
            addAction(Actions.forever(Actions.sequence(
                Actions.scaleBy(-scale, -scale, 1f),
                Actions.scaleBy(scale, scale, 1f),
            )))
        }
    }

    private fun addYanYinBox() {
        addAndFillActor(yanYinBox)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun setOnCheckListener(block: (Boolean) -> Unit) {
        yanYinBox.setOnCheckListener { block(it) }
    }

    fun check(isInvokeCheckBlock: Boolean = true) {
        yanYinBox.check(isInvokeCheckBlock)
    }

    fun uncheck(isInvokeCheckBlock: Boolean = true) {
        yanYinBox.uncheck(isInvokeCheckBlock)
    }

}