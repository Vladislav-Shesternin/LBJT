package com.education.lbjt.game.box2d.bodiesGroup.practical

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef
import com.education.lbjt.game.actors.practical_settings.APracticalSettings_JPulley
import com.education.lbjt.game.actors.practical_settings.APracticalSettings_JRope
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.bodies.`object`.BCObject
import com.education.lbjt.game.utils.JOINT_WIDTH
import com.education.lbjt.game.utils.TIME_ANIM_ALPHA_PRACTICAL_BODY
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.toB2
import com.education.lbjt.game.utils.toUI

class BGPractical_JPulley(_screenBox2d: AdvancedBox2dScreen): AbstractBGPractical(_screenBox2d) {

    override val title              = "Pulley Joint"
    override val aPracticalSettings = APracticalSettings_JPulley(screenBox2d)

    // Body
    private val bDynamicCircleLeft  = BCObject(screenBox2d, BodyDef.BodyType.DynamicBody)
    private val bDynamicCircleRight = BCObject(screenBox2d, BodyDef.BodyType.DynamicBody)

    // Joint
    private val jPulley = AbstractJoint<PulleyJoint, PulleyJointDef>(screenBox2d)

    // Field
    private val tmpPositionA = Vector2()
    private val tmpPositionB = Vector2()
    private val tmpPositionC = Vector2()
    private val startPositionDynamicBodyLeft  = Vector2()
    private val startPositionDynamicBodyRight = Vector2()

    override fun createBodyBlock() {
        initB_Dynamic()

        createB_Dynamic()
        createJ_Pulley()

        drawJoint()
    }

    override fun hideAndToStartBody(endBlock: () -> Unit) {
        setNoneId()
        arrayOf(bDynamicCircleLeft, bDynamicCircleRight).onEachIndexed { index, bco ->
            bco.actor?.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY) {
                bco.body?.also { b ->
                    b.setLinearVelocity(0f, 0f)
                    b.isAwake = false
                    b.setTransform(if (index == 0) startPositionDynamicBodyLeft else startPositionDynamicBodyRight, 0f)
                }
            }
        }
        endBlock()
    }

    override fun showAndUpdateBody() {
        jPulley.destroy()
        createJ_Pulley()

        setOriginalId()
        arrayOf(bDynamicCircleLeft, bDynamicCircleRight).onEach { bco ->
            bco.actor?.animShow(TIME_ANIM_ALPHA_PRACTICAL_BODY)
            bco.body?.isAwake = true
        }
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Dynamic() {
        arrayOf(bDynamicCircleLeft, bDynamicCircleRight).onEach { bco ->
            bco.apply {
                id = BodyId.Practical.DYNAMIC
                collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.Practical.STATIC, BodyId.Practical.DYNAMIC))
            }
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Dynamic() {
        createBody(bDynamicCircleLeft, 117f, 829f, 100f, 100f)
        startPositionDynamicBodyLeft.set(bDynamicCircleLeft.body?.position)

        createBody(bDynamicCircleRight, 483f, 829f, 100f, 100f)
        startPositionDynamicBodyRight.set(bDynamicCircleRight.body?.position)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Pulley() {
        createJoint(jPulley, PulleyJointDef().apply {
            bodyA = bDynamicCircleLeft.body
            bodyB = bDynamicCircleRight.body
            collideConnected = true

            groundAnchorA.set(position.cpy().add(167f, 879f).toB2)
            groundAnchorB.set(position.cpy().add(533f, 879f).toB2)

            localAnchorA.set(APracticalSettings_JPulley.localAnchorAValue.cpy().sub(bDynamicCircleLeft.center))
            localAnchorB.set(APracticalSettings_JPulley.localAnchorBValue.cpy().sub(bDynamicCircleRight.center))

            lengthA = APracticalSettings_JPulley.lengthAValue
            lengthB = APracticalSettings_JPulley.lengthBValue

            ratio = APracticalSettings_JPulley.ratioValue
        })
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun drawJoint() {
        bDynamicCircleRight.actor?.let { it.postDrawArray.add(AdvancedGroup.Static.Drawer { alpha ->
            jPulley.joint?.let { j ->
                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(bDynamicCircleLeft.body!!.position).toUI,
                    tmpPositionB.set(bDynamicCircleLeft.body!!.position).add(
                        tmpPositionC.set(j.anchorA).sub(bDynamicCircleLeft.body!!.position)
                    ).toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )

                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(bDynamicCircleRight.body!!.position).toUI,
                    tmpPositionB.set(bDynamicCircleRight.body!!.position).add(
                        tmpPositionC.set(j.anchorB).sub(bDynamicCircleRight.body!!.position)
                    ).toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )

                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(j.groundAnchorA).toUI,
                    tmpPositionB.set(j.anchorA).toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )

                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(j.groundAnchorB).toUI,
                    tmpPositionB.set(j.anchorB).toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )

                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(j.groundAnchorA).toUI,
                    tmpPositionB.set(j.groundAnchorB).toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )
            }
        }) }
    }

}