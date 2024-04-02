package com.education.lbjt.game.box2d.bodiesGroup.practical

import com.badlogic.gdx.physics.box2d.BodyDef
import com.education.lbjt.game.actors.practical_settings.APracticalSettings_JMouse
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.bodies.`object`.BCObject
import com.education.lbjt.game.utils.TIME_ANIM_ALPHA_PRACTICAL_BODY
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.advanced.AdvancedMouseScreen
import com.education.lbjt.game.utils.toB2
import java.util.concurrent.atomic.AtomicBoolean

class BGPractical_JMouse(private val screenMouse: AdvancedMouseScreen): AbstractBGPractical(screenMouse) {

    override val title              = "Mouse Joint"
    override val aPracticalSettings = APracticalSettings_JMouse(screenBox2d)

    // Body
    private val bDynamicCircle = BCObject(screenBox2d, BodyDef.BodyType.DynamicBody)

    // Field
    private val isFirstMove = AtomicBoolean(true)


    override fun createBodyBlock() {
        initB_DynamicCircle()
        createB_DynamicCircle()
    }

    override fun hideAndToStartBody(endBlock: () -> Unit) {
        bDynamicCircle.apply {
            getActor()?.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY) {
                setNoneId()
                body?.also { b ->
                    b.setLinearVelocity(0f, 0f)
                    b.isAwake = false
                    b.gravityScale = 0f
                    b.setTransform(this@BGPractical_JMouse.position.cpy().add(350f, 627f).toB2, 0f)
                }
                isFirstMove.set(true)
            }
        }

        endBlock()
    }

    override fun showAndUpdateBody() {
        screenMouse.updateMouseJoint(
            APracticalSettings_JMouse.maxForceValue,
            APracticalSettings_JMouse.frequencyHzValue,
            APracticalSettings_JMouse.dampingRatioValue,
        )
        bDynamicCircle.apply {
            getActor()?.animShow(TIME_ANIM_ALPHA_PRACTICAL_BODY)
            setOriginalId()
        }
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_DynamicCircle() {
        arrayOf(bDynamicCircle).onEach { it.apply {
            id = BodyId.Practical.DYNAMIC
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.Practical.DYNAMIC))

            bodyDef.gravityScale = 0f
        } }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_DynamicCircle() {
        createBody(bDynamicCircle, 230f, 508f, 239f, 239f)

        bDynamicCircle.body?.let { bCircle ->
            bDynamicCircle.renderBlockArray.add(AbstractBody.RenderBlock {
                if (isFirstMove.get() && bCircle.linearVelocity.isZero.not()) {
                    isFirstMove.set(false)
                    bCircle.gravityScale = 1f
                }
            })
        }

    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------



}