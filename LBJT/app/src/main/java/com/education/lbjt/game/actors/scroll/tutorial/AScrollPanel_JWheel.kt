package com.education.lbjt.game.actors.scroll.tutorial

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.utils.Align
import com.education.lbjt.R
import com.education.lbjt.game.actors.scroll.VerticalGroup
import com.education.lbjt.game.actors.scroll.tutorial.AAbstractTutorialScrollPanel.Static.Space
import com.education.lbjt.game.actors.scroll.tutorial.AAbstractTutorialScrollPanel.Static.LabelFont
import com.education.lbjt.game.actors.scroll.tutorial.AAbstractTutorialScrollPanel.Static.TypingLabelFontFamily
import com.education.lbjt.game.actors.scroll.tutorial.actors.AAbstractList
import com.education.lbjt.game.manager.util.SpriteUtil
import com.education.lbjt.game.screens.practicalScreen.Practical_JWheelScreen
import com.education.lbjt.game.screens.tutorialsScreen.JDistanceScreen
import com.education.lbjt.game.screens.tutorialsScreen.JPrismaticScreen
import com.education.lbjt.game.screens.tutorialsScreen.JRevoluteScreen
import com.education.lbjt.game.screens.tutorialsScreen.JWheelScreen
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class AScrollPanel_JWheel(
    _screen: AdvancedScreen,
    private val spriteUtil: SpriteUtil.JointWheel,
): AAbstractTutorialScrollPanel(_screen) {

    override fun VerticalGroup.addActorsOnVerticalGroup() {
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_1 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addLabel(R.string.jwheel_title_1, LabelFont.Inter_ExtraBold_50, GameColor.textRed)
        addSpace(Space._25)
        addTypingLabel(R.string.jwheel_text_1, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jwheel_text_2, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addList_TypingLabel(R.array.jwheel_arr_1, symbol = AAbstractList.Static.Symbol.Number) { click(R.array.jwheel_arr_1, it) }
        addSpace(Space._25)
        addTypingLabel(R.string.jwheel_text_3, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.jwheel_text_3, it) }
        addSpace(Space._25)
        addLabel(R.string.jwheel_sub_title_1, LabelFont.Inter_Regular_35, GameColor.textRed)
        addSpace(Space._25)
        addLabel(R.string.j_sub_title_mandatory, LabelFont.Inter_Regular_35, GameColor.textRed, Align.right)
        addSpace(Space._25)
        addList_TypingLabel(R.array.j_arr_mandatory, symbol = AAbstractList.Static.Symbol.Bullet)
        addSpace(Space._25)
        addLabel(R.string.j_sub_title_optional, LabelFont.Inter_Regular_35, GameColor.textRed, Align.right)
        addSpace(Space._25)
        addList_TypingLabel(R.array.jwheel_arr_2, symbol = AAbstractList.Static.Symbol.Bullet)
        addSpace(Space._25)
        addLongQuote(R.string.j_longquote_frequency)
        addSpace(Space._25)
        addList_TypingLabel(R.array.j_arr_damping, symbol = AAbstractList.Static.Symbol.Bullet)
        addSpace(Space._25)
        addLongQuote(R.string.j_longquote_damping)
        addSpace(Space._25)
        addImage(spriteUtil.I1, 325f, true)
        addSpace(Space._80)
        addLabel(R.string.jwheel_title_2, LabelFont.Inter_ExtraBold_50, GameColor.textRed)
        addSpace(Space._25)
        addTypingLabel(R.string.j_text_test_mouse, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.j_text_test_mouse, it) }
        addSpace(Space._25)
        addTypingLabel(R.string.j_text_turnoff_gravity, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.j_codepanel_turnoff_gravity, Static.CodePanelHeight._110)
        addSpace(Space._25)
        addTypingLabel(R.string.jwheel_text_4, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_2 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addNumberTypingLabel(Static.Number._1, R.string.jwheel_text_5, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jwheel_codepanel_1, Static.CodePanelHeight._110)
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._2, R.string.jwheel_text_6, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jwheel_codepanel_2, Static.CodePanelHeight._210)
        addSpace(Space._25)
        addTypingLabel(R.string.j_text_already_run, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.j_text_already_run, it) }
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._3, R.string.j_text_configure_local_anchor_ab, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jwheel_codepanel_3, Static.CodePanelHeight._320)
        addSpace(Space._25)
        addTypingLabel(R.string.j_note_mks, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.j_note_mks, it) }
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._4, R.string.j_text_configure_localAxisA, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jwheel_text_7, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.jwheel_text_7, it) }
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._5, R.string.j_text_configure_motor, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jwheel_text_8, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.jwheel_text_8, it) }
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._6, R.string.j_text_configure_elasticity, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jwheel_text_9, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.jwheel_text_9, it) }
        addSpace(Space._25)
        addTypingLabel(R.string.jwheel_text_10, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_1 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addTypingLabel(R.string.jwheel_text_11, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.j_text_works_great, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addImageAnim(0.060f, spriteUtil.mem , Animation.PlayMode.LOOP, 382f)
        addSpace(Space._80)
        addTypingLabel(R.string.main_source_info, LabelFont.Inter_Regular_35)
        addSpace(Space._25)
        addTypingLabel(R.string.PS_Vel_daN, LabelFont.Inter_Black_30)
        addSpace(Space._80)
        addBtnPanel(Practical_JWheelScreen::class.java.name)
        addSpace(Space._80)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    override fun click(textId: Int, event: String) {
        when(textId) {
            R.string.jwheel_text_3, R.string.j_text_already_run, R.string.j_note_mks -> {
                navigateToGeneralInformation()
            }
            R.string.j_text_test_mouse -> {
                navigateToJMouse()
            }
            R.array.jwheel_arr_1 -> {
                when(event) {
                    "prismatic_joint" -> screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                        screen.game.navigationManager.navigate(JPrismaticScreen::class.java.name, JWheelScreen::class.java.name)
                    }
                    "revolute_joint" -> screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                        screen.game.navigationManager.navigate(JRevoluteScreen::class.java.name, JWheelScreen::class.java.name)
                    }
                    "distance_joint" -> screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                        screen.game.navigationManager.navigate(JDistanceScreen::class.java.name, JWheelScreen::class.java.name)
                    }
                }
            }
            R.string.jwheel_text_7 -> screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                screen.game.navigationManager.navigate(JPrismaticScreen::class.java.name, JWheelScreen::class.java.name)
            }
            R.string.jwheel_text_8 -> screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                screen.game.navigationManager.navigate(JRevoluteScreen::class.java.name, JWheelScreen::class.java.name)
            }
            R.string.jwheel_text_9 -> screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                screen.game.navigationManager.navigate(JDistanceScreen::class.java.name, JWheelScreen::class.java.name)
            }
        }
    }

}