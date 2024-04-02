package com.education.lbjt.game.box2d.bodiesGroup.dialog

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.MotorJoint
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.education.lbjt.R
import com.education.lbjt.game.actors.AIndicator
import com.education.lbjt.game.actors.button.AButton
import com.education.lbjt.game.actors.dialog.ADialogNickname
import com.education.lbjt.game.actors.image.AImage
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.box2d.AbstractBodyGroup
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.BodyId.Comment.DIALOG
import com.education.lbjt.game.box2d.bodies.BDialogNickname
import com.education.lbjt.game.box2d.bodies.BRegularBtn
import com.education.lbjt.game.box2d.bodies.standart.BStatic
import com.education.lbjt.game.data.User
import com.education.lbjt.game.utils.FIREBASE_DATABASE_USERS
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.NICKNAME_LENGTH
import com.education.lbjt.game.utils.TIME_ANIM_DIALOG_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.actor.disable
import com.education.lbjt.game.utils.actor.enable
import com.education.lbjt.game.utils.actor.setOnTouchListener
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedStage
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.font.FontParameter.CharType
import com.education.lbjt.game.utils.runGDX
import com.education.lbjt.game.utils.toB2
import com.education.lbjt.util.DataStoreManager
import com.education.lbjt.util.internetConnection
import com.education.lbjt.util.log
import kotlinx.coroutines.launch

class BGDialogNickname(override val screenBox2d: AdvancedBox2dScreen): AbstractBodyGroup() {

    override val standartW = 700f

    private val languageUtil = screenBox2d.game.languageUtil

    private val parameter = FontParameter()

    // Actor
    private val aBackgroundImg = AImage(screenBox2d, screenBox2d.drawerUtil.getRegion(Color.BLACK.apply { a = 0.6f }))
    private val aIndicator     = AIndicator(screenBox2d)

    // Body
    private val bStatic            = BStatic(screenBox2d)
    private val bDialogNickname    = BDialogNickname(screenBox2d)
    private val bRegularBtnCancel  = BRegularBtn(screenBox2d, languageUtil.getStringResource(R.string.cancel), Label.LabelStyle(screenBox2d.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.LATIN_CYRILLIC).setSize(30)), GameColor.textGreen))
    private val bRegularBtnPublish = BRegularBtn(screenBox2d, languageUtil.getStringResource(R.string.publish), Label.LabelStyle(screenBox2d.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.LATIN_CYRILLIC).setSize(30)), GameColor.textGreen))

    // Joint
    private val jPrismaticDialogNickname = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)
    private val jPrismaticCancel         = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)
    private val jPrismaticPublish        = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)
    private val jMotorDialogNickname     = AbstractJoint<MotorJoint, MotorJointDef>(screenBox2d)
    private val jMotorCancel             = AbstractJoint<MotorJoint, MotorJointDef>(screenBox2d)
    private val jMotorPublish            = AbstractJoint<MotorJoint, MotorJointDef>(screenBox2d)


    // Field
    var isShow = false
        private set

    var publishBlock : (String) -> Unit = {}
    var animShowBlock: () -> Unit = {}
    var animHideBlock: () -> Unit = {}

    private var nickname = ""


    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        screenBox2d.stageUI.apply {
            addBackgroundImg()
            addIndicator()
        }

        initB_DialogNickname()
        initB_RegularBtn()

        createB_Static()
        createB_DialogNickname()
        createB_RegularBtn()

        createJ_Joint()

        animHideBG()
        asyncCollectNicknameInputText()
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_DialogNickname() {
        bDialogNickname.apply {
            id = DIALOG
            collisionList.addAll(arrayOf(BodyId.BORDERS, DIALOG))

            fixtureDef.density = 1.2f
            getActor()?.focusBlock = { isFocus -> if (isFocus) upDialogNickname() else downDialogNickname() }
        }
    }

    private fun initB_RegularBtn() {
        listOf(bRegularBtnCancel, bRegularBtnPublish).onEach { it.apply {
            id = DIALOG
            collisionList.addAll(arrayOf(BodyId.BORDERS, DIALOG))

            fixtureDef.density = 2f
        } }

        bRegularBtnCancel.getActor()?.setOnTouchListener { animHideBG(TIME_ANIM_DIALOG_ALPHA) }
        bRegularBtnPublish.getActor()?.setOnTouchListener { publishHandler() }
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addBackgroundImg() {
        addActor(aBackgroundImg)
        aBackgroundImg.setBoundsStandartBG(0f, 0f, 700f, 1400f)
    }

    private fun AdvancedStage.addIndicator() {
        addActor(aIndicator)
        aIndicator.setBoundsStandartBG(320f, 870f, 60f, 60f)
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        createBody(bStatic, 340f, 690f, 20f, 20f)
    }

    private fun createB_DialogNickname() {
        createBody(bDialogNickname, 8f, 603f, 684f, 194f)
    }

    private fun createB_RegularBtn() {
        createBody(bRegularBtnCancel, 33f, 481f, 293f, 107f)
        createBody(bRegularBtnPublish, 374f, 481f, 293f, 107f)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Joint() {
        // DialogNickname
        createJ_PrismaticAndMotor(
            jPrismaticDialogNickname, jMotorDialogNickname,
            bStatic, bDialogNickname,
            Vector2(10f, 10f), Vector2(342f, 97f),
            100f, -100f,
            Vector2(10f, 10f),
            600f
        )
        // Cancel
        createJ_PrismaticAndMotor(
            jPrismaticCancel, jMotorCancel,
            bDialogNickname, bRegularBtnCancel,
            Vector2(171f, -68f), Vector2(146f, 54f),
            30f, -70f,
            Vector2(171f, -68f),
            250f
        )

        val tmpCancelLeftAnchorA  = Vector2(63f, 30f)
        val tmpCancelLeftAnchorB  = Vector2(38f, 101f)
        val tmpCancelRightAnchorA = Vector2(279f, 30f)
        val tmpCancelRightAnchorB = Vector2(254f, 101f)
        bDialogNickname.actor?.preDrawArray?.add(AdvancedGroup.Static.Drawer { alpha ->
            drawJoint(alpha, bDialogNickname, bRegularBtnCancel, tmpCancelLeftAnchorA, tmpCancelLeftAnchorB)
            drawJoint(alpha, bDialogNickname, bRegularBtnCancel, tmpCancelRightAnchorA, tmpCancelRightAnchorB)
        })
        // Publish
        createJ_PrismaticAndMotor(
            jPrismaticPublish, jMotorPublish,
            bDialogNickname, bRegularBtnPublish,
            Vector2(512f, -68f), Vector2(146f, 54f),
            30f, -70f,
            Vector2(512f, -68f),
            250f
        )
        val tmpPublishLeftAnchorA  = Vector2(404f, 30f)
        val tmpPublishLeftAnchorB  = Vector2(38f, 101f)
        val tmpPublishRightAnchorA = Vector2(620f, 30f)
        val tmpPublishRightAnchorB = Vector2(254f, 101f)
        bDialogNickname.actor?.preDrawArray?.add(AdvancedGroup.Static.Drawer { alpha ->
            drawJoint(alpha, bDialogNickname, bRegularBtnPublish, tmpPublishLeftAnchorA, tmpPublishLeftAnchorB)
            drawJoint(alpha, bDialogNickname, bRegularBtnPublish, tmpPublishRightAnchorA, tmpPublishRightAnchorB)
        })
    }

    // ---------------------------------------------------
    // Anim
    // ---------------------------------------------------

    fun animShowBG(time: Float = 0f, nickname: String) {
        animShowBlock()

        isShow = true

        setOriginalId()
        actorList.onEach {
            it.animShow(time)
            if (it is AButton) it.enable() else it.enable()
        }

        if (nickname.isNotBlank()) bDialogNickname.getActor()?.setNickname(nickname) else bRegularBtnPublish.getActor()?.disable()
        bDialogNickname.body?.apply { applyForceToCenter(Vector2(0f, -600_000f), true) }
    }

    fun animHideBG(time: Float = 0f) {
        animHideBlock()

        isShow = false
        screenBox2d.stageUI.unfocusAndHideKeyboard()

        setNoneId()
        actorList.onEach {
            it.disable()
            it.animHide(time)
        }
    }

    // ---------------------------------------------------
    // Joint Util
    // ---------------------------------------------------

    private fun createJ_PrismaticAndMotor(
        _jPrismatic: AbstractJoint<PrismaticJoint, PrismaticJointDef>,
        _jMotor    : AbstractJoint<MotorJoint, MotorJointDef>,
        _bodyA: AbstractBody,
        _bodyB: AbstractBody,
        _anchorA: Vector2,
        _anchorB: Vector2,
        _upper: Float,
        _lower: Float,
        _offset: Vector2,
        _maxForce: Float
    ) {
        createJoint(_jPrismatic, PrismaticJointDef().apply {
            bodyA = _bodyA.body
            bodyB = _bodyB.body
            collideConnected = true

            localAxisA.set(0f, 1f)
            localAnchorA.set(_anchorA.subCenter(bodyA))
            localAnchorB.set(_anchorB.subCenter(bodyB))

            enableLimit = true
            upperTranslation = _upper.toStandartBG.toB2
            lowerTranslation = _lower.toStandartBG.toB2
        })

        createJoint(_jMotor, MotorJointDef().apply {
            bodyA = _bodyA.body
            bodyB = _bodyB.body
            collideConnected = true

            linearOffset.set(_offset.subCenter(bodyA))

            maxForce  = _maxForce * bodyB.mass
            correctionFactor = 0.8f
        })
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun upDialogNickname() {
        bStatic.body?.apply {
            runGDX {
                setTransform(position.add(Vector2(0f, 207f).toStandartBG.toB2), 0f)
                bDialogNickname.body?.apply { applyForce(Vector2(0f, 10f), worldCenter, true) }
            }
        }
    }

    private fun downDialogNickname() {
        bStatic.body?.apply {
            runGDX {
                setTransform(position.add(Vector2(0f, -207f).toStandartBG.toB2), 0f)
                bDialogNickname.body?.apply { applyForce(Vector2(0f, -10f), worldCenter, true) }
            }
        }
    }

    private fun asyncCollectNicknameInputText() {
        bDialogNickname.getActor()?.run {
            coroutine?.launch {
                textFlow.collect { text ->
                    runGDX {
                        nickname = text

                        if (text.length > NICKNAME_LENGTH || text.isBlank()) {
                            bRegularBtnPublish.getActor()?.disable()
                            ADialogNickname.Static.State.ERROR
                        } else {
                            bRegularBtnPublish.getActor()?.enable()
                            ADialogNickname.Static.State.DEFAULT
                        }.also { setState(it) }
                    }
                }
            }
        }
    }

    private fun publishHandler() {
        screenBox2d.stageUI.unfocusAndHideKeyboard()
        actorList.filterNot { it == aBackgroundImg }.onEach { it.disable() }

        if (screenBox2d.game.activity.internetConnection()) {
            aIndicator.animShowLoader()

            val database = Firebase.database.reference

            database.child(FIREBASE_DATABASE_USERS)
                .child(screenBox2d.game.activity.user.id!!)
                .child(User.NICKNAME)
                .setValue(nickname)
                .addOnSuccessListener {
                    log("Save Nickname")
                    runGDX {
                        aIndicator.animHideLoader()
                        animHideBG(TIME_ANIM_DIALOG_ALPHA)
                        publishBlock.invoke(nickname)
                    }
                    coroutine?.launch { DataStoreManager.UserNickname.update { nickname }}
                }.addOnFailureListener { exception ->
                    log("Failure Nickname: ${exception.message}")
                    runGDX {
                        aIndicator.animHideLoader()
                        actorList.filterNot { it == aBackgroundImg }.onEach { it.enable() }
                    }
                }
        } else {
            aIndicator.animShowNoWifi()
            actorList.filterNot { it == aBackgroundImg }.onEach { it.enable() }
        }
    }

}