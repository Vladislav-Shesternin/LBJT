package com.education.lbjt.game.screens

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.education.lbjt.game.LibGDXGame
import com.education.lbjt.game.actors.AIndicator
import com.education.lbjt.game.actors.scroll.ACommentScrollPanel
import com.education.lbjt.game.box2d.bodiesGroup.BGBorders
import com.education.lbjt.game.box2d.bodiesGroup.BGComment
import com.education.lbjt.game.box2d.bodiesGroup.dialog.BGDialogComment
import com.education.lbjt.game.box2d.bodiesGroup.dialog.BGDialogNickname
import com.education.lbjt.game.box2d.destroyAll
import com.education.lbjt.game.data.Comment
import com.education.lbjt.game.data.User
import com.education.lbjt.game.utils.COMMENT_COUNT
import com.education.lbjt.game.utils.FIREBASE_DATABASE_COMMENTS
import com.education.lbjt.game.utils.FIREBASE_DATABASE_USERS
import com.education.lbjt.game.utils.FIREBASE_STORAGE_ICON
import com.education.lbjt.game.utils.TIME_ANIM_DIALOG_ALPHA
import com.education.lbjt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.actor.disable
import com.education.lbjt.game.utils.advanced.AdvancedMouseScreen
import com.education.lbjt.game.utils.advanced.AdvancedStage
import com.education.lbjt.game.utils.region
import com.education.lbjt.game.utils.runGDX
import com.education.lbjt.game.utils.toTexture
import com.education.lbjt.util.ChildEventAdapter
import com.education.lbjt.util.internetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CommentsScreen(override val game: LibGDXGame): AdvancedMouseScreen(game) {

    // Actor
    private val aCommentScrollPanel = ACommentScrollPanel(this)
    private val aIndicator          = AIndicator(this)

    // BodyGroup
    private val bgBorders        = BGBorders(this)
    private val bgComment        = BGComment(this)
    private val bgDialogNickname = BGDialogNickname(this)
    private val bgDialogComment  = BGDialogComment(this)

    // Field
    private var listenerChildAdded: ChildEventListener? = null
    private val commentFlow       = MutableSharedFlow<Comment>(COMMENT_COUNT)
    private val mainActors        by lazy { bgComment.actorList.toMutableList().apply { addAll(
        listOf(aCommentScrollPanel, aIndicator)
    )} }

    override fun show() {
        stageUI.root.animHide()
        setUIBackground(game.themeUtil.assets.BACKGROUND.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addCommentScrollPanel()
        addIndicator()

        createBG_Borders()
        createBG_Comment()
        createBG_DialogNickname()
        createBG_DialogComment()
        bgComment.apply {
            impulseB_Icon()
            impulseB_Nickname()
        }
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)

        coroutine?.launch(Dispatchers.IO) { getComments() }
    }

    override fun keyDown(keycode: Int): Boolean {
        when(keycode) {
            Input.Keys.BACK -> { when {
                bgDialogNickname.isShow -> bgDialogNickname.animHideBG(TIME_ANIM_DIALOG_ALPHA)
                bgDialogComment.isShow  -> bgDialogComment.animHideBG(TIME_ANIM_DIALOG_ALPHA)
                else                    -> {
                    if (game.navigationManager.isBackStackEmpty()) game.navigationManager.exit()
                    else stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.back() }
                }
            } }
            Input.Keys.ENTER -> stageUI.unfocusAndHideKeyboard()
        }
        return true
    }

    override fun dispose() {
        listenerChildAdded?.remove()
        listOf(bgBorders, bgComment, bgDialogNickname, bgDialogComment).destroyAll()
        super.dispose()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addCommentScrollPanel() {
        addActor(aCommentScrollPanel)
        aCommentScrollPanel.setBounds(0f, 0f, 700f, 1000f)
        aCommentScrollPanel.addCommentVeldan()
    }

    private fun AdvancedStage.addIndicator() {
        addActor(aIndicator)
        aIndicator.setBounds(265f, 490f, 170f, 170f)
        aIndicator.disable()
    }

    // ------------------------------------------------------------------------
    // Create Body Group
    // ------------------------------------------------------------------------
    private fun createBG_Borders() {
        bgBorders.create(0f,0f,700f,1400f)
    }

    private fun createBG_Comment() {
        bgComment.create(0f,1004f,700f,396f)
        bgComment.apply {
            nicknameBlock               = { bgDialogNickname.animShowBG(TIME_ANIM_DIALOG_ALPHA, bgComment.getNickname()) }
            btnWriteCommentSuccessBlock = { bgDialogComment.animShowBG(TIME_ANIM_DIALOG_ALPHA) }
            btnWriteCommentFailureBlock = { aCommentScrollPanel.scrollToVeldanComment() }
        }
    }

    private fun createBG_DialogNickname() {
        bgDialogNickname.create(0f, 0f, 700f, 1400f)
        bgDialogNickname.apply {
            publishBlock  = { bgComment.updateNickname(it) }
            animShowBlock = { animHideMainActors() }
            animHideBlock = { animShowMainActors() }
        }

    }

    private fun createBG_DialogComment() {
        bgDialogComment.create(0f,0f,700f,1400f)
        bgDialogComment.apply {
            animShowBlock = { animHideMainActors() }
            animHideBlock = { animShowMainActors() }
        }
    }

    // ---------------------------------------------------
    // Get Comment
    // ---------------------------------------------------

    private suspend fun getComments() {
        if (game.activity.internetConnection().not()) aIndicator.animShowNoWifi()

        listenerChildAdded = object : ChildEventAdapter {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.getValue<Comment>()?.let { data -> commentFlow.tryEmit(data) }
            }
        }

        val commentCount = getCommentCount()
        val startIndex   = if ((commentCount - (COMMENT_COUNT-1)) > 0) commentCount - (COMMENT_COUNT-1) else 0

        coroutine?.launch {
            commentFlow.collectIndexed { index, data ->
                if (isActive && index >= startIndex) data.user_id?.let { userId ->
                    val nickname    = getNickname(userId)
                    val iconTexture = getIconTexture(userId).also { disposableSet.add(it) }
                    val comment     = data.comment

                    runGDX { if (isActive) aCommentScrollPanel.addComment(User(
                        nickname = nickname,
                        icon     = iconTexture,
                        comment  = comment
                    )) }
                }
            }
        }

        listenerChildAdded?.add()

    }

    private fun ChildEventListener.add() = Firebase.database.reference.child(FIREBASE_DATABASE_COMMENTS).addChildEventListener(this)
    private fun ChildEventListener.remove() = Firebase.database.reference.child(FIREBASE_DATABASE_COMMENTS).removeEventListener(this)

    private suspend fun getCommentCount() = suspendCoroutine<Long> { continuation ->
        Firebase.database.reference.child(FIREBASE_DATABASE_COMMENTS).get().addOnSuccessListener {
            continuation.resume(it.childrenCount)
        }
    }

    private suspend fun getNickname(path: String) = suspendCoroutine<String> { continuation ->
        Firebase.database.reference.child(FIREBASE_DATABASE_USERS).child(path).child(User.NICKNAME).get().addOnSuccessListener { snapshot ->
            continuation.resume(snapshot.getValue<String>() ?: "None")
        }
    }

    private suspend fun getIconTexture(path: String) = suspendCoroutine<Texture> { continuation ->
        Firebase.storage.getReferenceFromUrl(FIREBASE_STORAGE_ICON).child(path).getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
            runGDX { continuation.resume(bytes.toTexture()) }
        }
    }

    // ---------------------------------------------------
    // Anim
    // ---------------------------------------------------

    private fun animShowMainActors() {
        bgComment.setOriginalId()
        mainActors.onEach { it.animShow(TIME_ANIM_DIALOG_ALPHA) }
    }

    private fun animHideMainActors() {
        bgComment.setNoneId()
        mainActors.onEach { it.animHide(TIME_ANIM_DIALOG_ALPHA) }
    }

}