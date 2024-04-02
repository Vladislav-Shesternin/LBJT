package com.education.lbjt.game.box2d.bodiesGroup.practical

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef
import com.badlogic.gdx.physics.box2d.joints.RopeJoint
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef
import com.education.lbjt.game.actors.practical_settings.APracticalSettings_JFriction
import com.education.lbjt.game.actors.practical_settings.APracticalSettings_JRope
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.bodies.`object`.BCObject
import com.education.lbjt.game.box2d.bodies.`object`.BHObject
import com.education.lbjt.game.box2d.bodies.`object`.BVObject
import com.education.lbjt.game.utils.JOINT_WIDTH
import com.education.lbjt.game.utils.TIME_ANIM_ALPHA_PRACTICAL_BODY
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.toUI

class BGPractical_JRope(_screenBox2d: AdvancedBox2dScreen): AbstractBGPractical(_screenBox2d) {

    override val title              = "Rope Joint"
    override val aPracticalSettings = APracticalSettings_JRope(screenBox2d)

    // Body
    private val bStaticCircle        = BCObject(screenBox2d, BodyDef.BodyType.StaticBody)
    private val bListDynamicVertical = List(3) { BVObject(screenBox2d, BodyDef.BodyType.DynamicBody) }

    // Joint
    private val jRopeList = List(3) { AbstractJoint<RopeJoint, RopeJointDef>(screenBox2d) }

    // Field
    private val tmpPositionA = Vector2()
    private val tmpPositionB = Vector2()
    private val tmpPositionC = Vector2()
    private val startPositionDynamicBodyList = List(3) { Vector2() }

    override fun createBodyBlock() {
        initB_Static()
        initB_Dynamic()

        createB_Dynamic()
        createB_Static()

        createJ_Rope()

        drawJoint()
    }

    override fun hideAndToStartBody(endBlock: () -> Unit) {
        setNoneId()
        arrayOf(bStaticCircle.actor).onEach { it?.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY) }
        bListDynamicVertical.onEachIndexed { index, bv -> bv.actor?.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY) {
            bv.body?.also { b ->
                b.setLinearVelocity(0f, 0f)
                b.isAwake = false
                b.setTransform(startPositionDynamicBodyList[index], 0f)
            }
        } }
        endBlock()
    }

    override fun showAndUpdateBody() {
        jRopeList.onEach { it.destroy() }
        createJ_Rope()

        setOriginalId()
        bStaticCircle.actor?.animShow(TIME_ANIM_ALPHA_PRACTICAL_BODY)
        bListDynamicVertical.onEach {
            it.actor?.animShow(TIME_ANIM_ALPHA_PRACTICAL_BODY)
            it.body?.isAwake = true
        }
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
        bListDynamicVertical.onEach {
            it.apply {
                id = BodyId.Practical.DYNAMIC
                collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.Practical.STATIC, BodyId.Practical.DYNAMIC))
            }
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        createBody(bStaticCircle, 325f, 845f, 50f, 50f)
    }

    private fun createB_Dynamic() {
        var ny = 680f
        bListDynamicVertical.onEachIndexed { index, bVerObj ->
            createBody(bVerObj, 326f, ny, 48f, 116f)
            ny -= (48f + 116f)

            startPositionDynamicBodyList[index].set(bVerObj.body?.position)
        }
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Rope() {
        createJoint(jRopeList[0], RopeJointDef().apply {
            bodyA = bStaticCircle.body
            bodyB = bListDynamicVertical[0].body
            collideConnected = true

            localAnchorA.set(APracticalSettings_JRope.localAnchorAValue.cpy().sub(bStaticCircle.center))
            localAnchorB.set(APracticalSettings_JRope.localAnchorBValue.cpy().sub(bListDynamicVertical[0].center))

            maxLength = APracticalSettings_JRope.maxLength
        })
        jRopeList.takeLast(2).onEachIndexed { index, jRope ->
            createJoint(jRope, RopeJointDef().apply {
                bodyA = bListDynamicVertical[index].body
                bodyB = bListDynamicVertical[index + 1].body
                collideConnected = true

                localAnchorA.set(Vector2(24f, 0f).subCenter(bodyA))
                localAnchorB.set(APracticalSettings_JRope.localAnchorBValue.cpy().sub(bListDynamicVertical[index].center))

                maxLength = APracticalSettings_JRope.maxLength
            })
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun drawJoint() {
        bStaticCircle.actor?.let { it.postDrawArray.add(AdvancedGroup.Static.Drawer { alpha ->
            jRopeList[0].joint?.let { j ->
                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(bStaticCircle.body!!.position).toUI,
                    j.anchorA.toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )

                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(bListDynamicVertical[0].body!!.position).toUI,
                    tmpPositionB.set(bListDynamicVertical[0].body!!.position).add(
                        tmpPositionC.set(j.anchorB).sub(bListDynamicVertical[0].body!!.position)
                    ).toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )

                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(j.anchorA).toUI,
                    tmpPositionB.set(j.anchorB).toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )
            }

            jRopeList.takeLast(2).onEachIndexed { index, jRope ->
                jRope.joint?.let { j ->
                    screenBox2d.drawerUtil.drawer.line(
                        tmpPositionA.set(bListDynamicVertical[index].body!!.position).toUI,
                        tmpPositionB.set(bListDynamicVertical[index].body!!.position).add(
                            tmpPositionC.set(j.anchorA).sub(bListDynamicVertical[index].body!!.position)
                        ).toUI,
                        colorJoint.apply { a = alpha }, JOINT_WIDTH
                    )

                    screenBox2d.drawerUtil.drawer.line(
                        tmpPositionA.set(bListDynamicVertical[index+1].body!!.position).toUI,
                        tmpPositionB.set(bListDynamicVertical[index+1].body!!.position).add(
                            tmpPositionC.set(j.anchorB).sub(bListDynamicVertical[index+1].body!!.position)
                        ).toUI,
                        colorJoint.apply { a = alpha }, JOINT_WIDTH
                    )

                    screenBox2d.drawerUtil.drawer.line(
                        tmpPositionA.set(j.anchorA).toUI,
                        tmpPositionB.set(j.anchorB).toUI,
                        colorJoint.apply { a = alpha }, JOINT_WIDTH
                    )
                }
            }
        }) }
    }

}