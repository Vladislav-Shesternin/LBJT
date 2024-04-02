package com.education.lbjt.game.box2d.bodiesGroup

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.result.contract.ActivityResultContracts
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef
import com.badlogic.gdx.physics.box2d.joints.MotorJoint
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.education.lbjt.R
import com.education.lbjt.game.box2d.AbstractBody
import com.education.lbjt.game.box2d.AbstractBodyGroup
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.BodyId.Comment.ITEM
import com.education.lbjt.game.box2d.bodies.BFrameNickname
import com.education.lbjt.game.box2d.bodies.BIcon
import com.education.lbjt.game.box2d.bodies.BRegularBtn
import com.education.lbjt.game.box2d.bodies.BSeparator
import com.education.lbjt.game.utils.FIREBASE_STORAGE_ICON
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.LOCAL_ICON_DIR
import com.education.lbjt.game.utils.actor.disable
import com.education.lbjt.game.utils.actor.enable
import com.education.lbjt.game.utils.actor.setOnTouchListener
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.font.FontParameter.CharType
import com.education.lbjt.game.utils.region
import com.education.lbjt.game.utils.runGDX
import com.education.lbjt.game.utils.toB2
import com.education.lbjt.game.utils.toTexture
import com.education.lbjt.util.internetConnection
import com.education.lbjt.util.log
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream


class BGComment(override val screenBox2d: AdvancedBox2dScreen): AbstractBodyGroup() {

    override val standartW = 700f

    private val parameter = FontParameter()

    // Body
    private val bSeparator              = BSeparator(screenBox2d)
    private val bIcon                   = BIcon(screenBox2d)
    private val bFrameNickname          = BFrameNickname(screenBox2d)
    private val bRegularBtnWriteComment = BRegularBtn(screenBox2d, screenBox2d.game.languageUtil.getStringResource(R.string.write_comment), Label.LabelStyle(screenBox2d.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.LATIN_CYRILLIC).setSize(30)), GameColor.textGreen))

    // Joint
    private val jDistanceBtnWriteCommentLeft  = AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d)
    private val jDistanceBtnWriteCommentRight = AbstractJoint<DistanceJoint, DistanceJointDef>(screenBox2d)
    private val jPrismaticNickname            = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)
    private val jPrismaticIcon                = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)
    private val jMotorNickname                = AbstractJoint<MotorJoint, MotorJointDef>(screenBox2d)
    private val jMotorIcon                    = AbstractJoint<MotorJoint, MotorJointDef>(screenBox2d)

    // Field
    private val activity = screenBox2d.game.activity

    var nicknameBlock              : () -> Unit = {}
    var btnWriteCommentFailureBlock: () -> Unit = {}
    var btnWriteCommentSuccessBlock: () -> Unit = {}

    private val fileHandlerIcon       = Gdx.files.local(LOCAL_ICON_DIR)
    private var iconTexture: Texture? = null

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        initB_Separator()
        initB_Item()

        createB_Separator()
        createB_Item()

        createJ_Icon()
        createJ_BtnWriteComment()
        createJ_Nickname()

        screenBox2d.game.activity.user.nickname?.let { updateNickname(it) }
        if (fileHandlerIcon.exists()) updateIcon()
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Separator() {
        bSeparator.apply {
            id = BodyId.SEPARATOR
            collisionList.add(ITEM)
        }
    }

    private fun initB_Item() {
        arrayOf(bIcon, bFrameNickname, bRegularBtnWriteComment).onEach {
            it.apply {
                id = ITEM
                collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.SEPARATOR, ITEM))
            }
        }
        bRegularBtnWriteComment.bodyDef.apply {
            linearDamping  = 0.5f
            angularDamping = 0.5f
        }
        bRegularBtnWriteComment.fixtureDef.density = 5f

        bIcon                  .getActor()?.setOnTouchListener { iconHandler() }
        bFrameNickname         .getActor()?.setOnTouchListener { nicknameHandler() }
        bRegularBtnWriteComment.getActor()?.setOnTouchListener { btnWriteCommentHandler() }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Separator() {
        createBody(bSeparator, 0f, 0f, 700f, 10f)
    }

    private fun createB_Item() {
        createBody(bIcon, 11f, 203f, 154f, 154f)
        createBody(bFrameNickname, 181f, 230f, 501f, 101f)
        createBody(bRegularBtnWriteComment, 285f, 56f, 293f, 106f)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Icon() {
        createJ_PrismaticAndMotor(
            jPrismaticIcon, jMotorIcon,
            bIcon,
            Vector2(88f, 5f), Vector2(77f, 77f),
            310f, 100f,
            Vector2(88f, 280f)
        )

        val tmpAnchorA = Vector2(88f, 5f)
        val tmpAnchorB = Vector2(77f, 4f)
        bSeparator.actor?.preDrawArray?.add(AdvancedGroup.Static.Drawer { alpha ->
            drawJoint(
                alpha,
                bSeparator,
                bIcon,
                tmpAnchorA,
                tmpAnchorB
            )
        })
    }

    private fun createJ_BtnWriteComment() {
        createJ_Distance(
            jDistanceBtnWriteCommentLeft,
            bFrameNickname, bRegularBtnWriteComment,
            Vector2(30f, 4f), Vector2(10f, 93f), 118f,
        )
        createJ_Distance(
            jDistanceBtnWriteCommentRight,
            bFrameNickname, bRegularBtnWriteComment,
            Vector2(470f, 4f), Vector2(282f, 93f), 118f,
        )
    }

    private fun createJ_Nickname() {
        createJ_PrismaticAndMotor(
            jPrismaticNickname, jMotorNickname,
            bFrameNickname,
            Vector2(431f, 5f), Vector2(250f, 50f),
            310f, 160f,
            Vector2(431f, 280f)
        )
        val tmpLeftAnchorA  = Vector2(234f, 396f)
        val tmpLeftAnchorB  = Vector2(53f, 96f)
        val tmpRightAnchorA = Vector2(628f, 396f)
        val tmpRightAnchorB = Vector2(447f, 96f)
        bSeparator.actor?.preDrawArray?.add(AdvancedGroup.Static.Drawer { alpha ->
            drawJoint(alpha, bSeparator, bFrameNickname, tmpLeftAnchorA, tmpLeftAnchorB)
            drawJoint(alpha, bSeparator, bFrameNickname, tmpRightAnchorA, tmpRightAnchorB)
        })
    }

    // ---------------------------------------------------
    // Joint Util
    // ---------------------------------------------------

    private fun createJ_PrismaticAndMotor(
        _jPrismatic: AbstractJoint<PrismaticJoint, PrismaticJointDef>,
        _jMotor    : AbstractJoint<MotorJoint, MotorJointDef>,
        _bodyB: AbstractBody,
        _anchorA: Vector2,
        _anchorB: Vector2,
        _upper: Float,
        _lower: Float,
        _offset: Vector2,
    ) {
        createJoint(_jPrismatic, PrismaticJointDef().apply {
            bodyA = bSeparator.body
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
            bodyA = bSeparator.body
            bodyB = _bodyB.body
            collideConnected = true

            linearOffset.set(_offset.subCenter(bodyA))

            maxForce  = 250 * bodyB.mass
            correctionFactor = 0.8f
        })
    }

    private fun createJ_Distance(
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

            length = _length.toStandartBG.toB2
            frequencyHz  = 4f
            dampingRatio = 0.5f
        })

        _bodyA.actor?.preDrawArray?.add(AdvancedGroup.Static.Drawer { alpha -> _joint.drawJoint(alpha) })
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun nicknameHandler() = nicknameBlock()

    private fun iconHandler() {
        bIcon.getActor()?.disable()

        if (activity.internetConnection()) {
            bIcon.getActor()?.animShowLoader()

            val iconRef = Firebase.storage.getReferenceFromUrl(FIREBASE_STORAGE_ICON)

            activity.launchPickMedia(ActivityResultContracts.PickVisualMedia.ImageOnly) { it?.let { uri ->
                var iconStream           : InputStream?  = null
                var byteArrayOutputStream: OutputStream? = null
                var originalBitmap       : Bitmap?       = null
                var resizedBitmap        : Bitmap?       = null

                try {
                    iconStream            = activity.contentResolver.openInputStream(uri)
                    byteArrayOutputStream = ByteArrayOutputStream()
                    originalBitmap        = BitmapFactory.decodeStream(iconStream)
                    resizedBitmap         = Bitmap.createScaledBitmap(originalBitmap, 200, 200, true)

                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                    val byteArray = byteArrayOutputStream.toByteArray()

                    iconRef.child("${activity.user.id}").putBytes(byteArray)
                        .addOnSuccessListener {
                            runGDX {
                                log("Save Icon")
                                fileHandlerIcon.child("${activity.user.id}").writeBytes(byteArray, false)
                                updateIcon()
                                bIcon.getActor()?.apply { animHideLoader() }?.enable()
                            }
                        }.addOnFailureListener {
                            log("Failure Icon")
                            bIcon.getActor()?.apply { animHideLoader() }?.enable()
                        }
                } catch (e: Exception) {
                    e.printStackTrace()
                    bIcon.getActor()?.apply { animHideLoader() }?.enable()
                } finally {
                    iconStream?.close()
                    byteArrayOutputStream?.close()
                    originalBitmap?.recycle()
                    resizedBitmap?.recycle()
                }

            } ?: bIcon.getActor()?.apply { animHideLoader() }?.enable() }

        } else bIcon.getActor()?.apply { animShowNoWifi() }?.enable()
    }

    private fun btnWriteCommentHandler() {
        when {
            bIcon.isUpdated.not() && bFrameNickname.isUpdated.not() -> {
                impulseB_Icon()
                impulseB_Nickname()
            }
            bIcon.isUpdated.not()          -> impulseB_Icon()
            bFrameNickname.isUpdated.not() -> impulseB_Nickname()
            else                           -> btnWriteCommentSuccessBlock()
        }

        if (bIcon.isUpdated.not() || bFrameNickname.isUpdated.not()) btnWriteCommentFailureBlock()
    }

    fun impulseB_Icon() = bIcon.body?.apply { applyLinearImpulse(Vector2(0f, -1000f), worldCenter, true) }
    fun impulseB_Nickname() = bFrameNickname.body?.apply { applyLinearImpulse(Vector2(0f, -1500f), worldCenter, true) }

    fun updateNickname(nickname: String) {
        bFrameNickname.updateNickname(nickname)
    }

    private fun updateIcon() {
        val newTexture = fileHandlerIcon.child("${activity.user.id}").readBytes().toTexture()
        disposableSet.add(newTexture)
        iconTexture?.also { disposableSet.remove(it) }?.dispose()
        iconTexture = newTexture

        bIcon.updateIcon(newTexture.region)
    }

    fun getNickname(): String = bFrameNickname.getNickname()

}