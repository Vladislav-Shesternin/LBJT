package com.education.lbjt.game.box2d.bodiesGroup

import com.badlogic.gdx.physics.box2d.joints.MotorJoint
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef
import com.education.lbjt.game.box2d.AbstractBodyGroup
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.BodyId.Menu.BUTTON
import com.education.lbjt.game.box2d.BodyId.Menu.STATIC
import com.education.lbjt.game.box2d.bodies.standart.BStatic
import com.education.lbjt.game.box2d.bodies.BYanYinTheme
import com.education.lbjt.game.utils.actor.disable
import com.education.lbjt.game.utils.actor.enable
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.region
import com.education.lbjt.game.utils.runGDX
import com.education.lbjt.game.utils.theme.ThemeUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BGYanYinTheme(override val screenBox2d: AdvancedBox2dScreen): AbstractBodyGroup() {

    override val standartW = 131f

    // Body
    private val bStaticCenter = BStatic(screenBox2d)
    private val bYanYinTheme  = BYanYinTheme(screenBox2d)

    // Joint
    private val jMotor = AbstractJoint<MotorJoint, MotorJointDef>(screenBox2d)

    // Field
    private val themeUtil = screenBox2d.game.themeUtil
    private val soundUtil = screenBox2d.game.soundUtil


    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        initB_YanYinTheme()

        createB_StaticCenter()
        createB_YanYinTheme()

        createJ_Motor()
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_YanYinTheme() {
        bYanYinTheme.apply {
            id  = BUTTON
            collisionList.addAll(arrayOf(STATIC, BUTTON, BodyId.BORDERS))
        }

        bYanYinTheme.getActor()?.apply {
            when(themeUtil.type) {
                ThemeUtil.Type.WHITE -> uncheck(false)
                ThemeUtil.Type.BLACK -> check(false)
            }

            setOnCheckListener { isCheck ->
                disable()
                if (isCheck) themeUtil.update(ThemeUtil.Type.BLACK) else themeUtil.update(ThemeUtil.Type.WHITE)

                screenBox2d.setUIBackground(themeUtil.assets.BACKGROUND.region)
                screenBox2d.game.activity.setNavigationBarColor(themeUtil.navBarColorId)
                screenBox2d.game.backgroundColor = themeUtil.backgroundColor

                coroutine?.launch {
                    delay(200)
                    soundUtil.apply { play(ELECTRICITY) }
                    delay(1000)
                    runGDX { enable() }
                }
            }
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_StaticCenter() {
        createBody(bStaticCenter, 64f, 64f, 2f, 2f)
    }

    private fun createB_YanYinTheme() {
        createBody(bYanYinTheme, 0f, 0f, 131f, 131f)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Motor() {
        createJoint(jMotor, MotorJointDef().apply {
            bodyA = bStaticCenter.body
            bodyB = bYanYinTheme.body

            maxForce  = 200 * bodyB.mass
            maxTorque = 1000f
            correctionFactor = 0.7f
        })
    }

}