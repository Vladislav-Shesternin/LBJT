package com.education.lbjt.game.box2d.bodiesGroup.practical

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.education.lbjt.game.actors.image.AImage
import com.education.lbjt.game.actors.practical_settings.APracticalSettings_JPrismatic
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.bodies.`object`.BCObject
import com.education.lbjt.game.utils.DEGTORAD
import com.education.lbjt.game.utils.JOINT_WIDTH
import com.education.lbjt.game.utils.TIME_ANIM_ALPHA_PRACTICAL_BODY
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.actor.disable
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedStage
import com.education.lbjt.game.utils.toUI

class BGPractical_JPrismatic(_screenBox2d: AdvancedBox2dScreen): AbstractBGPractical(_screenBox2d) {

    override val title              = "Prismatic Joint"
    override val aPracticalSettings = APracticalSettings_JPrismatic(screenBox2d)

    // Actor
    private val aLinesImg   = AImage(screenBox2d, screenBox2d.game.themeUtil.assets.PRACTICAL_LINES)
    private val aDegreesImg = AImage(screenBox2d, screenBox2d.game.themeUtil.assets.PRACTICAL_DEGREES)

    // Body
    private val bStaticCircle  = BCObject(screenBox2d, BodyDef.BodyType.StaticBody)
    private val bDynamicCircle = BCObject(screenBox2d, BodyDef.BodyType.DynamicBody)

    // Joint
    private val jPrismatic = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)

    // Field
    private val tmpPositionA = Vector2()
    private val tmpPositionB = Vector2()
    private val startPositionDynamicBody = Vector2()

    override fun createBodyBlock() {
        screenBox2d.stageUI.apply {
            addLinesImg()
        }

        initB_Static()
        initB_Dynamic()

        createB_Dynamic()
        createB_Static()

        createJ_Prismatic()

        drawJoint()

        screenBox2d.stageUI.apply {
            addDegreesImg()
        }
    }

    override fun hideAndToStartBody(endBlock: () -> Unit) {
        setNoneId()
        arrayOf(bStaticCircle.actor, aLinesImg, aDegreesImg).onEach {  it?.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY) }
        bDynamicCircle.actor?.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY) {
            bDynamicCircle.body?.also { b ->
                b.setLinearVelocity(0f, 0f)
                b.isAwake = false
                b.setTransform(startPositionDynamicBody, 0f)
            }
        }
        endBlock()
    }

    override fun showAndUpdateBody() {
        jPrismatic.destroy()
        createJ_Prismatic()

        setOriginalId()
        arrayOf(
            bStaticCircle.actor, bDynamicCircle.actor,
            aLinesImg, aDegreesImg
        ).onEach { it?.animShow(TIME_ANIM_ALPHA_PRACTICAL_BODY) }
        bDynamicCircle.body?.isAwake = true
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
        bDynamicCircle.apply {
            id = BodyId.Practical.DYNAMIC
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.Practical.STATIC, BodyId.Practical.DYNAMIC))
        }
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addLinesImg() {
        addActor(aLinesImg)
        aLinesImg.setBoundsStandartBG(40f, 317f, 621f, 621f)
        aLinesImg.disable()
    }

    private fun AdvancedStage.addDegreesImg() {
        addActor(aDegreesImg)
        aDegreesImg.setBoundsStandartBG(33f, 330f, 643f, 598f)
        aDegreesImg.disable()
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        createBody(bStaticCircle, 325f, 602f, 50f, 50f)
    }

    private fun createB_Dynamic() {
        createBody(bDynamicCircle, 265f, 543f, 170f, 170f)
        startPositionDynamicBody.set(bDynamicCircle.body?.position)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Prismatic() {
        createJoint(jPrismatic, PrismaticJointDef().apply {
            bodyA = bStaticCircle.body
            bodyB = bDynamicCircle.body
            collideConnected = false

            localAnchorA.set(APracticalSettings_JPrismatic.localAnchorAValue.cpy().sub(bStaticCircle.center))
            localAnchorB.set(APracticalSettings_JPrismatic.localAnchorBValue.cpy().sub(bDynamicCircle.center))
            localAxisA.set(APracticalSettings_JPrismatic.localAxisAValue)

            referenceAngle = APracticalSettings_JPrismatic.referenceAngleValue * DEGTORAD

            lowerTranslation = APracticalSettings_JPrismatic.lowerTranslationValue
            upperTranslation = APracticalSettings_JPrismatic.upperTranslationValue
            enableLimit      = APracticalSettings_JPrismatic.enableLimitValue

            motorSpeed    = APracticalSettings_JPrismatic.motorSpeedValue
            maxMotorForce = APracticalSettings_JPrismatic.maxMotorForceValue
            enableMotor   = APracticalSettings_JPrismatic.enableMotorValue
        })
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun drawJoint() {
        bStaticCircle.actor?.let { it.postDrawArray.add(AdvancedGroup.Static.Drawer { alpha ->
            jPrismatic.joint?.let { j ->
                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(bStaticCircle.body!!.position).toUI,
                    tmpPositionB.set(bStaticCircle.body!!.position).add(j.localAnchorA).toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )

                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(bDynamicCircle.body!!.position).toUI,
                    tmpPositionB.set(bDynamicCircle.body!!.position).add(j.localAnchorB).toUI,
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