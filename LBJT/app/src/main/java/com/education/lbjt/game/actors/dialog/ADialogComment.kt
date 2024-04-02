package com.education.lbjt.game.actors.dialog

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.TextArea
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.utils.Align
import com.education.lbjt.R
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.font.FontParameter.CharType
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

class ADialogComment(override val screen: AdvancedScreen): AdvancedGroup() {

    override var standartW = 684f

    private val themeUtil   = screen.game.themeUtil

    private val parameter = FontParameter()

    private val panelImg = Image(themeUtil.assets.PANEL_WITH_LIGHT_WHITE)
    private val textArea = TextArea("", getTextFieldStyle())

    var focusBlock: (Boolean) -> Unit = {}
    val textFlow  = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    var state = Static.State.DEFAULT
        private set

    override fun addActorsOnGroup() {
        addPanelImg()
        addTextArea()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addPanelImg() {
        addActor(panelImg)
        panelImg.setBoundsStandart(0f, 0f,684f,584f)
    }

    private fun addTextArea() {
        addActor(textArea)
        textArea.apply {
            setBoundsStandart(63f, 63f,558f, 458f)
            messageText = screen.game.languageUtil.getStringResource(R.string.comment_hint)
            alignment = Align.topLeft
            addListener(object : FocusListener() { override fun keyboardFocusChanged(event: FocusEvent?, actor: Actor?, focused: Boolean) { focusBlock(focused) } })
            setTextFieldListener { _, _ -> textFlow.tryEmit(text) }
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun getTextFieldStyle() = TextField.TextFieldStyle(
        screen.fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.ALL).setSize(25)),
        GameColor.textGreen,
        NinePatchDrawable(themeUtil.assets.CURSOR),
        NinePatchDrawable(themeUtil.assets.SELECT),
        null,
    )

    fun setState(state: Static.State) {
        this.state = state
        when (state) {
            Static.State.DEFAULT    -> themeUtil.assets.PANEL_WITH_LIGHT_WHITE
            Static.State.ERROR -> themeUtil.assets.PANEL_WITH_LIGHT_RED
        }.also { ninePath: NinePatch -> panelImg.drawable = NinePatchDrawable(ninePath) }
    }

    object Static {
        enum class State {
            DEFAULT, ERROR
        }
    }

}