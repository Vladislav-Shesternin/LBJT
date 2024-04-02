package com.education.lbjt.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.education.lbjt.game.actors.frame.AFrameLanguage
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup

class BFrameLanguage(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "fame_language"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.2f
        friction    = 0.2f
    }
    override var actor: AdvancedGroup? = AFrameLanguage(screenBox2d)

    fun getActor(): AFrameLanguage? = actor as? AFrameLanguage
}