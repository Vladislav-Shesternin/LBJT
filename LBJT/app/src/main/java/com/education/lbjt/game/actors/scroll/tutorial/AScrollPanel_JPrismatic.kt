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
import com.education.lbjt.game.screens.practicalScreen.Practical_JPrismaticScreen
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class AScrollPanel_JPrismatic(
    _screen: AdvancedScreen,
    private val spriteUtil: SpriteUtil.JointPrismatic,
): AAbstractTutorialScrollPanel(_screen) {

    override fun VerticalGroup.addActorsOnVerticalGroup() {
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_1 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addLabel(R.string.jprismatic_title_1, LabelFont.Inter_ExtraBold_50, GameColor.textRed)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_1, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_2, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.jprismatic_text_2, it) }
        addSpace(Space._25)
        addLabel(R.string.jprismatic_sub_title_1, LabelFont.Inter_Regular_35, GameColor.textRed)
        addSpace(Space._25)
        addLabel(R.string.j_sub_title_mandatory, LabelFont.Inter_Regular_35, GameColor.textRed, Align.right)
        addSpace(Space._25)
        addList_TypingLabel(R.array.j_arr_mandatory, symbol = AAbstractList.Static.Symbol.Bullet)
        addSpace(Space._25)
        addLabel(R.string.j_sub_title_optional, LabelFont.Inter_Regular_35, GameColor.textRed, Align.right)
        addSpace(Space._25)
        addList_TypingLabel(R.array.jprismatic_arr_1, symbol = AAbstractList.Static.Symbol.Bullet)
        addSpace(Space._25)
        addImage(spriteUtil.I1, 325f, true)
        addSpace(Space._80)
        addLabel(R.string.jprismatic_title_2, LabelFont.Inter_ExtraBold_50, GameColor.textRed)
        addSpace(Space._25)
        addTypingLabel(R.string.j_text_test_mouse, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.j_text_test_mouse, it) }
        addSpace(Space._25)
        addTypingLabel(R.string.j_text_turnoff_gravity, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.j_codepanel_turnoff_gravity, Static.CodePanelHeight._110)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_3, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_2 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addNumberTypingLabel(Static.Number._1, R.string.jprismatic_text_4, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jprismatic_codepanel_1, Static.CodePanelHeight._110)
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._2, R.string.jprismatic_text_5, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jprismatic_codepanel_2, Static.CodePanelHeight._210)
        addSpace(Space._25)
        addTypingLabel(R.string.j_text_already_run, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.j_text_already_run, it) }
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._3, R.string.j_text_configure_local_anchor_ab, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jprismatic_codepanel_3, Static.CodePanelHeight._320)
        addSpace(Space._25)
        addTypingLabel(R.string.j_note_mks, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.j_note_mks, it) }
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._4, R.string.j_text_configure_localAxisA, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_6, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addLongQuote(R.string.jprismatic_longquote_1)
        addSpace(Space._25)
        addImage(spriteUtil.I2, 325f, true)
        addSpace(Space._80)
        addTypingLabel(R.string.jprismatic_text_7, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_8, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_9, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_10, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addImage(spriteUtil.I3, 325f, true)
        addSpace(Space._80)
        addLongQuote(R.string.jprismatic_longquote_2)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_11, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addLabel(R.string.jprismatic_sub_title_2, LabelFont.Inter_Regular_35, GameColor.textRed)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_12, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_3 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addImage(spriteUtil.I4, 325f, true)
        addSpace(Space._80)
        addTypingLabel(R.string.jprismatic_note_1, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jprismatic_codepanel_4, Static.CodePanelHeight._210)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_13, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_14, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addLongQuote(R.string.j_longquote_radians)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_15, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jprismatic_codepanel_5, Static.CodePanelHeight._320)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_16, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jprismatic_codepanel_6, Static.CodePanelHeight._200)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_note_2, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._5, R.string.j_text_configure_reference_angle, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_17, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_18, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jprismatic_codepanel_7, Static.CodePanelHeight._400)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_19, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_20, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addLongQuote(R.string.j_longquote_radian_сlockwise)
        addSpace(Space._25)
        addImage(spriteUtil.I5, 325f, true)
        addSpace(Space._80)
        addCodePanel(R.string.jprismatic_codepanel_8, Static.CodePanelHeight._170)
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._6, R.string.j_text_configure_limit, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_21, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jprismatic_codepanel_9, Static.CodePanelHeight._200)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_text_22, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.j_note_mks, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.j_note_mks, it) }
        addSpace(Space._25)
        addTypingLabel(R.string.j_text_why_limit, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jprismatic_codepanel_10, Static.CodePanelHeight._240)
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_4 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addList_TypingLabel(R.array.jprismatic_arr_2, symbol = AAbstractList.Static.Symbol.Bullet)
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._7, R.string.j_text_configure_motor, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jprismatic_codepanel_11, Static.CodePanelHeight._200)
        addSpace(Space._25)
        addList_TypingLabel(R.array.jprismatic_arr_3, symbol = AAbstractList.Static.Symbol.Bullet)
        addSpace(Space._25)
        addTypingLabel(R.string.jprismatic_note_3, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.j_text_why_motor, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jprismatic_codepanel_12, Static.CodePanelHeight._240)
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_5 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addTypingLabel(R.string.jprismatic_text_23, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.j_text_works_great, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addImageAnim(0.060f, spriteUtil.mem , Animation.PlayMode.LOOP, 520f)
        addSpace(Space._80)
        addTypingLabel(R.string.main_source_info, LabelFont.Inter_Regular_35)
        addSpace(Space._25)
        addTypingLabel(R.string.PS_Vel_daN, LabelFont.Inter_Black_30)
        addSpace(Space._80)
        addBtnPanel(Practical_JPrismaticScreen::class.java.name)
        addSpace(Space._80)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    override fun click(textId: Int, event: String) {
        when(textId) {
            R.string.jprismatic_text_2, R.string.j_text_already_run, R.string.j_note_mks -> {
                navigateToGeneralInformation()
            }
            R.string.j_text_test_mouse -> {
                navigateToJMouse()
            }
        }
    }

}