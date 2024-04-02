package com.education.lbjt.game.actors.scroll

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.education.lbjt.R
import com.education.lbjt.game.actors.comment.AComment
import com.education.lbjt.game.actors.comment.AVeldanComment
import com.education.lbjt.game.data.User
import com.education.lbjt.game.utils.COMMENT_COUNT
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.disposeAll
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.font.FontParameter.CharType

class ACommentScrollPanel(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        private const val MIN_TEXT_HEIGHT = 81f
    }

    override var standartW = 700f

    private val parameter = FontParameter()

    private val verticalGroup   = ACommentVerticalGroup(screen, 50f, endGap = 150f)
    private val scrollPanel     = ScrollPane(verticalGroup)
    private val testHeightLabel = Label("", Label.LabelStyle(screen.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.ALL).setSize(20)), GameColor.textGreen))
    private val commentVeldan   = AVeldanComment(screen)

    override fun addActorsOnGroup() {
        addTestHeightLabel()
        addScrollPanel()
    }

    override fun dispose() {
        super.dispose()
        disposeAll(verticalGroup)
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addTestHeightLabel() {
        addActor(testHeightLabel)
        testHeightLabel.apply {
            animHide()
            width = 471f.toStandart
            wrap = true
        }
    }

    private fun addScrollPanel() {
        addActor(scrollPanel)
        scrollPanel.setBoundsStandart(0f, 0f, width, height)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun addCommentVeldan() {
        testHeightLabel.setText(screen.game.languageUtil.getStringResource(R.string.veldan_comment))
        commentVeldan.setSize(700f, (if (testHeightLabel.prefHeight < MIN_TEXT_HEIGHT) MIN_TEXT_HEIGHT else testHeightLabel.prefHeight) + 73f)
        verticalGroup.addActor(commentVeldan)
    }

    fun addComment(user: User) {
        testHeightLabel.setText(user.comment)
        verticalGroup.addActor(AComment(screen).apply {
            setSize(700f, (if (testHeightLabel.prefHeight < MIN_TEXT_HEIGHT) MIN_TEXT_HEIGHT else testHeightLabel.prefHeight) + 73f)
            update(user)
        })
        if (verticalGroup.children.size > COMMENT_COUNT) verticalGroup.removeActorAt(0, true)
    }

    fun scrollToVeldanComment() {
        scrollPanel.velocityY = 0f
        scrollPanel.scrollY   = 0f
        commentVeldan.animRed()
    }

}