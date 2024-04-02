//package com.education.lbjt.game.box2d.bodiesGroup
//
//import com.badlogic.gdx.math.Vector2
//import com.badlogic.gdx.physics.box2d.joints.MotorJoint
//import com.badlogic.gdx.physics.box2d.joints.MotorJointDef
//import com.education.lbjt.game.actors.AIndicator
//import com.education.lbjt.game.actors.button.AButton_Monetization
//import com.education.lbjt.game.box2d.AbstractBody
//import com.education.lbjt.game.box2d.AbstractBodyGroup
//import com.education.lbjt.game.box2d.AbstractJoint
//import com.education.lbjt.game.box2d.BodyId
//import com.education.lbjt.game.box2d.BodyId.AboutAuthor.ITEM
//import com.education.lbjt.game.box2d.bodies.BMonetizationBtn
//import com.education.lbjt.game.box2d.bodies.standart.BStatic
//import com.education.lbjt.game.utils.JOINT_WIDTH
//import com.education.lbjt.game.utils.actor.disable
//import com.education.lbjt.game.utils.actor.enable
//import com.education.lbjt.game.utils.actor.setOnTouchListener
//import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
//import com.education.lbjt.game.utils.advanced.AdvancedGroup
//import com.education.lbjt.game.utils.advanced.AdvancedStage
//import com.education.lbjt.game.utils.toUI
//
//class BGMonetization(override val screenBox2d: AdvancedBox2dScreen): AbstractBodyGroup() {
//
//    override val standartW = 622f
//
//    // Actors
//    private val aIndicator = AIndicator(screenBox2d)
//
//    // Body
//    private val bStatic              = BStatic(screenBox2d)
//    private val bMonetizationBtnAds  = BMonetizationBtn(screenBox2d)
//    private val bMonetizationBtnGift = BMonetizationBtn(screenBox2d)
//
//    // Joint
//    private val jMotorAds  = AbstractJoint<MotorJoint, MotorJointDef>(screenBox2d)
//    private val jMotorGift = AbstractJoint<MotorJoint, MotorJointDef>(screenBox2d)
//
//    // Field
//    private val drawer        = screenBox2d.drawerUtil.drawer
//    private val tmpPositionA  = Vector2()
//    private val tmpPositionA1 = Vector2()
//    private val tmpPositionB  = Vector2()
//    private val tmpPositionB1 = Vector2()
//
//    override fun create(x: Float, y: Float, w: Float, h: Float) {
//        super.create(x, y, w, h)
//
//        initB_Monetization()
//
//        createB_Static()
//        createB_Monetization()
//
//        createJ_MotorAds()
//
//        screenBox2d.stageUI.apply { addIndicator() }
//    }
//
//    // ---------------------------------------------------
//    // Init
//    // ---------------------------------------------------
//
//    private fun initB_Monetization() {
//        arrayOf(bMonetizationBtnAds, bMonetizationBtnGift).onEach { it.apply {
//            id = ITEM
//            collisionList.addAll(arrayOf(BodyId.BORDERS, ITEM))
//            bodyDef.fixedRotation = true
//        } }
//
//        bMonetizationBtnAds.actor?.setOnTouchListener { handlerTouchMonetizationBtnAds() }
//        bMonetizationBtnGift.actor?.setOnTouchListener { handlerTouchMonetizationBtnAds() }
//    }
//
//    // ---------------------------------------------------
//    // Add Actor
//    // ---------------------------------------------------
//
//    private fun AdvancedStage.addIndicator() {
//        addActor(aIndicator)
//        aIndicator.setBoundsStandartBG(281f, 447f, 60f, 60f)
//    }
//
//    // ---------------------------------------------------
//    // Create Body
//    // ---------------------------------------------------
//
//    private fun createB_Static() {
//        createBody(bStatic, 303f, 310f, 15f, 15f)
//    }
//
//    private fun createB_Monetization() {
//        createBody(bMonetizationBtnAds, 0f, 211f, 296f, 296f)
//        createBody(bMonetizationBtnGift, 326f, 211f, 296f, 296f)
//    }
//
//    // ---------------------------------------------------
//    // Create Joint
//    // ---------------------------------------------------
//
//    private fun createJ_MotorAds() {
//        listOf<Static.MotorJointData>(
//            Static.MotorJointData(
//                jMotorAds,
//                bMonetizationBtnAds,
//                offset = Vector2(-155f, 46f),
//                anchorA = Vector2(-155f, -310f),
//                anchorB = Vector2(147f, 6f)
//            ),
//            Static.MotorJointData(
//                jMotorGift,
//                bMonetizationBtnGift,
//                offset = Vector2(170f, 46f),
//                anchorA = Vector2(170f, -310f),
//                anchorB = Vector2(147f, 6f)
//            ),
//        ).onEach { data ->
//            createJoint(data.joint, MotorJointDef().apply {
//                bodyA = bStatic.body
//                bodyB = data.bodyB.body
//
//                linearOffset.set(data.offset.subCenter(bodyA))
//
//                maxForce = 200 * bodyB.mass
//                correctionFactor = 0.95f
//
//                data.bodyB.actor?.preDrawArray?.add(AdvancedGroup.Static.Drawer { alpha ->
//                    drawer.line(
//                        tmpPositionA.set(bodyA.position).add(tmpPositionA1.set(data.anchorA).subCenter(bodyA)).toUI,
//                        tmpPositionB.set(bodyB.position).add(tmpPositionB1.set(data.anchorB).subCenter(bodyB)).toUI,
//                        colorJoint.apply { a = alpha }, JOINT_WIDTH
//                    )
//                })
//            })
//        }
//    }
//
//    // ---------------------------------------------------
//    // Logic
//    // ---------------------------------------------------
//
//    private fun handlerTouchMonetizationBtnAds() {
//        screenBox2d.stageUI.root.disable()
//        aIndicator.animShowLoader()
//        screenBox2d.game.activity.rewardedUtil.apply { loadAndShow(
//            successBlock = {
//                screenBox2d.stageUI.root.enable()
//                aIndicator.animHideLoader()
//            },
//            failedBlock  = this@BGMonetization::failedBlock
//        ) }
//    }
//
//    private fun failedBlock() {
//        screenBox2d.stageUI.root.enable()
//        val btns = listOf(bMonetizationBtnAds, bMonetizationBtnGift)
//
//        btns.onEach { it.actor?.disable() }
//        aIndicator.animShowNoWifi { btns.onEach { it.actor?.enable() } }
//    }
//
//    object Static {
//        data class MotorJointData(
//            val joint: AbstractJoint<MotorJoint, MotorJointDef>,
//            val bodyB: AbstractBody,
//            val offset: Vector2,
//            val anchorA: Vector2,
//            val anchorB: Vector2,
//        )
//    }
//
//}