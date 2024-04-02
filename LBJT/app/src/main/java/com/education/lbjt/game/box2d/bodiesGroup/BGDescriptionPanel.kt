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
import com.education.lbjt.game.box2d.bodies.BDescriptionPanel
import com.education.lbjt.game.box2d.bodies.standart.BStatic
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.toB2

class BGDescriptionPanel(override val screenBox2d: AdvancedBox2dScreen): AbstractBodyGroup() {

    override val standartW = 595f

    // Body
    private val bStatic           = BStatic(screenBox2d)
    private val bDescriptionPanel = BDescriptionPanel(screenBox2d)

    // Joint
    private val jPrismatic     = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)
    private val jDistanceRight = AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d)
    private val jDistanceLeft  = AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d)

    // Field
    private val drawer = screenBox2d.drawerUtil.drawer

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        initB_DescriptionPanel()

        createB_Static()
        createB_DescriptionPanel()

        createJ_Prismatic()
        createJ_Distance()
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_DescriptionPanel() {
        bDescriptionPanel.apply {
            id = ITEM
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.SEPARATOR, ITEM))
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        createBody(bStatic, 290f, 522f, 15f, 15f)
    }

    private fun createB_DescriptionPanel() {
        createBody(bDescriptionPanel, 0f, 0f, 595f, 484f)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Prismatic() {
        createJoint(jPrismatic, PrismaticJointDef().apply {
            bodyA = bStatic.body
            bodyB = bDescriptionPanel.body

            localAxisA.set(0f, 1f)
        })
    }

    private fun createJ_Distance() {
        listOf(
            Static.DistanceJointData(jDistanceRight, Vector2(223f, 7f), Vector2(513f, 450f)),
            Static.DistanceJointData(jDistanceLeft, Vector2(-208f, 7f), Vector2(81f, 450f)),
        ).onEach { data ->
            createJoint(data.joint, DistanceJointDef().apply {
                bodyA = bStatic.body
                bodyB = bDescriptionPanel.body

                localAnchorA.set(data.anchorA.subCenter(bodyA))
                localAnchorB.set(data.anchorB.subCenter(bodyB))

                length = 78f.toStandartBG.toB2
                frequencyHz = 1.2f
                dampingRatio = 0.2f
            })
            bDescriptionPanel.actor?.preDrawArray?.add(AdvancedGroup.Static.Drawer { alpha -> data.joint.drawJoint(alpha) })
        }

    }

    object Static {
        data class DistanceJointData(
            val joint: AbstractJoint<DistanceJoint, DistanceJointDef>,
            val anchorA: Vector2,
            val anchorB: Vector2,
        )
    }

}