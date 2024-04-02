package com.education.lbjt.game.box2d.bodies

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.education.lbjt.game.actors.AIconSelectable
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup

class BIcon(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.6f
        friction    = 0.5f
    }
    override var actor: AdvancedGroup? = AIconSelectable(screenBox2d)

    fun getActor(): AIconSelectable? = actor as? AIconSelectable

    var isUpdated = false

    fun updateIcon(textureRegion: TextureRegion) {
        isUpdated = true
        getActor()?.updateIcon(textureRegion)
    }

}