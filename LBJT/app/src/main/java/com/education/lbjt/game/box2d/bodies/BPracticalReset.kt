package com.education.lbjt.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.education.lbjt.game.actors.button.AButton
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup

class BPracticalReset(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "reset"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 3f
        restitution = 0.3f
        friction    = 0.3f
    }
    override var actor: AdvancedGroup? = AButton(screenBox2d, AButton.Static.Type.RESET)

    fun getActor(): AButton? = actor as? AButton

}