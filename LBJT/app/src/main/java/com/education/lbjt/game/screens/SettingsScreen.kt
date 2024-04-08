package com.education.lbjt.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.education.lbjt.R
import com.education.lbjt.game.LibGDXGame
import com.education.lbjt.game.actors.checkbox.ACheckBox
import com.education.lbjt.game.actors.label.AutoLanguageSpinningLabel
import com.education.lbjt.game.box2d.WorldUtil
import com.education.lbjt.game.box2d.bodies.BLanguage
import com.education.lbjt.game.box2d.bodiesGroup.BGBorders
import com.education.lbjt.game.box2d.bodiesGroup.BGFrameLanguage
import com.education.lbjt.game.box2d.bodiesGroup.BGLanguage
import com.education.lbjt.game.box2d.bodiesGroup.BGVolume
import com.education.lbjt.game.box2d.destroyAll
import com.education.lbjt.game.box2d.util.CheckGroupBGLanguage
import com.education.lbjt.game.manager.AudioManager
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.advanced.AdvancedMouseScreen
import com.education.lbjt.game.utils.advanced.AdvancedStage
import com.education.lbjt.game.utils.font.FontParameter
import com.education.lbjt.game.utils.font.FontParameter.CharType
import com.education.lbjt.game.utils.language.Language
import com.education.lbjt.game.utils.region
import com.education.lbjt.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class SettingsScreen(override val game: LibGDXGame): AdvancedMouseScreen(game) {

    companion object {
        var percentVolumeMusic = -1
        var percentVolumeSound = -1
    }

    private val parameter = FontParameter()

    // Actor
    private val aLanguageLbl = AutoLanguageSpinningLabel(this, R.string.language, Label.LabelStyle(fontGeneratorInter_ExtraBold.generateFont(parameter.setCharacters(CharType.LATIN_CYRILLIC).setSize(50)), GameColor.textGreen))
    private val aDebugBox    = ACheckBox(this, ACheckBox.Static.Type.DEBUG)

    // BodyGroup
    private val bgBorders       = BGBorders(this)
    private val bgVolumeMusic   = BGVolume(this, R.string.music, if (percentVolumeMusic != -1) percentVolumeMusic else AudioManager.volumeLevelPercent.roundToInt())
    private val bgVolumeSound   = BGVolume(this, R.string.sound, if (percentVolumeSound != -1) percentVolumeSound else AudioManager.volumeLevelPercent.roundToInt())
    private val bgFrameLanguage = BGFrameLanguage(this)
    private val bgLanguageEN    = BGLanguage(this, BLanguage.Static.Type.EN)
    private val bgLanguageUK    = BGLanguage(this, BLanguage.Static.Type.UK)



    override fun show() {
        stageUI.root.animHide()
        setUIBackground(game.themeUtil.assets.BACKGROUND.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addLanguageLbl()
        addDebugBox()

        createBG_Borders()
        createBG_Volume()
        createBG_FrameLanguage()
        createBG_Language()

        asyncCollectPercentVolumeFlow()
        bgFrameLanguage.impulseFrameLanguage()
        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

    override fun dispose() {
        listOf(
            bgBorders, bgVolumeMusic, bgVolumeSound,
            bgFrameLanguage, bgLanguageEN, bgLanguageUK,
        ).destroyAll()

        super.dispose()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addLanguageLbl() {
        addActor(aLanguageLbl)
        aLanguageLbl.setBounds(10f, 559f, 337f, 65f)
    }

    private fun AdvancedStage.addDebugBox() {
        addActor(aDebugBox)
        aDebugBox.apply {
            setBounds(302f, 186f, 96f, 92f)
            if (WorldUtil.isDebug) check(false) else uncheck(false)
            setOnCheckListener { isCheck ->
                disable()
                WorldUtil.isDebug = isCheck
                coroutine?.launch {
                    game.soundUtil.apply { play(ELECTRICITY) }
                    delay(1000)
                    runGDX { enable() }
                }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Create Body Group
    // ------------------------------------------------------------------------

    private fun createBG_Borders() {
        bgBorders.create(0f,0f,700f,1400f)
    }

    private fun createBG_Volume() {
        bgVolumeMusic.create(30f,691f,314f,630f)
        bgVolumeSound.create(357f,691f,314f,630f)
    }

    private fun createBG_FrameLanguage() {
        bgFrameLanguage.create(357f,540f,314f,102f)
    }

    private fun createBG_Language() {
        val checkGroupBGLanguage = CheckGroupBGLanguage()

        bgLanguageEN.apply {
            this.checkGroupBGLanguage = checkGroupBGLanguage
            setOnCheckBlock { if (it) game.languageUtil.languageFlow.value = Language.EN }
            create(413f,0f,202f,498f)
        }
        bgLanguageUK.apply {
            this.checkGroupBGLanguage = checkGroupBGLanguage
            setOnCheckBlock { if (it) game.languageUtil.languageFlow.value = Language.UK }
            create(86f,0f,202f,498f)
        }

        when(game.languageUtil.languageFlow.value) {
            Language.EN -> {
                checkGroupBGLanguage.currentCheckedBGLanguage = bgLanguageEN
                bgLanguageEN.check(false)
                bgLanguageUK.uncheck(false)
            }
            Language.UK -> {
                checkGroupBGLanguage.currentCheckedBGLanguage = bgLanguageUK
                bgLanguageUK.check(false)
                bgLanguageEN.uncheck(false)
            }
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun asyncCollectPercentVolumeFlow() {
        coroutine?.launch(Dispatchers.Default) {
            launch { bgVolumeMusic.percentVolumeFlow.collect { percent ->
                percentVolumeMusic = percent
                game.musicUtil.volumeLevelFlow.value = percent / 100f
            } }
            launch { bgVolumeSound.percentVolumeFlow.collect { percent ->
                percentVolumeSound = percent
                game.soundUtil.volumeLevel = percent / 100f
            } }
        }
    }

}