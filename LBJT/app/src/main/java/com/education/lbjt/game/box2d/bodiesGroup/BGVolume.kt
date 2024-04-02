package com.education.lbjt.game.box2d.bodiesGroup

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.MotorJoint
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.education.lbjt.game.actors.label.AutoLanguageSpinningLabel
import com.education.lbjt.game.actors.volume.AVolume
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.box2d.AbstractBodyGroup
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.BodyId.Settings.LANGUAGE
import com.education.lbjt.game.box2d.BodyId.Settings.START
import com.education.lbjt.game.box2d.BodyId.Settings.VOLUME
import com.education.lbjt.game.box2d.bodies.standart.BStatic
import com.education.lbjt.game.box2d.bodies.BVolume
import com.education.lbjt.game.box2d.bodies.BVolumeRod
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.JOINT_WIDTH
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedStage
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.font.FontParameter.CharType
import com.education.lbjt.game.utils.runGDX
import com.education.lbjt.game.utils.toB2
import com.education.lbjt.game.utils.toUI
import com.education.lbjt.util.Once
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class BGVolume(
    override val screenBox2d: AdvancedBox2dScreen,
    private val nameResId: Int,
    private val startVolumePercent: Int,
): AbstractBodyGroup() {

    companion object {
        private const val MIN_LOUDER         = 39.80f
        private const val MAX_LOUDER         = 26.30f
        private const val ONE_PERCENT_LOUDER = (MIN_LOUDER - MAX_LOUDER) / 100f
    }

    override val standartW = 314f

    private val parameter = FontParameter()

    // Actor
    private val aNameLbl = AutoLanguageSpinningLabel(screenBox2d, nameResId, Label.LabelStyle(screenBox2d.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.LATIN_CYRILLIC).setSize(50)), GameColor.textGreen))

    // Body
    private val bVolumeQuiet     = BVolume(screenBox2d, AVolume.Static.Type.QUIET)
    private val bVolumeLouder    = BVolume(screenBox2d, AVolume.Static.Type.LOUDER)
    private val bVolumeRodQuiet  = BVolumeRod(screenBox2d, BVolumeRod.Static.Type.QUIET)
    private val bVolumeRodLouder = BVolumeRod(screenBox2d, BVolumeRod.Static.Type.LOUDER)
    private val bStaticStart     = BStatic(screenBox2d)

    // Joint
    private val jPulley     = AbstractJoint<PulleyJoint, PulleyJointDef>(screenBox2d)
    private val jMotorStart = AbstractJoint<MotorJoint, MotorJointDef>(screenBox2d)

    // Field
    private val drawer     = screenBox2d.drawerUtil.drawer
    private val tmpVector2 = Vector2()

    private val percentLouderFlow by lazy { MutableStateFlow(startVolumePercent) }
    val percentVolumeFlow = MutableSharedFlow<Int>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)


    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        screenBox2d.stageUI.apply {
            addNameLbl()
        }

        initB_Volume()
        initB_VolumeRod()
        initB_StaticStart()

        createB_Volume()
        createB_VolumeRod()
        createB_StaticStart()

        createJ_Pulley()
        asyncCollectPercentLouderFlow()
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Volume() {
        arrayOf(bVolumeQuiet, bVolumeLouder).onEach { it.apply {
            id  = VOLUME
            collisionList.add(VOLUME)
        } }
    }

    private fun initB_VolumeRod() {
        arrayOf(bVolumeRodQuiet, bVolumeRodLouder).onEach { it.apply {
            id  = VOLUME
            collisionList.addAll(arrayOf(VOLUME, START, LANGUAGE, BodyId.BORDERS))

            bodyDef.linearDamping  = 1.3f
            bodyDef.angularDamping = 10f
        } }
    }

    private fun initB_StaticStart() {
        bStaticStart.apply {
            id  = START
            collisionList.add(VOLUME)
            fixtureDef.isSensor = true
        }
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addNameLbl() {
        addActor(aNameLbl)
        aNameLbl.setBoundsStandartBG(0f, 565f, 314f, 65f)
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Volume() {
        createBody(bVolumeQuiet, 0f, 440f, 149f, 102f)
        createBody(bVolumeLouder, 165f, 440f, 149f, 102f)
    }

    private fun createB_VolumeRod() {
        createBody(bVolumeRodQuiet, 51f, 390f, 47f, 47f)
        createBody(bVolumeRodLouder, 216f, 390f, 47f, 47f)
    }

    private fun createB_StaticStart() {
        createBody(bStaticStart, 238f, 353f, 3f, 3f)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Pulley() {
        createJoint(jPulley, PulleyJointDef().apply {
            bodyA = bVolumeRodQuiet.body
            bodyB = bVolumeRodLouder.body

            groundAnchorA.set(position.cpy().add(Vector2(74f, 440f).toStandartBG).toB2)
            groundAnchorB.set(position.cpy().add(Vector2(239f, 440f).toStandartBG).toB2)
            localAnchorA.set(Vector2(23f, 23f).subCenter(bodyA))
            localAnchorB.set(Vector2(23f, 23f).subCenter(bodyB))
            lengthA = 209f.toStandartBG.toB2
            lengthB = 209f.toStandartBG.toB2

            bVolumeRodQuiet.actor?.preDrawArray?.add(AdvancedGroup.Static.Drawer { alpha -> drawer.line(tmpVector2.set(groundAnchorA).toUI, bodyA.position.add(localAnchorA).toUI, colorJoint.apply { a = alpha }, JOINT_WIDTH) })
            bVolumeRodLouder.actor?.preDrawArray?.add(AdvancedGroup.Static.Drawer { alpha -> drawer.line(tmpVector2.set(groundAnchorB).toUI, bodyB.position.add(localAnchorB).toUI, colorJoint.apply { a = alpha }, JOINT_WIDTH) })

        })
    }

    private fun createJ_MotorStart() {
        createJoint(jMotorStart, MotorJointDef().apply {
            bodyA = bStaticStart.body
            bodyB = bVolumeRodLouder.body
            collideConnected = true

            maxForce  = 500 * bodyB.mass
            maxTorque = 1000f
            correctionFactor = 0.1f
        })
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun asyncCollectPercentLouderFlow() {
        coroutine?.launch(Dispatchers.Default) {
            percentLouderFlow.collectIndexed { index, percent ->
                if (index == 0) {
                    runGDX { bStaticStart.body?.let { it.setTransform(it.position.x, MIN_LOUDER-(ONE_PERCENT_LOUDER*percent), 0f) } }
                    launch { createJ_MotorStart() }

                    runGDX { bVolumeRodLouder.apply {
                        val onceDestroy = Once()
                        beginContactBlockArray.add(AbstractBody.ContactBlock { if (it.id == START) onceDestroy.once { coroutine?.launch {
                            delay(700)
                            runGDX { bStaticStart.destroy() }
                        } } })
                        renderBlockArray.add(AbstractBody.RenderBlock { (body?.position?.y ?: 0f).also { _y ->
                            percentLouderFlow.value = when {
                                _y >= MIN_LOUDER -> 0
                                _y <= MAX_LOUDER -> 100
                                else -> ((MIN_LOUDER - _y) / ONE_PERCENT_LOUDER).roundToInt()
                            }
                        } })
                    } }
                }
                runGDX {
                    bVolumeLouder.getActor()?.updatePercent(percent)
                    bVolumeQuiet.getActor()?.updatePercent(100 - percent)

                    if (bStaticStart.body == null) percentVolumeFlow.tryEmit(percent)
                }
            }
        }
    }

}