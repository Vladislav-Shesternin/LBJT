package com.education.lbjt.game.box2d.bodiesGroup.practical

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.education.lbjt.game.actors.label.ALabel
import com.education.lbjt.game.actors.practical_settings.APracticalSettings_JRevolute
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.bodies.`object`.BHObject
import com.education.lbjt.game.box2d.bodies.`object`.BVObject
import com.education.lbjt.game.utils.DEGTORAD
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.JOINT_WIDTH
import com.education.lbjt.game.utils.RADTODEG
import com.education.lbjt.game.utils.TIME_ANIM_ALPHA_PRACTICAL_BODY
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedStage
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.toUI

class BGPractical_JRevolute(_screenBox2d: AdvancedBox2dScreen): AbstractBGPractical(_screenBox2d) {

    override val title              = "Revolute Joint"
    override val aPracticalSettings = APracticalSettings_JRevolute(screenBox2d)

    private val parameter            = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "jointAngle=°.-")
    private val fontInter_Regular_35 = screenBox2d.fontGeneratorInter_Regular.generateFont(parameter.setSize(35))

    // Actor
    private val aJointAngleLbl = ALabel(screenBox2d,"jointAngle = 0°", Label.LabelStyle(fontInter_Regular_35, GameColor.textGreen))

    // Body
    private val bStaticVertical    = BVObject(screenBox2d, BodyDef.BodyType.StaticBody)
    private val bDynamicHorizontal = BHObject(screenBox2d, BodyDef.BodyType.DynamicBody)

    // Joint
    private val jRevolute = AbstractJoint<RevoluteJoint, RevoluteJointDef>(screenBox2d)

    // Field
    private val tmpPositionA = Vector2()
    private val tmpPositionB = Vector2()
    private val tmpPositionJ = Vector2()

    override fun createBodyBlock() {
        initB_Static()
        initB_Dynamic()

        createB_Static()
        createB_Dynamic()

        createJ_Revolute()

        drawJoint()

        screenBox2d.stageUI.apply {
            addJointAngleLbl()
        }
    }

    override fun hideAndToStartBody(endBlock: () -> Unit) {
        setNoneId()
        aJointAngleLbl.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY)
        bStaticVertical.actor?.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY)
        bDynamicHorizontal.apply {
            actor?.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY) {
                body?.also { b ->
                    b.setLinearVelocity(0f, 0f)
                    b.isAwake = false
                    b.setTransform(b.position, 0f)
                }
            }
        }
        endBlock()
    }

    override fun showAndUpdateBody() {
        jRevolute.destroy()
        createJ_Revolute()

        setOriginalId()
        aJointAngleLbl.animShow(TIME_ANIM_ALPHA_PRACTICAL_BODY)
        arrayOf(bStaticVertical, bDynamicHorizontal).onEach { it.actor?.animShow(TIME_ANIM_ALPHA_PRACTICAL_BODY) }
        bDynamicHorizontal.body?.isAwake = true
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Static() {
        bStaticVertical.apply {
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
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addJointAngleLbl() {
        addActor(aJointAngleLbl)
        aJointAngleLbl.setBoundsStandartBG(227f, 1037f, 246f, 42f)
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        createBody(bStaticVertical, 315f, 543f, 70f, 170f)
    }

    private fun createB_Dynamic() {
        createBody(bDynamicHorizontal, 265f, 593f, 170f, 70f)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Revolute() {
        createJoint(jRevolute, RevoluteJointDef().apply {
            bodyA = bStaticVertical.body
            bodyB = bDynamicHorizontal.body
            collideConnected = false

            localAnchorA.set(APracticalSettings_JRevolute.localAnchorAValue.cpy().sub(bStaticVertical.center))
            localAnchorB.set(APracticalSettings_JRevolute.localAnchorBValue.cpy().sub(bDynamicHorizontal.center))

            referenceAngle = APracticalSettings_JRevolute.referenceAngleValue * DEGTORAD
            lowerAngle     = APracticalSettings_JRevolute.lowerAngleValue * DEGTORAD
            upperAngle     = APracticalSettings_JRevolute.upperAngleValue * DEGTORAD
            enableLimit    = APracticalSettings_JRevolute.enableLimitValue
            motorSpeed     = APracticalSettings_JRevolute.motorSpeedValue * DEGTORAD
            maxMotorTorque = APracticalSettings_JRevolute.maxMotorTorqueValue
            enableMotor    = APracticalSettings_JRevolute.enableMotorValue
        })
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun drawJoint() {
        bDynamicHorizontal.actor?.let { it.postDrawArray.add(AdvancedGroup.Static.Drawer { alpha ->
            jRevolute.joint?.let { j ->
                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(bStaticVertical.body!!.position).toUI,
                    tmpPositionB.set(bStaticVertical.body!!.position).add(j.localAnchorA).toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )

                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(bDynamicHorizontal.body!!.position).toUI,
                    tmpPositionB.set(bDynamicHorizontal.body!!.position).add(tmpPositionJ.set(j.anchorB).sub(bDynamicHorizontal.body!!.position)).toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )

                aJointAngleLbl.label.setText("jointAngle = ${String.format("%.2f", j.jointAngle * RADTODEG).replace(',','.')}°")
            }
        }) }
    }

}