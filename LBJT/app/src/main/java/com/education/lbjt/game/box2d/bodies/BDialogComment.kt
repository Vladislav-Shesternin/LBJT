package com.education.lbjt.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.education.lbjt.game.actors.dialog.ADialogComment
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup

class BDialogComment(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "dialog_comment"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.3f
        friction    = 0.3f
    }
    override var actor: AdvancedGroup? = ADialogComment(screenBox2d)

    fun getActor(): ADialogComment? = actor as? ADialogComment
}