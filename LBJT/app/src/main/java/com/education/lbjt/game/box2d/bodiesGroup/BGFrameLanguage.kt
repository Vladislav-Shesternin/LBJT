package com.education.lbjt.game.box2d.bodiesGroup

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.WeldJoint
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef
import com.education.lbjt.game.box2d.AbstractBodyGroup
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.BodyId.Settings.LANGUAGE
import com.education.lbjt.game.box2d.BodyId.Settings.VOLUME
import com.education.lbjt.game.box2d.bodies.BFrameLanguage
import com.education.lbjt.game.box2d.bodies.standart.BStatic
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.toB2

class BGFrameLanguage(override val screenBox2d: AdvancedBox2dScreen): AbstractBodyGroup() {

    override val standartW = 314f

    // Body
    private val bStatic        = BStatic(screenBox2d)
    private val bFrameLanguage = BFrameLanguage(screenBox2d)

    // Joint
    private val jWeld = AbstractJoint<WeldJoint, WeldJointDef>(screenBox2d)

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        initB_FrameLanguage()

        createB_Static()
        createB_FrameLanguage()

        createJ_Weld()
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_FrameLanguage() {
        bFrameLanguage.apply {
            id  = LANGUAGE
            collisionList.addAll(arrayOf(LANGUAGE, VOLUME, BodyId.BORDERS))
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        createBody(bStatic, 152f, 46f, 10f, 10f)
    }

    private fun createB_FrameLanguage() {
        createBody(bFrameLanguage, 0f, 0f, 314f, 102f)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Weld() {
        createJoint(jWeld, WeldJointDef().apply {
            bodyA = bStatic.body
            bodyB = bFrameLanguage.body

            frequencyHz  = 1f
            dampingRatio = 0.1f
        })
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun impulseFrameLanguage() {
        bFrameLanguage.body?.applyLinearImpulse(
            Vector2(0f, 300f),
            position.cpy().add(Vector2(55f, 45f).toStandartBG).toB2,
            true
        )
    }

}