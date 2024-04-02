package com.education.lbjt.game.box2d.bodiesGroup

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.education.lbjt.game.box2d.AbstractBodyGroup
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.BodyId.AboutAuthor.ITEM
import com.education.lbjt.game.box2d.bodies.standart.BStatic
import com.education.lbjt.game.box2d.bodies.BThanksFrame
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.toB2

class BGThanksFrame(override val screenBox2d: AdvancedBox2dScreen): AbstractBodyGroup() {

    override val standartW = 700f

    // Body
    private val bStatic      = BStatic(screenBox2d)
    private val bThanksFrame = BThanksFrame(screenBox2d)

    // Joint
    private val jPrismatic     = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)
    private val jDistanceRight = AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d)
    private val jDistanceLeft  = AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d)

    // Field
    private val drawer = screenBox2d.drawerUtil.drawer

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        initB_ThanksFrame()

        createB_Static()
        createB_ThanksFrame()

        createJ_Prismatic()
        createJ_Distance()

        imitateImpulseThanksFrame()
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_ThanksFrame() {
        bThanksFrame.apply {
            id = ITEM
            collisionList.addAll(arrayOf(BodyId.BORDERS, ITEM))
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        createBody(bStatic, 342f, 28f, 15f, 15f)
    }

    private fun createB_ThanksFrame() {
        createBody(bThanksFrame, 214f, 0f, 271f, 72f)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Prismatic() {
        createJoint(jPrismatic, PrismaticJointDef().apply {
            bodyA = bStatic.body
            bodyB = bThanksFrame.body

            localAnchorB.set(Vector2(135f, 35f).subCenter(bodyB))
            localAxisA.set(1f, 0f)
        })
    }

    private fun createJ_Distance() {
        listOf(
            Static.DistanceJointData(jDistanceRight, Vector2(358f, 8f), Vector2(266f, 36f)),
            Static.DistanceJointData(jDistanceLeft, Vector2(-342f, 8f), Vector2(5f, 36f)),
        ).onEach { data ->
            createJoint(data.joint, DistanceJointDef().apply {
                bodyA = bStatic.body
                bodyB = bThanksFrame.body

                localAnchorA.set(data.anchorA.subCenter(bodyA))
                localAnchorB.set(data.anchorB.subCenter(bodyB))

                length = 214f.toStandartBG.toB2
                frequencyHz  = 0.5f
                dampingRatio = 0.1f
            })
            bThanksFrame.actor?.preDrawArray?.add(AdvancedGroup.Static.Drawer { alpha -> data.joint.drawJoint(alpha) })
        }

    }

    object Static {
        data class DistanceJointData(
            val joint: AbstractJoint<DistanceJoint, DistanceJointDef>,
            val anchorA: Vector2,
            val anchorB: Vector2,
        )
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun imitateImpulseThanksFrame() {
        bThanksFrame.body?.let { it.applyLinearImpulse(Vector2(500f, 0f), it.worldCenter, true) }
    }

}