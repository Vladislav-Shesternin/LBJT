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
import com.education.lbjt.game.actors.dialog.ADialogComment
import com.education.lbjt.game.actors.image.AImage
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.box2d.AbstractBodyGroup
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.BodyId.Comment.DIALOG
import com.education.lbjt.game.box2d.bodies.BDialogComment
import com.education.lbjt.game.box2d.bodies.BRegularBtn
import com.education.lbjt.game.box2d.bodies.standart.BStatic
import com.education.lbjt.game.data.Comment
import com.education.lbjt.game.utils.COMMENT_LENGTH
import com.education.lbjt.game.utils.FIREBASE_DATABASE_COMMENTS
import com.education.lbjt.game.utils.GameColor
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
import com.education.lbjt.util.internetConnection
import com.education.lbjt.util.log
import kotlinx.coroutines.launch

class BGDialogComment(override val screenBox2d: AdvancedBox2dScreen): AbstractBodyGroup() {

    override val standartW = 700f

    private val activity     = screenBox2d.game.activity
    private val languageUtil = screenBox2d.game.languageUtil

    private val parameter = FontParameter()

    // Actor
    private val aBackgroundImg = AImage(screenBox2d, screenBox2d.drawerUtil.getRegion(Color.BLACK.apply { a = 0.6f }))
    private val aIndicator     = AIndicator(screenBox2d)

    // Body
    private val bStatic            = BStatic(screenBox2d)
    private val bDialogComment     = BDialogComment(screenBox2d)
    private val bRegularBtnCancel  = BRegularBtn(screenBox2d, languageUtil.getStringResource(R.string.cancel), Label.LabelStyle(screenBox2d.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.LATIN_CYRILLIC).setSize(30)), GameColor.textGreen))
    private val bRegularBtnPublish = BRegularBtn(screenBox2d, languageUtil.getStringResource(R.string.publish), Label.LabelStyle(screenBox2d.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.LATIN_CYRILLIC).setSize(30)), GameColor.textGreen))

    // Joint
    private val jPrismaticDialogComment = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)
    private val jPrismaticCancel        = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)
    private val jPrismaticPublish       = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)
    private val jMotorDialogComment     = AbstractJoint<MotorJoint, MotorJointDef>(screenBox2d)
    private val jMotorCancel            = AbstractJoint<MotorJoint, MotorJointDef>(screenBox2d)
    private val jMotorPublish           = AbstractJoint<MotorJoint, MotorJointDef>(screenBox2d)


    // Field
    var isShow = false
        private set

    var animShowBlock: () -> Unit = {}
    var animHideBlock: () -> Unit = {}

    private var comment = ""

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        screenBox2d.stageUI.apply {
            addBackgroundImg()
            addIndicator()
        }

        initB_DialogComment()
        initB_RegularBtn()

        createB_Static()
        createB_DialogComment()
        createB_RegularBtn()

        createJ_Joint()

        animHideBG()
        asyncCollectCommentInputText()
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_DialogComment() {
        bDialogComment.apply {
            id = DIALOG
            collisionList.addAll(arrayOf(BodyId.BORDERS, DIALOG))

            getActor()?.focusBlock = { isFocus -> if (isFocus) upDialogComment() else downDialogComment() }
        }
    }

    private fun initB_RegularBtn() {
        listOf(bRegularBtnCancel, bRegularBtnPublish).onEach { it.apply {
            id = DIALOG
            collisionList.addAll(arrayOf(BodyId.BORDERS, DIALOG))

            fixtureDef.density = 7f
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
        aIndicator.setBoundsStandartBG(320f, 1065f, 60f, 60f)
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        createBody(bStatic, 340f, 690f, 20f, 20f)
    }

    private fun createB_DialogComment() {
        createBody(bDialogComment, 8f, 408f, 684f, 584f)
    }

    private fun createB_RegularBtn() {
        createBody(bRegularBtnCancel, 33f, 286f, 293f, 107f)
        createBody(bRegularBtnPublish, 374f, 286f, 293f, 107f)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Joint() {
        // DialogNickname
        createJ_PrismaticAndMotor(
            jPrismaticDialogComment, jMotorDialogComment,
            bStatic, bDialogComment,
            Vector2(10f, 10f), Vector2(342f, 292f),
            100f, -100f,
            Vector2(10f, 10f),
            600f
        )
        // Cancel
        createJ_PrismaticAndMotor(
            jPrismaticCancel, jMotorCancel,
            bDialogComment, bRegularBtnCancel,
            Vector2(171f, -68f), Vector2(146f, 54f),
            30f, -70f,
            Vector2(171f, -68f),
            250f
        )
        val tmpCancelLeftAnchorA  = Vector2(63f, 30f)
        val tmpCancelLeftAnchorB  = Vector2(38f, 101f)
        val tmpCancelRightAnchorA = Vector2(279f, 30f)
        val tmpCancelRightAnchorB = Vector2(254f, 101f)
        bDialogComment.actor?.preDrawArray?.add(AdvancedGroup.Static.Drawer { alpha ->
            drawJoint(alpha, bDialogComment, bRegularBtnCancel, tmpCancelLeftAnchorA, tmpCancelLeftAnchorB)
            drawJoint(alpha, bDialogComment, bRegularBtnCancel, tmpCancelRightAnchorA, tmpCancelRightAnchorB)
        })
        // Publish
        createJ_PrismaticAndMotor(
            jPrismaticPublish, jMotorPublish,
            bDialogComment, bRegularBtnPublish,
            Vector2(512f, -68f), Vector2(146f, 54f),
            30f, -70f,
            Vector2(512f, -68f),
        250f
        )
        val tmpPublishLeftAnchorA  = Vector2(404f, 30f)
        val tmpPublishLeftAnchorB  = Vector2(38f, 101f)
        val tmpPublishRightAnchorA = Vector2(620f, 30f)
        val tmpPublishRightAnchorB = Vector2(254f, 101f)
        bDialogComment.actor?.preDrawArray?.add(AdvancedGroup.Static.Drawer { alpha ->
            drawJoint(alpha, bDialogComment, bRegularBtnPublish, tmpPublishLeftAnchorA, tmpPublishLeftAnchorB)
            drawJoint(alpha, bDialogComment, bRegularBtnPublish, tmpPublishRightAnchorA, tmpPublishRightAnchorB)
        })
    }

    // ---------------------------------------------------
    // Anim
    // ---------------------------------------------------

    fun animShowBG(time: Float = 0f) {
        animShowBlock()

        isShow = true

        setOriginalId()
        actorList.onEach {
            it.animShow(time)
            it.enable()
        }

        if (comment.isEmpty()) bRegularBtnPublish.getActor()?.disable()
        bDialogComment.body?.apply { applyForceToCenter(Vector2(0f, -2_000_000f), true) }
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

    private fun upDialogComment() {
        bStatic.body?.apply {
            runGDX {
                setTransform(position.add(Vector2(0f, 309f).toStandartBG.toB2), 0f)
                bDialogComment.body?.apply { applyForce(Vector2(0f, 10f), worldCenter, true) }
            }
        }
    }

    private fun downDialogComment() {
        bStatic.body?.apply {
            runGDX {
                setTransform(position.add(Vector2(0f, -309f).toStandartBG.toB2), 0f)
                bDialogComment.body?.apply { applyForce(Vector2(0f, -10f), worldCenter, true) }
            }
        }
    }

    private fun asyncCollectCommentInputText() {
        coroutine?.launch {
            bDialogComment.getActor()?.run {
                textFlow.collect { text ->
                    comment = text

                    if (text.length > COMMENT_LENGTH || text.isBlank()) {
                        bRegularBtnPublish.getActor()?.disable()
                        ADialogComment.Static.State.ERROR
                    } else {
                        bRegularBtnPublish.getActor()?.enable()
                        ADialogComment.Static.State.DEFAULT
                    }.also { setState(it) }
                }
            }
        }
    }

    private fun publishHandler() {
        screenBox2d.stageUI.unfocusAndHideKeyboard()
        actorList.filterNot { it == aBackgroundImg }.onEach {it.disable() }

        if (activity.internetConnection()) {
            aIndicator.animShowLoader()

            val database = Firebase.database.reference

            database.child(FIREBASE_DATABASE_COMMENTS)
                .child(System.currentTimeMillis().toString())
                .setValue(Comment(activity.user.id, comment))
                .addOnSuccessListener {
                    log("Save Comment")
                    aIndicator.animHideLoader()
                    animHideBG(TIME_ANIM_DIALOG_ALPHA)
                }.addOnFailureListener { exception ->
                    log("Failure Comment: ${exception.message}")
                    aIndicator.animHideLoader()
                    actorList.filterNot { it == aBackgroundImg }.onEach { it.enable() }
                }
        } else {
            aIndicator.animShowNoWifi()
            actorList.filterNot { it == aBackgroundImg }.onEach { it.enable() }
        }
    }

}