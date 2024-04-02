package com.education.lbjt.game.box2d.bodiesGroup

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.joints.GearJoint
import com.badlogic.gdx.physics.box2d.joints.GearJointDef
import com.badlogic.gdx.physics.box2d.joints.MotorJoint
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef
import com.education.lbjt.game.box2d.AbstractBodyGroup
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.bodies.BLiftGear
import com.education.lbjt.game.box2d.bodies.BLiftPlatform
import com.education.lbjt.game.box2d.bodies.`object`.BVObject
import com.education.lbjt.game.box2d.bodies.standart.BStatic
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.runGDX
import com.education.lbjt.game.utils.toB2
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BGLift(override val screenBox2d: AdvancedBox2dScreen): AbstractBodyGroup() {

    override val standartW = 654f

    // Body
    private val bStatic       = BStatic(screenBox2d)
    private val bLiftPlatform = BLiftPlatform(screenBox2d)
    private val bLiftGear     = BLiftGear(screenBox2d)
    private val bLever        = BVObject(screenBox2d, BodyDef.BodyType.DynamicBody)

    // BodyGroup
    val bgRegularBtns = BGRegularButtons(screenBox2d)

    // Joint
    private val jPulley            = AbstractJoint<PulleyJoint, PulleyJointDef>(screenBox2d)
    private val jPrismaticPlatform = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)
    private val jPrismaticLever    = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)
    private val jRevolute          = AbstractJoint<RevoluteJoint, RevoluteJointDef>(screenBox2d)
    private val jGear              = AbstractJoint<GearJoint, GearJointDef>(screenBox2d)
    private val jPrismaticLastBtn  = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)
    private val jMotor             = AbstractJoint<MotorJoint, MotorJointDef>(screenBox2d)

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        screenBox2d.game.soundUtil.isPause = true

        initB_Kinematic()
        initB_Dynamic()

        createB_Static()
        createB_LiftDetail()

        createJ_Pulley()
        createJ_Prismatic()
        createJ_Revolute()
        createJ_Gear()

        createBG_RegularBtns()

        createJ_PrismaticLastBtn()
        createJ_Motor()

        runGDX {
            coroutine?.launch {
                delay(300)
                screenBox2d.game.soundUtil.isPause = false
            }
        }
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Kinematic() {
        arrayOf(bLiftPlatform, bLiftGear).onEach { it.apply {
            id = BodyId.Tutorials.KINEMATIC
            collisionList.add(BodyId.Tutorials.DYNAMIC)

            fixtureDef.density = 100f
        } }
    }

    private fun initB_Dynamic() {
        arrayOf(bLever).onEach { it.apply {
            id = BodyId.Tutorials.DYNAMIC
            collisionList.addAll(arrayOf(BodyId.Tutorials.KINEMATIC, BodyId.Tutorials.DYNAMIC))
        } }

        bLever.apply {
            bodyDef.fixedRotation = true
            fixtureDef.density    = 700f
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        createBody(bStatic,0f,0f,10f,10f)
    }

    private fun createB_LiftDetail() {
        createBody(bLiftPlatform,0f,0f,525f,97f)
        createBody(bLiftGear,528f,1153f,92f,92f)
        createBody(bLever,588f,993f,66f,160f)
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createBG_RegularBtns() {
        createBodyGroup(bgRegularBtns, 30f,35f, 466f, 2908f)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Pulley() {
        createJoint(jPulley, PulleyJointDef().apply {
            bodyA = bLiftPlatform.body
            bodyB = bLever.body

            localAnchorA.set(Vector2(263f, 0f).subCenter(bodyA))
            localAnchorB.set(Vector2(33f, 160f).subCenter(bodyB))

            groundAnchorA.set(position.cpy().add(Vector2(263f, 35f).toStandartBG).toB2)
            groundAnchorB.set(position.cpy().add(Vector2(621f, 1153f).toStandartBG).toB2)

            lengthA = 1872f.toStandartBG.toB2
            lengthB = 0f

            ratio = 1.885196f
        })

        val leftAnchorA  = Vector2(525f, 1199f)
        val leftAnchorB  = Vector2(525f, 0f)
        val rightAnchorA = Vector2(621f, 1199f)
        val rightAnchorB = Vector2(33f, 160f)

        bStatic.actor?.postDrawArray?.add(AdvancedGroup.Static.Drawer { alpha ->
            drawJoint(alpha, bStatic, bLiftPlatform, leftAnchorA, leftAnchorB)
            drawJoint(alpha, bStatic, bLever, rightAnchorA, rightAnchorB)
        })
    }

    private fun createJ_Prismatic() {
        createJoint(jPrismaticPlatform, PrismaticJointDef().apply {
            bodyA = bStatic.body
            bodyB = bLiftPlatform.body

            localAnchorA.set(Vector2(263f, 0f).subCenter(bodyA))
            localAnchorB.set(Vector2(263f, 0f).subCenter(bodyB))

            localAxisA.set(0f, 1f)

            enableLimit      = true
            lowerTranslation = -1872f.toStandartBG.toB2
            upperTranslation = 17.5f.toStandartBG.toB2
        })
        createJoint(jPrismaticLever, PrismaticJointDef().apply {
            bodyA = bStatic.body
            bodyB = bLever.body

            localAnchorA.set(Vector2(621f, 0f).subCenter(bodyA))
            localAnchorB.set(Vector2(33f, 0f).subCenter(bodyB))

            localAxisA.set(0f, 1f)
        })
    }

    private fun createJ_Revolute() {
        createJoint(jRevolute, RevoluteJointDef().apply {
            bodyA = bStatic.body
            bodyB = bLiftGear.body

            localAnchorA.set(Vector2(574f, 1199f).subCenter(bodyA))
        })
    }

    private fun createJ_Gear() {
        createJoint(jGear, GearJointDef().apply {
            bodyA  = bLiftGear.body
            bodyB  = bLever.body

            joint1 = jRevolute.joint
            joint2 = jPrismaticLever.joint

            collideConnected = true
            ratio = -1f
        })
    }

    private fun createJ_PrismaticLastBtn() {
        createJoint(jPrismaticLastBtn, PrismaticJointDef().apply {
            bodyA  = bLiftPlatform.body
            bodyB  = bgRegularBtns.bRegularBtnList.last().body

            collideConnected = false

            localAnchorA.set(Vector2(263f, 35f).subCenter(bodyA))
            localAnchorB.set(Vector2(233f, 0f).subCenter(bodyB))

            enableLimit      = true
            upperTranslation = 20f.toStandartBG.toB2
            lowerTranslation = 0f

            localAxisA.set(0f, 1f)
        })
    }

    private fun createJ_Motor() {
        createJoint(jMotor, MotorJointDef().apply {
            bodyA  = bStatic.body
            bodyB  = bLever.body

            linearOffset.set(Vector2(621f, 1017f).subCenter(bodyA))
            maxForce = 1000 * bodyB.mass
        })
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun destroyMotorJoint() {
        jMotor.destroy()
    }

}