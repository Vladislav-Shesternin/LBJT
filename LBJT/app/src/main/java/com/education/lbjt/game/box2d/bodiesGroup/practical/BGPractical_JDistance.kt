package com.education.lbjt.game.box2d.bodiesGroup.practical

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef
import com.education.lbjt.game.actors.practical_settings.APracticalSettings_JDistance
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.bodies.`object`.BCObject
import com.education.lbjt.game.box2d.bodies.`object`.BHObject
import com.education.lbjt.game.utils.TIME_ANIM_ALPHA_PRACTICAL_BODY
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedMouseScreen
import com.education.lbjt.game.utils.toB2

class BGPractical_JDistance(_screenBox2d: AdvancedMouseScreen): AbstractBGPractical(_screenBox2d) {

    override val title              = "Distance Joint"
    override val aPracticalSettings = APracticalSettings_JDistance(screenBox2d)

    // Body
    private val bStaticHorizontal = BHObject(screenBox2d, BodyDef.BodyType.StaticBody)
    private val bDynamicCircle    = BCObject(screenBox2d, BodyDef.BodyType.DynamicBody)

    // Joint
    private val jDistance = AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d)

    override fun createBodyBlock() {
        initB_Static()
        initB_Dynamic()

        createB_Static()
        createB_Dynamic()

        createJ_Distance()
        bDynamicCircle.actor?.let { it.postDrawArray.add(AdvancedGroup.Static.Drawer { alpha -> jDistance.drawJoint(alpha) }) }
    }

    override fun hideAndToStartBody(endBlock: () -> Unit) {
        setNoneId()
        bStaticHorizontal.actor?.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY)
        bDynamicCircle.apply {
            actor?.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY) {
                body?.also { b ->
                    b.setLinearVelocity(0f, 0f)
                    b.isAwake = false
                    b.setTransform(this@BGPractical_JDistance.position.cpy().add(350f, 691f).toB2, 0f)
                }
            }
        }
        endBlock()
    }

    override fun showAndUpdateBody() {
        jDistance.destroy()
        createJ_Distance()

        setOriginalId()
        arrayOf(bStaticHorizontal, bDynamicCircle).onEach { it.actor?.animShow(TIME_ANIM_ALPHA_PRACTICAL_BODY) }
        bDynamicCircle.body?.isAwake = true
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Static() {
        bStaticHorizontal.apply {
            id = BodyId.Practical.STATIC
            collisionList.add(BodyId.Practical.DYNAMIC)
        }
    }

    private fun initB_Dynamic() {
        bDynamicCircle.apply {
            id = BodyId.Practical.DYNAMIC
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.Practical.STATIC, BodyId.Practical.DYNAMIC))
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        createBody(bStaticHorizontal, 265f, 776f, 170f, 70f)
    }

    private fun createB_Dynamic() {
        createBody(bDynamicCircle, 265f, 446f, 170f, 170f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun createJ_Distance() {
        createJoint(jDistance, DistanceJointDef().apply {
            bodyA = bStaticHorizontal.body
            bodyB = bDynamicCircle.body
            collideConnected = true

            localAnchorA.set(APracticalSettings_JDistance.localAnchorAValue.cpy().sub(bStaticHorizontal.center))
            localAnchorB.set(APracticalSettings_JDistance.localAnchorBValue.cpy().sub(bDynamicCircle.center))

            length       = APracticalSettings_JDistance.lengthValue
            frequencyHz  = APracticalSettings_JDistance.frequencyHzValue
            dampingRatio = APracticalSettings_JDistance.dampingRatioValue
        })
    }

}