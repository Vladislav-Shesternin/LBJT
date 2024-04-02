package com.education.lbjt.game.box2d.bodiesGroup

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.education.lbjt.R
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.box2d.AbstractBodyGroup
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.BodyId.Menu.BUTTON
import com.education.lbjt.game.box2d.BodyId.Menu.STATIC
import com.education.lbjt.game.box2d.bodies.BRegularBtn
import com.education.lbjt.game.box2d.bodies.BStaticCircle
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.font.FontParameter.CharType
import com.education.lbjt.game.utils.toB2

class BGMenu(override val screenBox2d: AdvancedBox2dScreen): AbstractBodyGroup() {

    override val standartW = 466f

    private val parameter = FontParameter()

    // Body
    private val bStaticCircleLeft  = BStaticCircle(screenBox2d)
    private val bStaticCircleRight = BStaticCircle(screenBox2d)

    val bRegularBtnTutorial    = BRegularBtn(screenBox2d, screenBox2d.game.languageUtil.getStringResource(R.string.tutorials),    Label.LabelStyle(screenBox2d.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.LATIN_CYRILLIC).setSize(50)), GameColor.textGreen))
    val bRegularBtnSettings    = BRegularBtn(screenBox2d, screenBox2d.game.languageUtil.getStringResource(R.string.settings),     Label.LabelStyle(screenBox2d.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.LATIN_CYRILLIC).setSize(40)), GameColor.textGreen))
    val bRegularBtnAboutAuthor = BRegularBtn(screenBox2d, screenBox2d.game.languageUtil.getStringResource(R.string.about_author), Label.LabelStyle(screenBox2d.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.LATIN_CYRILLIC).setSize(30)), GameColor.textGreen))
    val bRegularBtnComment     = BRegularBtn(screenBox2d, screenBox2d.game.languageUtil.getStringResource(R.string.comments),     Label.LabelStyle(screenBox2d.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.LATIN_CYRILLIC).setSize(25)), GameColor.textGreen))

    // Joint
    private val jDistanceTutorialLeft   = AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d)
    private val jDistanceTutorialRight  = AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d)
    private val jDistanceSettings       = AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d)
    private val jDistanceAboutAuthor    = AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d)
    private val jDistanceComment        = AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d)

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        initB_StaticCircle()
        initB_RegularBtn()

        createB_StaticCircle()
        createB_RegularBtn()

        createJ_Joint()
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_StaticCircle() {
        arrayOf(bStaticCircleLeft, bStaticCircleRight).onEach { it.apply {
            id  = STATIC
            collisionList.add(BUTTON)
            fixtureDef.apply {
                restitution = 1f
                friction    = 0f
            }
        } }
    }

    private fun initB_RegularBtn() {
        arrayOf(bRegularBtnTutorial, bRegularBtnSettings, bRegularBtnAboutAuthor, bRegularBtnComment).onEach { it.apply {
            id = BUTTON
            bodyDef.angularDamping = 0.9f
            bodyDef.linearDamping  = 0.4f

            collisionList.addAll(arrayOf(STATIC, BUTTON, BodyId.BORDERS))
        } }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_StaticCircle() {
        createBody(bStaticCircleLeft, 7f, 899f, 113f, 113f)
        createBody(bStaticCircleRight, 346f, 899f, 113f, 113f)

    }

    private fun createB_RegularBtn() {
        createBody(bRegularBtnTutorial, 0f, 519f, 466f, 169f)
        createBody(bRegularBtnSettings, 47f, 318f, 372f, 135f)
        createBody(bRegularBtnAboutAuthor, 65f, 147f, 326f, 118f)
        createBody(bRegularBtnComment, 93f, 0f, 279f, 101f)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Joint() {
        // Tutorial
        createJointUtil(
            jDistanceTutorialLeft,
            bStaticCircleLeft, bRegularBtnTutorial,
            Vector2(56f, 2f), Vector2(64f, 167f), 200f,
        )
        createJointUtil(
            jDistanceTutorialRight,
            bStaticCircleRight, bRegularBtnTutorial,
            Vector2(56f, 2f), Vector2(403f, 167f), 200f,
        )
        // Settings
        createJointUtil(
            jDistanceSettings,
            bRegularBtnTutorial, bRegularBtnSettings,
            Vector2(233f, 20f), Vector2(186f, 132f), 89f,
        )
        // AboutAuthor
        createJointUtil(
            jDistanceAboutAuthor,
            bRegularBtnSettings, bRegularBtnAboutAuthor,
            Vector2(186f, 17f), Vector2(163f, 116f), 73f,
        )
        // Comment
        createJointUtil(
            jDistanceComment,
            bRegularBtnAboutAuthor, bRegularBtnComment,
            Vector2(163f, 14f), Vector2(140f, 99f), 63f,
        )
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun createJointUtil(
        _joint      : AbstractJoint<DistanceJoint, DistanceJointDef>,
        _bodyA      : AbstractBody,
        _bodyB      : AbstractBody,
        _anchorA    : Vector2,
        _anchorB    : Vector2,
        _length     : Float,
    ) {
        createJoint(_joint, DistanceJointDef().apply {
            bodyA = _bodyA.body
            bodyB = _bodyB.body

            localAnchorA.set(_anchorA.subCenter(bodyA))
            localAnchorB.set(_anchorB.subCenter(bodyB))
            collideConnected = true

            length       = _length.toStandartBG.toB2
            frequencyHz  = 1.7f
            dampingRatio = 0.6f
        })

        _bodyA.actor?.preDrawArray?.add(AdvancedGroup.Static.Drawer { alpha -> _joint.drawJoint(alpha) })
    }
}