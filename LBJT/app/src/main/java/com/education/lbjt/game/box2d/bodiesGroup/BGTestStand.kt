package com.education.lbjt.game.box2d.bodiesGroup

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef
import com.badlogic.gdx.physics.box2d.joints.WeldJoint
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.education.lbjt.R
import com.education.lbjt.game.actors.label.ALabel
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.box2d.AbstractBodyGroup
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.bodies.BRegularBtn
import com.education.lbjt.game.box2d.bodies.BTestStandBorders
import com.education.lbjt.game.box2d.bodies.`object`.BCObject
import com.education.lbjt.game.box2d.bodies.`object`.BHObject
import com.education.lbjt.game.box2d.bodies.`object`.BVObject
import com.education.lbjt.game.utils.DEGTORAD
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedStage
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.runGDX
import com.education.lbjt.game.utils.toB2
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BGTestStand(override val screenBox2d: AdvancedBox2dScreen): AbstractBodyGroup() {

    override val standartW = 700f

    private val textTestStand = screenBox2d.game.languageUtil.getStringResource(R.string.test_stand)
    private val textNext      = screenBox2d.game.languageUtil.getStringResource(R.string.next)

    private val parameter = FontParameter()
    private val font25    = screenBox2d.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(textTestStand).setSize(25))
    private val font50    = screenBox2d.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(textNext).setSize(50))

    // Actor
    private val aTitleLbl = ALabel(screenBox2d, textTestStand, Label.LabelStyle(font25, GameColor.textGreen))

    // Body
    private val bTestStandBorders   = BTestStandBorders(screenBox2d)
    private val bHObjectStatic      = BHObject(screenBox2d, BodyDef.BodyType.StaticBody)
    private val bCObjectDynamic     = BCObject(screenBox2d, BodyDef.BodyType.DynamicBody)
    private val bVObjectDynamic     = BVObject(screenBox2d, BodyDef.BodyType.DynamicBody)
    private val bVObjectKinematic   = BVObject(screenBox2d, BodyDef.BodyType.KinematicBody)
    private val bHObjectKinematic   = BHObject(screenBox2d, BodyDef.BodyType.KinematicBody)
    private val bCObjectDynamicMini = BCObject(screenBox2d, BodyDef.BodyType.DynamicBody)
    val bRegularBtnNext             = BRegularBtn(screenBox2d, textNext, Label.LabelStyle(font50, GameColor.textGreen))

    // Joint
    private val jRevolutDynamic  = AbstractJoint<RevoluteJoint, RevoluteJointDef>(screenBox2d)
    private val jDistanceDynamic = AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d)
    private val jWeld            = AbstractJoint<WeldJoint, WeldJointDef>(screenBox2d)



    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        screenBox2d.game.soundUtil.isPause = true

        screenBox2d.stageUI.apply {
            addTitleLbl()
        }

        initB_Static()
        initB_Dynamic()

        createB_Static()
        createB_Kinematic()
        createB_Dynamic()
        createB_RegularBtnNext()

        createJ_RevoluteDynamic()
        createJ_DistanceDynamic()
        createJ_Weld()

        rotateKinematic()

        coroutine?.launch {
            delay(1000)
            runGDX { moveKinematic() }
        }

        coroutine?.launch {
            delay(300)
            screenBox2d.game.soundUtil.isPause = false
        }
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Static() {
        arrayOf(bTestStandBorders, bHObjectStatic, bVObjectKinematic, bHObjectKinematic).onEach { it.apply {
            id = BodyId.TestStand.STATIC
            collisionList.add(BodyId.TestStand.DYNAMIC)
        } }
    }

    private fun initB_Dynamic() {
        arrayOf(bCObjectDynamic, bVObjectDynamic, bCObjectDynamicMini, bRegularBtnNext).onEach { it.apply {
            id = BodyId.TestStand.DYNAMIC
            collisionList.addAll(arrayOf(BodyId.TestStand.STATIC, BodyId.TestStand.DYNAMIC))
        } }

        bCObjectDynamicMini.fixtureDef.density = 20f
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addTitleLbl() {
        addActor(aTitleLbl)
        aTitleLbl.setBoundsStandartBG(34f, 388f, 661f, 38f)
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        createBody(bTestStandBorders,0f,0f,700f,431f)
        createBody(bHObjectStatic,307f,5f,85f,35f)
    }

    private fun createB_Kinematic() {
        createBody(bVObjectKinematic,565f,172f,35f,85f)
        createBody(bHObjectKinematic,307f,321f,85f,35f)
    }

    private fun createB_Dynamic() {
        createBody(bCObjectDynamic,307f,39f,85f,85f)
        createBody(bVObjectDynamic,100f,172f,35f,85f)
        createBody(bCObjectDynamicMini,331f,221f,37f,37f)
    }

    private fun createB_RegularBtnNext() {
        createBody(bRegularBtnNext,205f,455f,290f,105f)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_RevoluteDynamic() {
        createJoint(jRevolutDynamic, RevoluteJointDef().apply {
            bodyA = bTestStandBorders.body
            bodyB = bVObjectDynamic.body

            localAnchorA.set(Vector2(118f, 215f).subCenter(bodyA))
            localAnchorB.set(Vector2(17.5f, 42.5f).subCenter(bodyB))

            enableMotor    = true
            motorSpeed     = -180 * DEGTORAD
            maxMotorTorque = 100f
        })
    }

    private fun createJ_DistanceDynamic() {
        createJoint(jDistanceDynamic, DistanceJointDef().apply {
            bodyA = bHObjectKinematic.body
            bodyB = bCObjectDynamicMini.body

            localAnchorA.set(Vector2(42.5f, 17.5f).subCenter(bodyA))

            length       = 100f.toB2
            frequencyHz  = 1f
            dampingRatio = 0.1f
        })

        bHObjectKinematic.actor?.preDrawArray?.add(AdvancedGroup.Static.Drawer { jDistanceDynamic.drawJoint(it) })
    }

    private fun createJ_Weld() {
        createJoint(jWeld, WeldJointDef().apply {
            bodyA = bTestStandBorders.body
            bodyB = bRegularBtnNext.body

            localAnchorA.set(Vector2(349f, 430f).subCenter(bodyA))
            localAnchorB.set(Vector2(144f, -24f).subCenter(bodyB))

            collideConnected = true

            frequencyHz  = 1.5f
            dampingRatio = 0.1f
        })

        bTestStandBorders.actor?.postDrawArray?.add(AdvancedGroup.Static.Drawer {
            drawJoint(it, bTestStandBorders, bRegularBtnNext, Vector2(349f, 430f), Vector2(144f, 50f))
        })

    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun rotateKinematic() {
        bVObjectKinematic.body?.angularVelocity = 180 * DEGTORAD
    }

    private fun moveKinematic() {
        bHObjectKinematic.apply {

            val leftX  = 75f.toB2 + center.x
            val rightX = 540f.toB2 + center.x

            body?.setLinearVelocity(5f, 0f)

            renderBlockArray.add(AbstractBody.RenderBlock { time ->
                body?.let { b ->
                    when {
                        b.position.x <= leftX  -> {
                            body?.setLinearVelocity(0f, 0f)

                            coroutine?.launch {
                                delay(2000)
                                runGDX { body?.setLinearVelocity(5f, 0f) }
                            }
                        }
                        b.position.x >= rightX -> {
                            body?.setLinearVelocity(0f, 0f)

                            coroutine?.launch {
                                delay(2000)
                                runGDX { body?.setLinearVelocity(-5f, 0f) }
                            }
                        }
                    }
                }
            })

        }
    }

    fun impulseB_RegularBtnNext() {
        bRegularBtnNext.body?.applyAngularImpulse(700f, true)
    }

}