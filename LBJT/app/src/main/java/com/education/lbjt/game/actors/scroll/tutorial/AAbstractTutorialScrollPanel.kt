package com.education.lbjt.game.actors.scroll.tutorial

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.github.tommyettinger.textra.Font
import com.github.tommyettinger.textra.Font.FontFamily
import com.github.tommyettinger.textra.TypingAdapter
import com.github.tommyettinger.textra.TypingLabel
import com.education.lbjt.game.actors.image.AImageAnim
import com.education.lbjt.game.actors.image.AImageFrame
import com.education.lbjt.game.actors.scroll.ATutorialScrollPane
import com.education.lbjt.game.actors.scroll.HorizontalGroup
import com.education.lbjt.game.actors.scroll.VerticalGroup
import com.education.lbjt.game.actors.scroll.tutorial.actors.AAbstractList
import com.education.lbjt.game.actors.scroll.tutorial.actors.ABtnPanel
import com.education.lbjt.game.actors.scroll.tutorial.actors.ACodePanel
import com.education.lbjt.game.actors.scroll.tutorial.actors.AList_Label
import com.education.lbjt.game.actors.scroll.tutorial.actors.AList_TypingLabel
import com.education.lbjt.game.actors.scroll.tutorial.actors.ALongQuote_TypingLabel
import com.education.lbjt.game.screens.tutorialsScreen.GeneralInformationScreen
import com.education.lbjt.game.screens.tutorialsScreen.JMouseScreen
import com.education.lbjt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.disposeAll
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.region

abstract class AAbstractTutorialScrollPanel(final override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter              = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val fontInter_Black_30     = screen.fontGeneratorInter_Black.generateFont(parameter.setSize(30))
    private val fontInter_ExtraBold_50 = screen.fontGeneratorInter_ExtraBold.generateFont(parameter.setSize(50))
    private val fontInter_ExtraBold_35 = screen.fontGeneratorInter_ExtraBold.generateFont(parameter.setSize(35))
    private val fontInter_Bold_30      = screen.fontGeneratorInter_Bold.generateFont(parameter.setSize(30))
    private val fontInter_Medium_30    = screen.fontGeneratorInter_Medium.generateFont(parameter.setSize(30))
    private val fontInter_Regular_30   = screen.fontGeneratorInter_Regular.generateFont(parameter.setSize(30))
    private val fontInter_Regular_35   = screen.fontGeneratorInter_Regular.generateFont(parameter.setSize(35))
    private val fontInter_Light_30     = screen.fontGeneratorInter_Light.generateFont(parameter.setSize(30))

    // Actor
    private val verticalGroup   = VerticalGroup(screen, endGap = 145f)
    val scrollPanel             = ATutorialScrollPane(screen, verticalGroup)

    // Field
    private var thisWidth     = 0f
    private val languageUtil  = screen.game.languageUtil

    final override fun addActorsOnGroup() {
        thisWidth = width

        addActors(scrollPanel)
        scrollPanel.setSize(width, height)
        verticalGroup.addActorsOnVerticalGroup()

        //verticalGroup.debug()
        //verticalGroup.children.onEach { it.debug() }
    }

    override fun dispose() {
        super.dispose()
        disposeAll(verticalGroup)
    }

    abstract fun VerticalGroup.addActorsOnVerticalGroup()

    open fun click(textId: Int, event: String) {}

    fun VerticalGroup.addSpace(space: Static.Space) {
        addActor(Actor().also { it.setSize(thisWidth, space.space) })
    }

    fun VerticalGroup.addImage(region: TextureRegion, height: Float) {
        addActor(Image(region).also { it.setSize(thisWidth, height) })
    }

    fun VerticalGroup.addImage(texture: Texture, height: Float, isFrame: Boolean = false) {
        if (isFrame) addActor(AImageFrame(screen, texture.region).also { it.setSize(thisWidth, height) })
        else addActor(Image(texture).also { it.setSize(thisWidth, height) })
    }

    fun VerticalGroup.addImageAnim(frameDuration: Float, keyFrames: List<TextureRegion>, playMode: PlayMode, height: Float) {
        addActor(AImageAnim(screen, frameDuration, keyFrames, playMode).also { it.setSize(thisWidth, height) })
    }

    fun VerticalGroup.addLabel(stringId: Int, labelFont: Static.LabelFont, color: Color, align: Int = Align.left) {
        addActor(Label(languageUtil.getStringResource(stringId), Label.LabelStyle(labelFont.getFont(), color)).also {
            it.wrap   = true
            it.width  = thisWidth
            it.height = it.prefHeight
            it.setAlignment(align)
        })
    }

    fun VerticalGroup.addTypingLabel(stringId: Int, fontFamily: Static.TypingLabelFontFamily, triggerBlock: (event: String) -> Unit = {}) {
        addActor(TypingLabel(languageUtil.getStringResource(stringId), fontFamily.getFont()).also {
            it.wrap = true
            it.width = thisWidth
            it.height = it.prefHeight

            it.typingListener = object : TypingAdapter() {
                override fun event(event: String?) { triggerBlock(event ?: "") }
            }
        })
    }

    fun VerticalGroup.addTypingLabel(stringId: Int, labelFont: Static.LabelFont, triggerBlock: (event: String) -> Unit = {}) {
        val font = Font(labelFont.getFont())
        disposableSet.add(font)

        addActor(TypingLabel(languageUtil.getStringResource(stringId), font).also {
            it.wrap = true
            it.width = thisWidth
            it.height = it.prefHeight

            it.typingListener = object : TypingAdapter() {
                override fun event(event: String?) { triggerBlock(event ?: "") }
            }
        })
    }

    fun VerticalGroup.addNumberTypingLabel(number: Static.Number, stringId: Int, fontFamily: Static.TypingLabelFontFamily) {
        val horizontalGroup = HorizontalGroup(screen)
        val typingLbl       = TypingLabel(languageUtil.getStringResource(stringId), fontFamily.getFont())
        val numberImg       = Image(screen.game.themeUtil.assets.NUMBER_LIST[number.nIndex])
        val spaceActor      = Actor()

        typingLbl.also {
            it.wrap = true
            it.width = 612f
            it.height = it.prefHeight
        }
        numberImg.also {
            it.setSize(30f, 30f)
            it.y = typingLbl.height - 25f
        }
        spaceActor.setSize(8f, 8f)
        horizontalGroup.also {
            it.setSize(thisWidth, typingLbl.height)
            it.addActors(numberImg, spaceActor, typingLbl)
        }

        addActor(horizontalGroup)
    }

    fun VerticalGroup.addCodePanel(stringId: Int, codePanelHeight: Static.CodePanelHeight) {
        addActor(ACodePanel(screen, languageUtil.getStringResource(stringId), fontInter_Light_30).also { it.setSize(thisWidth, codePanelHeight.height) })
    }

    fun VerticalGroup.addList_Label(stringId: Int, align: AAbstractList.Static.Align = AAbstractList.Static.Align.Left, symbol: AAbstractList.Static.Symbol = AAbstractList.Static.Symbol.Bullet) {
        addActor(AList_Label(screen, fontInter_Regular_30, languageUtil.getStringArrayResources(stringId).toList(), align, symbol, fontInter_Regular_30).also { it.width = thisWidth })
    }

    fun VerticalGroup.addList_TypingLabel(stringId: Int, align: AAbstractList.Static.Align = AAbstractList.Static.Align.Left, symbol: AAbstractList.Static.Symbol = AAbstractList.Static.Symbol.Bullet, triggerBlock: (event: String) -> Unit = {}) {
        addActor(AList_TypingLabel(screen, Static.TypingLabelFontFamily.Inter_RegularBold_30.getFont(), languageUtil.getStringArrayResources(stringId).toList(), align, symbol, fontInter_Regular_30).also {
            it.width = thisWidth

            it.labels.onEach { lbl -> lbl.typingListener = object : TypingAdapter() {
                override fun event(event: String?) { triggerBlock(event ?: "") }
            } }
        })
    }

    fun VerticalGroup.addLongQuote(stringId: Int, triggerBlock: (event: String) -> Unit = {}) {
        addActor(ALongQuote_TypingLabel(screen, languageUtil.getStringResource(stringId), Static.TypingLabelFontFamily.Inter_RegularBold_30.getFont()).also {
            it.width = thisWidth

            it.quoteLbl.typingListener = object : TypingAdapter() {
                override fun event(event: String?) { triggerBlock(event ?: "") }
            }
        })
    }

    fun VerticalGroup.addBtnPanel(practicalScreenName: String) {
        addActor(ABtnPanel(screen, fontInter_ExtraBold_35, practicalScreenName).also { it.setSize(thisWidth, 90f) })
    }


    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    protected fun navigateToGeneralInformation() {
        screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
            screen.game.navigationManager.navigate(GeneralInformationScreen::class.java.name, screen::class.java.name)
        }
    }
    protected fun navigateToJMouse() {
        screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
            screen.game.navigationManager.navigate(JMouseScreen::class.java.name, screen::class.java.name)
        }
    }

    private fun Static.LabelFont.getFont(): BitmapFont = when(this) {
        Static.LabelFont.Inter_Black_30     -> fontInter_Black_30
        Static.LabelFont.Inter_ExtraBold_50 -> fontInter_ExtraBold_50
        Static.LabelFont.Inter_Regular_35   -> fontInter_Regular_35
    }

    private fun Static.TypingLabelFontFamily.getFont(): Font = Font().also { fnt ->
        when(this) {
            Static.TypingLabelFontFamily.Inter_MediumBold_30  -> fnt.setFamily(FontFamily(arrayOf(
                Font(fontInter_Medium_30).setName("Inter_Medium"), Font(fontInter_Bold_30).setName("Inter_Bold")
            )))
            Static.TypingLabelFontFamily.Inter_RegularBold_30 -> fnt.setFamily(FontFamily(arrayOf(
                Font(fontInter_Regular_30).setName("Inter_Regular"), Font(fontInter_Bold_30).setName("Inter_Bold")
            )))
        }
        disposableSet.add(fnt)
    }

    object Static {
        enum class LabelFont {
            Inter_Black_30,
            Inter_ExtraBold_50,
            Inter_Regular_35,
        }

        enum class TypingLabelFontFamily {
            Inter_MediumBold_30,
            Inter_RegularBold_30,
        }

        enum class Space(val space: Float) {
            _80(80f), _25(25f)
        }

        enum class CodePanelHeight(val height: Float) {
             _110(110f), _140(140f), _170(170f),
            _200(200f), _210(210f), _240(240f), _280(280f),
            _300(300f), _320(320f), _330(330f), _390(390f),
            _400(400f)
        }

        enum class Number(val nIndex: Int) {
            _1(0),_2(1),_3(2),_4(3),
            _5(4),_6(5),_7(6),_8(7),
            _9(8)
        }
    }

}