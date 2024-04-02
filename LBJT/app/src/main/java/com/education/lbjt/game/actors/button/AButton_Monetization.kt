package com.education.lbjt.game.actors.button

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.education.lbjt.R
import com.education.lbjt.game.actors.label.ASpinningLabel
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.actor.disable
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.font.FontParameter.CharType

class AButton_Monetization(override val screen: AdvancedScreen): AButton(screen, AButton.Static.Type.MONETIZATION) {

    override var standartW = 296f

    private val initData = getInitDataByType()

    private val parameter = FontParameter()

    private val label = ASpinningLabel(screen, initData.text, Label.LabelStyle(screen.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.LATIN.chars+CharType.CYRILLIC.chars).setSize(25)), GameColor.textGreen))
    private val image = Image(initData.region)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()
        addLabel()
        addImage()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addLabel() {
        addActor(label)
        label.apply {
            setBoundsStandart(initData.positionLabel, initData.sizeLabel)
            disable()
        }
    }

    private fun addImage() {
        addActor(image)
        image.apply {
            setBoundsStandart(initData.positionImage, initData.sizeImage)
            disable()
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun getInitDataByType(): Static.InitData = Static.InitData(
            screen.game.languageUtil.getStringResource(R.string.ads),
            screen.game.themeUtil.assets.ADS,
            Vector2(95f, 189f),
            Vector2(106f, 30f),
            Vector2(79f, 81f),
            Vector2(138f, 93f),
        )


    object Static {
        data class InitData(
            val text: String,
            val region: TextureRegion,
            val positionLabel: Vector2,
            val sizeLabel: Vector2,
            val positionImage: Vector2,
            val sizeImage: Vector2,
        )

        enum class Type {
            ADS, GIFT
        }
    }

}