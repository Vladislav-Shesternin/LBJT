package com.education.lbjt.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.education.lbjt.game.actors.image.AImage
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup

class BLiftGear(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0f
        friction    = 1f
    }
    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.themeUtil.assets.LIFT_GEAR)
}