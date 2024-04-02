//package com.education.lbjt.game.box2d.bodies
//
//import com.badlogic.gdx.physics.box2d.BodyDef
//import com.badlogic.gdx.physics.box2d.FixtureDef
//import com.education.lbjt.game.actors.button.AButton_Monetization
//import com.education.lbjt.game.box2d.AbstractBody
//import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
//import com.education.lbjt.game.utils.advanced.AdvancedGroup
//
//class BMonetizationBtn(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
//    override val name       = "monetization_btn"
//    override val bodyDef    = BodyDef().apply {
//        type = BodyDef.BodyType.DynamicBody
//    }
//    override val fixtureDef = FixtureDef().apply {
//        density     = 1f
//        restitution = 0.4f
//        friction    = 0.4f
//    }
//    override var actor: AdvancedGroup? = AButton_Monetization(screenBox2d)
//
//    fun getActor(): AButton_Monetization? = actor as? AButton_Monetization
//}