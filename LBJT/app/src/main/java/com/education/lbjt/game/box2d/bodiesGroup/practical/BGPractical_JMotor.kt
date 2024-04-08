package com.education.lbjt.game.box2d.bodiesGroup.practical

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.joints.MotorJoint
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef
import com.education.lbjt.game.actors.practical_settings.APracticalSettings_JMotor
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.bodies.`object`.BCObject
import com.education.lbjt.game.utils.JOINT_WIDTH
import com.education.lbjt.game.utils.TIME_ANIM_ALPHA_PRACTICAL_BODY
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.toUI

class BGPractical_JMotor(_screenBox2d: AdvancedBox2dScreen): AbstractBGPractical(_screenBox2d) {

    override val title              = "Motor Joint"
    override val aPracticalSettings = APracticalSettings_JMotor(screenBox2d)

    // Body
    private val bStaticCircle  = BCObject(screenBox2d, BodyDef.BodyType.StaticBody)
    private val bDynamicCircle = BCObject(screenBox2d, BodyDef.BodyType.DynamicBody)

    // Joint
    private val jMotor = AbstractJoint<MotorJoint, MotorJointDef>(screenBox2d)

    // Field
    private val tmpPositionA  = Vector2()
    private val tmpPositionB  = Vector2()
    private val startPosition = Vector2()

    override fun createBodyBlock() {
        initB_Dynamic()

        createB_Static_Dynamic()
        createJ_Motor()

        drawJoint()
    }

    override fun hideAndToStartBody(endBlock: () -> Unit) {
        jMotor.destroy()

        setNoneId()

        bStaticCircle.actor?.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY)
        bDynamicCircle.actor?.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY) {
            bDynamicCircle.body?.also { b ->
                b.setLinearVelocity(0f, 0f)
                b.isAwake = false

                b.setTransform(startPosition, 0f)
            }
        }
        endBlock()
    }

    override fun showAndUpdateBody() {
        createJ_Motor()

        setOriginalId()
        arrayOf(bStaticCircle, bDynamicCircle).onEach {
            it.actor?.animShow(TIME_ANIM_ALPHA_PRACTICAL_BODY)
            it.body?.isAwake = true
        }
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Dynamic() {
        bDynamicCircle.apply {
            id = BodyId.Practical.DYNAMIC
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.Practical.DYNAMIC))
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static_Dynamic() {
        createBody(bDynamicCircle, 265f, 543f, 170f, 170f)
        createBody(bStaticCircle, 337f, 616f, 25f, 25f)

        startPosition.set(bDynamicCircle.body?.position)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Motor() {
        createJoint(jMotor, MotorJointDef().apply {
            bodyA = bStaticCircle.body
            bodyB = bDynamicCircle.body

            maxForce  = APracticalSettings_JMotor.maxForceValue * bodyB.mass
            maxTorque = APracticalSettings_JMotor.maxTorqueValue * bodyB.mass
            correctionFactor = APracticalSettings_JMotor.correctionFactorValue
        })
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun drawJoint() {
        bStaticCircle.actor?.let { it.postDrawArray.add(AdvancedGroup.Static.Drawer { alpha ->
            jMotor.joint?.let { j ->
                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(j.anchorA).toUI,
                    tmpPositionB.set(j.anchorB).toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )
            }
        }) }
    }

}