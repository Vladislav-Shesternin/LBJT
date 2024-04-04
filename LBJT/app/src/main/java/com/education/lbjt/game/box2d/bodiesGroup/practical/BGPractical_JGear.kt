package com.education.lbjt.game.box2d.bodiesGroup.practical

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.joints.GearJoint
import com.badlogic.gdx.physics.box2d.joints.GearJointDef
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.education.lbjt.game.actors.image.AImage
import com.education.lbjt.game.actors.label.ALabel
import com.education.lbjt.game.actors.practical_settings.APracticalSettings_JGear
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.bodies.`object`.BCObject
import com.education.lbjt.game.box2d.bodies.`object`.BHObject
import com.education.lbjt.game.box2d.bodies.standart.BStatic
import com.education.lbjt.game.box2d.destroyAll
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.JOINT_WIDTH
import com.education.lbjt.game.utils.TIME_ANIM_ALPHA_PRACTICAL_BODY
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedStage
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.toB2
import com.education.lbjt.game.utils.toUI

class BGPractical_JGear(_screenBox2d: AdvancedBox2dScreen): AbstractBGPractical(_screenBox2d) {

    override val title              = "Gear Joint"
    override val aPracticalSettings = APracticalSettings_JGear(screenBox2d)

    private val parameter            = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val fontInter_Regular_35 = screenBox2d.fontGeneratorInter_Regular.generateFont(parameter.setSize(35))
    private val fontInter_Bold_25    = screenBox2d.fontGeneratorInter_Bold.generateFont(parameter.setSize(25))

    // Actor
    private val aRRLbl = ALabel(screenBox2d, "Revolute / Revolute", Label.LabelStyle(fontInter_Regular_35, GameColor.textGreen))
    private val aPPLbl = ALabel(screenBox2d, "Prismatic / Prismatic", Label.LabelStyle(fontInter_Regular_35, GameColor.textGreen))
    private val aRPLbl = ALabel(screenBox2d, "Revolute / Prismatic", Label.LabelStyle(fontInter_Regular_35, GameColor.textGreen))
    private val aRR_RatioImg = AImage(screenBox2d, screenBox2d.game.themeUtil.assets.RATIO_H)
    private val aPP_RatioImg = AImage(screenBox2d, screenBox2d.game.themeUtil.assets.RATIO_V)
    private val aRP_RatioImg = AImage(screenBox2d, screenBox2d.game.themeUtil.assets.RATIO_V)
    private val aRR_RatioLbl = ALabel(screenBox2d, "${APracticalSettings_JGear.ratioValue}", Label.LabelStyle(fontInter_Bold_25, GameColor.textGreen))
    private val aPP_RatioLbl = ALabel(screenBox2d, "${APracticalSettings_JGear.ratioValue}", Label.LabelStyle(fontInter_Bold_25, GameColor.textGreen))
    private val aRP_RatioLbl = ALabel(screenBox2d, "${APracticalSettings_JGear.ratioValue}", Label.LabelStyle(fontInter_Bold_25, GameColor.textGreen))

    private val aList = listOf(
        aRRLbl, aPPLbl, aRPLbl,
        aRR_RatioImg, aPP_RatioImg, aRP_RatioImg,
        aRR_RatioLbl, aPP_RatioLbl, aRP_RatioLbl,
    )
    private val aRatioLblList = listOf(
        aRR_RatioLbl, aPP_RatioLbl, aRP_RatioLbl,
    )

    // Body
    private val bRRStaticLeft         = BStatic(screenBox2d).apply { getActor()?.drawable = TextureRegionDrawable(screenBox2d.game.themeUtil.assets.C_STATIC) }
    private val bRRStaticRight        = BStatic(screenBox2d).apply { getActor()?.drawable = TextureRegionDrawable(screenBox2d.game.themeUtil.assets.C_STATIC) }
    private val bRRDynamicCircleLeft  = BCObject(screenBox2d, BodyDef.BodyType.DynamicBody)
    private val bRRDynamicCircleRight = BCObject(screenBox2d, BodyDef.BodyType.DynamicBody)

    private val bPPStaticTop            = BStatic(screenBox2d).apply { getActor()?.drawable = TextureRegionDrawable(screenBox2d.game.themeUtil.assets.C_STATIC) }
    private val bPPStaticBot            = BStatic(screenBox2d).apply { getActor()?.drawable = TextureRegionDrawable(screenBox2d.game.themeUtil.assets.C_STATIC) }
    private val bPPDynamicHorizontalTop = BHObject(screenBox2d, BodyDef.BodyType.DynamicBody)
    private val bPPDynamicHorizontalBot = BHObject(screenBox2d, BodyDef.BodyType.DynamicBody)

    private val bRPStaticTop            = BStatic(screenBox2d).apply { getActor()?.drawable = TextureRegionDrawable(screenBox2d.game.themeUtil.assets.C_STATIC) }
    private val bRPStaticBot            = BStatic(screenBox2d).apply { getActor()?.drawable = TextureRegionDrawable(screenBox2d.game.themeUtil.assets.C_STATIC) }
    private val bRPDynamicCircleTop     = BCObject(screenBox2d, BodyDef.BodyType.DynamicBody)
    private val bRPDynamicHorizontalBot = BHObject(screenBox2d, BodyDef.BodyType.DynamicBody)

    // Joint
    private val jRR_Left  = AbstractJoint<RevoluteJoint, RevoluteJointDef>(screenBox2d)
    private val jRR_Right = AbstractJoint<RevoluteJoint, RevoluteJointDef>(screenBox2d)
    private val jRR_Gear  = AbstractJoint<GearJoint, GearJointDef>(screenBox2d)

    private val jPP_Top  = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)
    private val jPP_Bot  = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)
    private val jPP_Gear = AbstractJoint<GearJoint, GearJointDef>(screenBox2d)

    private val jRP_Top  = AbstractJoint<RevoluteJoint, RevoluteJointDef>(screenBox2d)
    private val jRP_Bot  = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)
    private val jRP_Gear = AbstractJoint<GearJoint, GearJointDef>(screenBox2d)

    // Field
    private val tmpPositionA = Vector2()
    private val tmpPositionB = Vector2()
    private val startPositionList = listOf<Vector2>(
        Vector2(191f, 1129f).toB2, Vector2(510f, 1129f).toB2,
        Vector2(350f, 919f).toB2, Vector2(350f, 825f).toB2,
        Vector2(350f, 615f).toB2, Vector2(350f, 471f).toB2,
    )

    override fun createBodyBlock() {
        screenBox2d.stageUI.addJointTypesLbls()

        initB_Dynamic()

        createB_Dynamic()

        createJ_RR()
        createJ_PP()
        createJ_RP()

        drawJoint()

    }

    override fun hideAndToStartBody(endBlock: () -> Unit) {
        listOf(
            jRR_Left, jRR_Right, jRR_Gear,
            jPP_Top, jPP_Bot, jPP_Gear,
            jRP_Top, jRP_Bot, jRP_Gear,
        ).destroyAll()

        setNoneId()
        aList.onEach { it.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY) }

        listOf(
            bRRStaticLeft, bRRStaticRight,
            bPPStaticTop, bPPStaticBot,
            bRPStaticTop, bRPStaticBot,
        ).onEach { it.actor?.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY) }

        listOf(
            bRRDynamicCircleLeft, bRRDynamicCircleRight,
            bPPDynamicHorizontalTop, bPPDynamicHorizontalBot,
            bRPDynamicCircleTop, bRPDynamicHorizontalBot
        ).onEachIndexed { index, dyno ->
            dyno.actor?.animHide(TIME_ANIM_ALPHA_PRACTICAL_BODY) {
                dyno.body?.also { b ->
                    b.setLinearVelocity(0f, 0f)
                    b.isAwake = false

                    b.setTransform(startPositionList[index], 0f)
                }
            }
        }
        endBlock()
    }

    override fun showAndUpdateBody() {
        createJ_RR()
        createJ_PP()
        createJ_RP()

        setOriginalId()
        aRatioLblList.onEach { it.label.setText(String.format("%.1f", APracticalSettings_JGear.ratioValue).replace(',', '.')) }
        aList.onEach { it.animShow(TIME_ANIM_ALPHA_PRACTICAL_BODY) }
        arrayOf(
            bRRDynamicCircleLeft, bRRDynamicCircleRight,
            bRRStaticLeft, bRRStaticRight,

            bPPDynamicHorizontalTop, bPPDynamicHorizontalBot,
            bPPStaticTop, bPPStaticBot,

            bRPDynamicCircleTop, bRPDynamicHorizontalBot,
            bRPStaticTop, bRPStaticBot,
        ).onEach {
            it.actor?.animShow(TIME_ANIM_ALPHA_PRACTICAL_BODY)
            it.body?.isAwake = true
        }
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Dynamic() {
        arrayOf(
            bRRDynamicCircleLeft, bRRDynamicCircleRight,
            bPPDynamicHorizontalTop, bPPDynamicHorizontalBot,
            bRPDynamicCircleTop, bRPDynamicHorizontalBot
        ).onEach {
            it.apply {
                id = BodyId.Practical.DYNAMIC
                collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.Practical.DYNAMIC))
            }
        }
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addJointTypesLbls() {
        addActors(aRRLbl, aPPLbl, aRPLbl)
        aRRLbl.setBoundsStandartBG(191f, 1093f, 319f, 42f)
        aPPLbl.setBoundsStandartBG(181f, 833f, 338f, 42f)
        aRPLbl.setBoundsStandartBG(191f, 579f, 328f, 42f)

        addActors(aRR_RatioImg, aPP_RatioImg, aRP_RatioImg)
        aRR_RatioImg.setBoundsStandartBG(323f, 984f, 81f, 73f)
        aPP_RatioImg.setBoundsStandartBG(37f, 665f, 98f, 74f)
        aRP_RatioImg.setBoundsStandartBG(37f, 323f, 98f, 74f)

        addActors(aRR_RatioLbl, aPP_RatioLbl, aRP_RatioLbl)
        aRR_RatioLbl.setBoundsStandartBG(296f, 984f, 54f, 30f)
        aPP_RatioLbl.setBoundsStandartBG(81f, 759f, 54f, 30f)
        aRP_RatioLbl.setBoundsStandartBG(81f, 417f, 54f, 30f)
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Dynamic() {
        createBody(bRRDynamicCircleLeft, 106f, 899f, 170f, 170f)
        createBody(bRRDynamicCircleRight, 425f, 899f, 170f, 170f)
        createBody(bRRStaticLeft, 178f, 972f, 25f, 25f)
        createBody(bRRStaticRight, 497f, 972f, 25f, 25f)

        createBody(bPPDynamicHorizontalTop, 265f, 739f, 170f, 70f)
        createBody(bPPDynamicHorizontalBot, 265f, 645f, 170f, 70f)
        createBody(bPPStaticTop, 337f, 762f, 25f, 25f)
        createBody(bPPStaticBot, 337f, 668f, 25f, 25f)

        createBody(bRPDynamicCircleTop, 265f, 385f, 170f, 170f)
        createBody(bRPDynamicHorizontalBot, 265f, 291f, 170f, 70f)
        createBody(bRPStaticTop, 337f, 458f, 25f, 25f)
        createBody(bRPStaticBot, 337f, 314f, 25f, 25f)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_RR() {
        createJoint(jRR_Left, RevoluteJointDef().apply {
            bodyA = bRRStaticLeft.body
            bodyB = bRRDynamicCircleLeft.body
        })
        createJoint(jRR_Right, RevoluteJointDef().apply {
            bodyA = bRRStaticRight.body
            bodyB = bRRDynamicCircleRight.body
        })
        createJoint(jRR_Gear, GearJointDef().apply {
            bodyA = bRRDynamicCircleLeft.body
            bodyB = bRRDynamicCircleRight.body
            joint1 = jRR_Left.joint
            joint2 = jRR_Right.joint

            ratio = APracticalSettings_JGear.ratioValue
        })
    }

    private fun createJ_PP() {
        createJoint(jPP_Top, PrismaticJointDef().apply {
            bodyA = bPPStaticTop.body
            bodyB = bPPDynamicHorizontalTop.body
        })
        createJoint(jPP_Bot, PrismaticJointDef().apply {
            bodyA = bPPStaticBot.body
            bodyB = bPPDynamicHorizontalBot.body
        })
        createJoint(jPP_Gear, GearJointDef().apply {
            bodyA = bPPDynamicHorizontalTop.body
            bodyB = bPPDynamicHorizontalBot.body
            joint1 = jPP_Top.joint
            joint2 = jPP_Bot.joint

            ratio = APracticalSettings_JGear.ratioValue
        })
    }

    private fun createJ_RP() {
        createJoint(jRP_Top, RevoluteJointDef().apply {
            bodyA = bRPStaticTop.body
            bodyB = bRPDynamicCircleTop.body
        })
        createJoint(jRP_Bot, PrismaticJointDef().apply {
            bodyA = bRPStaticBot.body
            bodyB = bRPDynamicHorizontalBot.body
        })
        createJoint(jRP_Gear, GearJointDef().apply {
            bodyA = bRPDynamicCircleTop.body
            bodyB = bRPDynamicHorizontalBot.body
            joint1 = jRP_Top.joint
            joint2 = jRP_Bot.joint

            ratio = APracticalSettings_JGear.ratioValue
        })
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun drawJoint() {
        bPPStaticBot.actor?.let { it.postDrawArray.add(AdvancedGroup.Static.Drawer { alpha ->
            jPP_Top.joint?.let { j ->
                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(j.anchorA).toUI,
                    tmpPositionB.set(j.anchorB).toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )
            }
            jPP_Bot.joint?.let { j ->
                screenBox2d.drawerUtil.drawer.line(
                    tmpPositionA.set(j.anchorA).toUI,
                    tmpPositionB.set(j.anchorB).toUI,
                    colorJoint.apply { a = alpha }, JOINT_WIDTH
                )
            }
        }) }
    }

}