package com.education.lbjt.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.education.lbjt.game.actors.button.AButton_Regular
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup

class BRegularBtn(
    override val screenBox2d: AdvancedBox2dScreen,
    val text     : String,
    val style    : LabelStyle,
    val labelType: AButton_Regular.Static.LabelType = AButton_Regular.Static.LabelType.DEFAULT,
): AbstractBody() {
    override val name       = "regular_btn"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.3f
        friction    = 0.3f
    }
    override var actor: AdvancedGroup? = AButton_Regular(screenBox2d, text, style, labelType)

    fun getActor(): AButton_Regular? = actor as? AButton_Regular
}