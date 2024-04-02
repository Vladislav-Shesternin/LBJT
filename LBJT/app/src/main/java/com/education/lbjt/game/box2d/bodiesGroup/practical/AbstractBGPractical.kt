package com.education.lbjt.game.box2d.bodiesGroup.practical

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.MotorJoint
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.education.lbjt.game.actors.label.ALabel
import com.education.lbjt.game.actors.practical_settings.AAbstractPracticalSettings
import com.education.lbjt.game.box2d.AbstractBodyGroup
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.bodies.BHorizontalPractical
import com.education.lbjt.game.box2d.bodies.BPracticalReset
import com.education.lbjt.game.box2d.bodies.BPracticalSettings
import com.education.lbjt.game.box2d.bodies.BVerticalPractical
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedStage
import com.education.lbjt.game.utils.font.FontParameter

abstract class AbstractBGPractical(final override val screenBox2d: AdvancedBox2dScreen) : AbstractBodyGroup() {

    override val standartW = 700f

    abstract val title             : String
    abstract val aPracticalSettings: AAbstractPracticalSettings

    private val parameter              by lazy { FontParameter().setCharacters(title) }
    private val fontInter_ExtraBold_50 by lazy { screenBox2d.fontGeneratorInter_ExtraBold.generateFont(parameter.setSize(50)) }

    // Actor
    private val aTitleLbl by lazy { ALabel(screenBox2d, title, Label.LabelStyle(fontInter_ExtraBold_50, GameColor.textRed)) }

    // Body
    private val bTop   = BHorizontalPractical(screenBox2d)
    private val bDown  = BHorizontalPractical(screenBox2d)
    private val bLeft  = BVerticalPractical(screenBox2d)
    private val bRight = BVerticalPractical(screenBox2d)

    val bPracticalSettings = BPracticalSettings(screenBox2d)
    val bPracticalReset    = BPracticalReset(screenBox2d)

    // Joint
    private val jMotorPracticalSettings = AbstractJoint<MotorJoint, MotorJointDef>(screenBox2d)
    private val jMotorPracticalReset    = AbstractJoint<MotorJoint, MotorJointDef>(screenBox2d)


    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        initB_Borders()
        initB_Practical()

        createB_Horizontal()
        createB_Vertical()

        createBodyBlock()

        screenBox2d.stageUI.apply {
            addPracticalSettings()
            addTitleLbl()
        }

        createB_PracticalSettings()
        createB_PracticalReset()

        createJ_PracticalSettings()
        createJ_PracticalReset()
    }

    abstract fun createBodyBlock()

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Borders() {
        arrayOf(bTop, bDown, bLeft, bRight).onEach { it.apply {
            id = BodyId.BORDERS
            collisionList.add(BodyId.Practical.DYNAMIC)
        } }
    }

    private fun initB_Practical() {
        arrayOf(bPracticalSettings, bPracticalReset).onEach { it.apply {
            id = BodyId.Practical.DYNAMIC
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.Practical.DYNAMIC))
        } }
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addTitleLbl() {
        addActor(aTitleLbl)
        aTitleLbl.setBoundsStandartBG(10f, 1159f, 680f, 61f)
        aTitleLbl.label.setAlignment(Align.center)
    }

    private fun AdvancedStage.addPracticalSettings() {
        addActor(aPracticalSettings)
        aPracticalSettings.setBoundsStandartBG(0f, 0f, this@AbstractBGPractical.size.x, this@AbstractBGPractical.size.y)
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Horizontal() {
        createBody(bTop, 0f, 1245f, 700f, 10f)
        createBody(bDown, 0f, 0f, 700f, 10f)
    }

    private fun createB_Vertical() {
        createBody(bLeft, 0f, 10f, 10f, 1235f)
        createBody(bRight, 690f, 10f, 10f, 1235f)
    }

    private fun createB_PracticalSettings() {
        createBody(bPracticalSettings, 526f, 30f, 144f, 144f)

        bPracticalSettings.getActor()?.apply {
            settingsBlock = {
                bPracticalReset.getActor()?.apply {
                    disable()
                    animHide(TIME_ANIM_SCREEN_ALPHA)
                }
                hideAndToStartBody {
                    aPracticalSettings.showAndEnabled()
                }
            }
            doneBlock = {
                aPracticalSettings.hideAndDisabled {
                    bPracticalReset.getActor()?.apply {
                        enable()
                        animShow(TIME_ANIM_SCREEN_ALPHA)
                    }
                    showAndUpdateBody()
                }
            }
        }
    }

    private fun createB_PracticalReset() {
        createBody(bPracticalReset, 526f, 186f, 144f, 72f)

        bPracticalReset.getActor()?.apply {
            setOnClickListener {
                aPracticalSettings.reset()
                bPracticalSettings.getActor()?.runSettingsBlock()
            }
        }
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_PracticalSettings() {
        createJoint(jMotorPracticalSettings, MotorJointDef().apply {
            bodyA = bDown.body
            bodyB = bPracticalSettings.body
            collideConnected = true

            linearOffset.set(Vector2(598f, 102f).subCenter(bodyA))

            maxForce  = 500 * bodyB.mass
            maxTorque = 20_000f
            correctionFactor = 0.5f
        })
    }

    private fun createJ_PracticalReset() {
        createJoint(jMotorPracticalReset, MotorJointDef().apply {
            bodyA = bDown.body
            bodyB = bPracticalReset.body
            collideConnected = true

            linearOffset.set(Vector2(598f, 222f).subCenter(bodyA))

            maxForce  = 500 * bodyB.mass
            maxTorque = 20_000f
            correctionFactor = 0.5f
        })
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    abstract fun hideAndToStartBody(endBlock: () -> Unit)

    abstract fun showAndUpdateBody()

}