package com.education.lbjt.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.education.lbjt.game.actors.frame.AFrameNickname
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup

class BFrameNickname(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "frame_nickname"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.5f
        friction    = 0.3f
    }
    override var actor: AdvancedGroup? = AFrameNickname(screenBox2d)

    fun getActor(): AFrameNickname? = actor as? AFrameNickname

    var isUpdated = false

    fun updateNickname(nickname: String) {
        isUpdated = true
        getActor()?.updateNickname(nickname)
    }

    fun getNickname(): String = getActor()?.getNickname() ?: ""

}