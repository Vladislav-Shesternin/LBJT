package com.education.lbjt.game.box2d.bodiesGroup

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.education.lbjt.R
import com.education.lbjt.game.actors.button.AButton_Regular
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.box2d.AbstractBodyGroup
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.bodies.BRegularBtn
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.toB2

class BGRegularButtons(override val screenBox2d: AdvancedBox2dScreen): AbstractBodyGroup() {

    override val standartW = 466f

    private val parameter = FontParameter()
    private val font35    = screenBox2d.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(FontParameter.CharType.ALL).setSize(35))

    private val stringIdList = listOf(
        R.string.general_information,
        R.string.mouse_joint,
        R.string.distance_joint,
        R.string.revolute_joint,
        R.string.prismatic_joint,
        R.string.wheel_joint,
        R.string.weld_joint,
        R.string.friction_joint,
        R.string.rope_joint,
        R.string.pulley_joint,
        R.string.gear_joint,
        R.string.motor_joint,
    )

    // Body
    val bRegularBtnList = List(12) { BRegularBtn(screenBox2d, screenBox2d.game.languageUtil.getStringResource(stringIdList[it]), Label.LabelStyle(font35, GameColor.textGreen), AButton_Regular.Static.LabelType.TYPING) }

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        initB_RegularBtns()

        createB_RegularBtnList()

        createJ_DistanceForAllRegularBtns()
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_RegularBtns() {
        bRegularBtnList.onEach { it.apply {
            id = BodyId.Tutorials.DYNAMIC
            collisionList.addAll(arrayOf(BodyId.Tutorials.KINEMATIC, BodyId.Tutorials.DYNAMIC))

            bodyDef.fixedRotation = true
        } }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_RegularBtnList() {
        var ny = 0f
        bRegularBtnList.reversed().onEach { bBtn ->
            createBody(bBtn,0f,ny,466f,169f)
            ny += 169f + 80f
        }
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_DistanceLeftRight(
        _bodyA: AbstractBody,
        _bodyB: AbstractBody,
    ) {
        repeat(2) {
            createJoint(AbstractJoint(screenBox2d), DistanceJointDef().apply {
                bodyA = _bodyA.body
                bodyB = _bodyB.body

                collideConnected = true

                val resultAnchorA = if (it == 0) Vector2(62f, 164f) else Vector2(401f, 164f)
                val resultAnchorB = if (it == 0) Vector2(401f, 55f) else Vector2(62f, 55f)

                localAnchorA.set(resultAnchorA.subCenter(bodyA))
                localAnchorB.set(resultAnchorB.subCenter(bodyB))

                length       = 369.5f.toStandartBG.toB2
                frequencyHz  = 9f
                dampingRatio = 2f
            })
        }
        // center
        createJoint(AbstractJoint(screenBox2d), DistanceJointDef().apply {
            bodyA = _bodyA.body
            bodyB = _bodyB.body

            collideConnected = true

            localAnchorA.set(Vector2(232f, 164f).subCenter(bodyA))
            localAnchorB.set(Vector2(232f, 55f).subCenter(bodyB))

            length       = 109f.toStandartBG.toB2
            frequencyHz  = 9f
            dampingRatio = 2f
        })
    }

    private fun createJ_DistanceForAllRegularBtns() {
        val reversedRegularBtnList = bRegularBtnList.reversed()

        reversedRegularBtnList.onEachIndexed { index, bBtn ->
            if (index < bRegularBtnList.lastIndex) {
                createJ_DistanceLeftRight(
                    _bodyA = bBtn,
                    _bodyB = reversedRegularBtnList[index+1],
                )

                val leftAnchorA  = Vector2(62f, 164f)
                val leftAnchorB  = Vector2(62f, 5f)
                val rightAnchorA = Vector2(401f, 164f)
                val rightAnchorB = Vector2(401f, 5f)
                bBtn.actor?.preDrawArray?.add(AdvancedGroup.Static.Drawer { alpha ->
                    drawJoint(alpha, bBtn, reversedRegularBtnList[index+1], leftAnchorA, leftAnchorB)
                    drawJoint(alpha, bBtn, reversedRegularBtnList[index+1], rightAnchorA, rightAnchorB)
                })
            }
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

}