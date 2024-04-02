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
import com.education.lbjt.game.screens.practicalScreen.Practical_JFrictionScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JRopeScreen
import com.education.lbjt.game.screens.tutorialsScreen.JDistanceScreen
import com.education.lbjt.game.screens.tutorialsScreen.JMouseScreen
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class AScrollPanel_JRope(
    _screen: AdvancedScreen,
    private val spriteUtil: SpriteUtil.JointRope,
): AAbstractTutorialScrollPanel(_screen) {

    override fun VerticalGroup.addActorsOnVerticalGroup() {
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_1 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addLabel(R.string.jrope_title_1, LabelFont.Inter_ExtraBold_50, GameColor.textRed)
        addSpace(Space._25)
        addTypingLabel(R.string.jrope_text_1, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jrope_text_2, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.jrope_text_2, it) }
        addSpace(Space._25)
        addLabel(R.string.jrope_sub_title_1, LabelFont.Inter_Regular_35, GameColor.textRed)
        addSpace(Space._25)
        addLabel(R.string.j_sub_title_mandatory, LabelFont.Inter_Regular_35, GameColor.textRed, Align.right)
        addSpace(Space._25)
        addList_TypingLabel(R.array.j_arr_mandatory, symbol = AAbstractList.Static.Symbol.Bullet)
        addSpace(Space._25)
        addLabel(R.string.j_sub_title_optional, LabelFont.Inter_Regular_35, GameColor.textRed, Align.right)
        addSpace(Space._25)
        addList_TypingLabel(R.array.jrope_arr_1, symbol = AAbstractList.Static.Symbol.Bullet)
        addSpace(Space._25)
        addImage(spriteUtil.I1, 325f, true)
        addSpace(Space._80)
        addLabel(R.string.jrope_title_2, LabelFont.Inter_ExtraBold_50, GameColor.textRed)
        addSpace(Space._25)
        addTypingLabel(R.string.j_text_test_mouse, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.j_text_test_mouse, it) }
        addSpace(Space._25)
        addTypingLabel(R.string.jrope_text_3, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_1 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addNumberTypingLabel(Static.Number._1, R.string.jrope_text_4, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jrope_codepanel_1, Static.CodePanelHeight._110)
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._2, R.string.jrope_text_5, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jrope_codepanel_2, Static.CodePanelHeight._210)
        addSpace(Space._25)
        addTypingLabel(R.string.jrope_text_6, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.j_text_already_run, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.j_text_already_run, it) }
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._3, R.string.j_text_configure_local_anchor_ab, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jrope_codepanel_3, Static.CodePanelHeight._320)
        addSpace(Space._25)
        addTypingLabel(R.string.j_note_mks, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.j_note_mks, it) }
        addSpace(Space._25)
        addTypingLabel(R.string.jrope_text_7, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._4, R.string.j_text_configure_maxlength, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jrope_codepanel_4, Static.CodePanelHeight._200)
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._5, R.string.jrope_text_8, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jrope_text_9, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jrope_text_10, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jrope_codepanel_5, Static.CodePanelHeight._400)
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_1 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addTypingLabel(R.string.jrope_text_11, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.jrope_text_11, it) }
        addSpace(Space._25)
        addTypingLabel(R.string.j_text_works_great, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addImageAnim(0.070f, spriteUtil.mem , Animation.PlayMode.LOOP, 365f)
        addSpace(Space._80)
        addTypingLabel(R.string.main_source_info, LabelFont.Inter_Regular_35)
        addSpace(Space._25)
        addTypingLabel(R.string.PS_Vel_daN, LabelFont.Inter_Black_30)
        addSpace(Space._80)
        addBtnPanel(Practical_JRopeScreen::class.java.name)
        addSpace(Space._80)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    override fun click(textId: Int, event: String) {
        when(textId) {
            R.string.jrope_text_2, R.string.j_text_already_run, R.string.j_note_mks -> {
                navigateToGeneralInformation()
            }
            R.string.j_text_test_mouse -> {
                navigateToJMouse()
            }
            R.string.jrope_text_11 -> {
                screen.stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) {
                    screen.game.navigationManager.navigate(JDistanceScreen::class.java.name, screen::class.java.name)
                }
            }
        }
    }

}