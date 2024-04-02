package com.education.lbjt.game.box2d.bodies.`object`

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.education.lbjt.game.actors.image.AImage
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup

class BVObject(
    override val screenBox2d: AdvancedBox2dScreen,
    private val bodyType: BodyType,
): AbstractBody() {

    override val name       = "v_object"
    override val bodyDef    = BodyDef().apply {
        type = bodyType
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.3f
        friction    = 0.3f
    }
    override var actor: AdvancedGroup? = AImage(screenBox2d, getRegionByType())

    fun getActor(): AImage? = actor as? AImage

    private fun getRegionByType(): TextureRegion = when (bodyType) {
        BodyType.StaticBody    -> screenBox2d.game.themeUtil.assets.V_STATIC
        BodyType.KinematicBody -> screenBox2d.game.themeUtil.assets.V_KINEMATIC
        BodyType.DynamicBody   -> screenBox2d.game.themeUtil.assets.V_DYNAMIC
    }

}