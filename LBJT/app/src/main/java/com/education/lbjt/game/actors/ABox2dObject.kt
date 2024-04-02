package com.education.lbjt.game.actors

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.github.tommyettinger.textra.TypingLabel
import com.education.lbjt.R
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class ABox2dObject(
    override val screen: AdvancedScreen,
    private val type: Static.Type,
    private val font: BitmapFont,
): AdvancedGroup() {

    private val initData = getInitDataByType()

    private val circleImg      = Image(initData.cRegion)
    private val horizontalImg  = Image(initData.hRegion)
    private val verticalImg    = Image(initData.vRegion)
    private val jointImg       = Image(screen.drawerUtil.getRegion(GameColor.joint))
    private val descriptionLbl = TypingLabel(initData.description, Label.LabelStyle(font, GameColor.textGreen))


    override fun addActorsOnGroup() {
        if (type== Static.Type.Joint) addJointActors() else addCHVActors()
        addDescriptionLbl()
    }

    private fun addCHVActors() {
        addActors(circleImg, horizontalImg, verticalImg)
        circleImg.setBounds(42f,42f,85f,85f)
        horizontalImg.setBounds(42f,0f,85f,35f)
        verticalImg.setBounds(0f,0f,35f,85f)
    }

    private fun addJointActors() {
        addActor(jointImg)
        jointImg.setBounds(59f,0f,10f,128f)
    }

    private fun addDescriptionLbl() {
        addActor(descriptionLbl)
        descriptionLbl.apply {
            setBounds(145f,0f,520f,128f)
            wrap = true
        }
    }

    private fun getInitDataByType(): Static.InitData = when(type) {
        Static.Type.Static         -> Static.InitData(
            screen.game.themeUtil.assets.C_STATIC,
            screen.game.themeUtil.assets.H_STATIC,
            screen.game.themeUtil.assets.V_STATIC,
            screen.game.languageUtil.getStringResource(R.string.static_description)
        )
        Static.Type.Kinematic    -> Static.InitData(
            screen.game.themeUtil.assets.C_KINEMATIC,
            screen.game.themeUtil.assets.H_KINEMATIC,
            screen.game.themeUtil.assets.V_KINEMATIC,
            screen.game.languageUtil.getStringResource(R.string.kinematic_description)
        )
        Static.Type.Dynamic    -> Static.InitData(
            screen.game.themeUtil.assets.C_DYNAMIC,
            screen.game.themeUtil.assets.H_DYNAMIC,
            screen.game.themeUtil.assets.V_DYNAMIC,
            screen.game.languageUtil.getStringResource(R.string.dynamic_description)
        )
        Static.Type.Joint -> Static.InitData(description = screen.game.languageUtil.getStringResource(R.string.joint_description))
    }

    object Static {
        data class InitData(
            val cRegion: TextureRegion? = null,
            val hRegion: TextureRegion? = null,
            val vRegion: TextureRegion? = null,
            val description: String,
        )

        enum class Type {
            Static,
            Kinematic,
            Dynamic,
            Joint,
        }
    }

}