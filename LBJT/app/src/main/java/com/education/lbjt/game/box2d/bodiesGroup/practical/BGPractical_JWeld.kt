package com.education.lbjt.game.box2d.bodiesGroup.practical

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.joints.WeldJoint
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef
import com.education.lbjt.game.actors.practical_settings.APracticalSettings_JWeld
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.bodies.`object`.BCObject
import com.education.lbjt.game.box2d.bodies.`object`.BHObject
import com.education.lbjt.game.utils.DEGTORAD
import com.education.lbjt.game.utils.JOINT_WIDTH
import com.education.lbjt.game.utils.TIME_ANIM_ALPHA_PRACTICAL_BODY
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.toUI

class BGPractical_JWeld(_screenBox2d: AdvancedBox2dScreen): AbstractBGPractical(_screenBox2d) {

    override val title              = "Weld Joint"
    override val aPracticalSettings = APracticalSettings_JWeld(screenBox2d)

    // Body
    private val bStaticCircle      = BCObject(screenBox2d, BodyDef.BodyType.StaticBody)
    private val bDynamicHorizontal = BHObject(screenBox2d, BodyDef.BodyType.DynamicBody)

    // Joint
    private val jWeld = AbstractJoint<WeldJoint, WeldJointDef>(screenBox2d)

    // Field
    private val tmpPositionA = Vector2()
    private val tmpPositionB = Vector2()
    private val tmpPositionC = Vector2()
    private val startPositionDynamicBody = Vector2()

    override fun createBodyBlock() {
        initB_Static()
        initB_Dynamic()

        createB_Dynamic()
        createB_Static()

        createJ_Weld()

        drawJoint()
    }

    override fun hideAndToStartBody(endBlock: () -> Unit) {
        setNoneId()
        arrayOf(bStaticCircle.actor).onEach {  it?.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY) }
        bDynamicHorizontal.actor?.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY) {
            bDynamicHorizontal.body?.also { b ->
                b.setLinearVelocity(0f, 0f)
                b.isAwake = false
                b.setTransform(startPositionDynamicBody, 0f)
            }
        }
        endBlock()
    }

    override fun showAndUpdateBody() {
        jWeld.destroy()
        createJ_Weld()

        setOriginalId()
        arrayOf(bStaticCircle.actor, bDynamicHorizontal.actor).onEach { it?.animShow(TIME_ANIM_ALPHA_PRACTICAL_BODY) }
        bDynamicHorizontal.body?.isAwake = true
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Static() {
        bStaticCircle.apply {
            id = BodyId.Practical.STATIC
            collisionList.add(BodyId.Practical.DYNAMIC)
        }
    }

    private fun initB_Dynamic() {
        bDynamicHorizontal.apply {
            id = BodyId.Practical.DYNAMIC
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.Practical.STATIC, BodyId.Practical.DYNAMIC))
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        createBody(bStaticCircle, 325f, 602f, 50f, 50f)
    }

    private fun createB_Dynamic() {
        createBody(bDynamicHorizontal, 220f, 574f, 260f, 107f)
        startPositionDynamicBody.set(bDynamicHorizontal.body?.position)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Weld() {
        createJoint(jWeld, WeldJointDef().apply {
            bodyA = bStaticCircle.body
            bodyB = bDynamicHorizontal.body
            collideConnected = false

            localAnchorA.set(APracticalSettings_JWeld.localAnchorAValue.cpy().sub(bStaticCircle.center))
            localAnchorB.set(APracticalSettings_JWeld.localAnchorBValue.cpy().sub(bDynamicHorizontal.center))

            referenceAngle = APracticalSettings_JWeld.referenceAngleValue * DEGTORAD
            frequencyHz    = APracticalSettings_JWeld.frequencyHzValue
            dampingRatio   = APracticalSettings_JWeld.dampingRatioValue
        })
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun drawJoint() {
        bStaticCircle.actor?.let { it.postDrawArray.add(AdvancedGroup.Static.Drawer { alpha ->
            jWeld.joint?.let { j ->
                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(bStaticCircle.body!!.position).toUI,
                    tmpPositionB.set(bStaticCircle.body!!.position).add(j.localAnchorA).toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )

                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(bDynamicHorizontal.body!!.position).toUI,
                    tmpPositionB.set(bDynamicHorizontal.body!!.position).add(tmpPositionC.set(j.anchorB).sub(bDynamicHorizontal.body!!.position)).toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )

                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(j.anchorA).toUI,
                    tmpPositionB.set(j.anchorB).toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )
            }
        }) }
    }

}