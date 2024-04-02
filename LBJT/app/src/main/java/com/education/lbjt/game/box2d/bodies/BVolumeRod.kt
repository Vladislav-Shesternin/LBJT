package com.education.lbjt.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.education.lbjt.game.actors.image.AImage
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup

class BVolumeRod(
    override val screenBox2d: AdvancedBox2dScreen,
    val type: Static.Type
): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.2f
        friction    = 0.2f
    }
    override var actor: AdvancedGroup? = AImage(screenBox2d, getRegionByType(type))

    fun getActor(): AImage? = actor as? AImage

    private fun getRegionByType(type: Static.Type) = when(type) {
        Static.Type.QUIET  -> screenBox2d.game.themeUtil.assets.ROD_QUIET
        Static.Type.LOUDER -> screenBox2d.game.themeUtil.assets.ROD_LOUDER
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    object Static {
        enum class Type {
            QUIET, LOUDER
        }
    }
}