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
import com.education.lbjt.game.screens.practicalScreen.Practical_JGearScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JMotorScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JPulleyScreen
import com.education.lbjt.game.screens.tutorialsScreen.JGearScreen
import com.education.lbjt.game.screens.tutorialsScreen.JPrismaticScreen
import com.education.lbjt.game.screens.tutorialsScreen.JRevoluteScreen
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class AScrollPanel_JMotor(
    _screen: AdvancedScreen,
    private val spriteUtil: SpriteUtil.JointMotor,
): AAbstractTutorialScrollPanel(_screen) {

    override fun VerticalGroup.addActorsOnVerticalGroup() {
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_1 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addLabel(R.string.jmotor_title_1, LabelFont.Inter_ExtraBold_50, GameColor.textRed)
        addSpace(Space._25)
        addTypingLabel(R.string.jmotor_text_1, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jmotor_text_2, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.jmotor_text_2, it) }
        addSpace(Space._25)
        addLabel(R.string.jmotor_sub_title_1, LabelFont.Inter_Regular_35, GameColor.textRed)
        addSpace(Space._25)
        addLabel(R.string.j_sub_title_mandatory, LabelFont.Inter_Regular_35, GameColor.textRed, Align.right)
        addSpace(Space._25)
        addList_TypingLabel(R.array.j_arr_mandatory, symbol = AAbstractList.Static.Symbol.Bullet)
        addSpace(Space._25)
        addLabel(R.string.j_sub_title_optional, LabelFont.Inter_Regular_35, GameColor.textRed, Align.right)
        addSpace(Space._25)
        addList_TypingLabel(R.array.jmotor_arr_1, symbol = AAbstractList.Static.Symbol.Bullet)
        addSpace(Space._25)
        addLabel(R.string.jmotor_title_2, LabelFont.Inter_ExtraBold_50, GameColor.textRed)
        addSpace(Space._25)
        addTypingLabel(R.string.j_text_test_mouse, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.j_text_test_mouse, it) }
        addSpace(Space._25)
        addTypingLabel(R.string.jmotor_text_3, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_1 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addNumberTypingLabel(Static.Number._1, R.string.jmotor_text_4, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jmotor_codepanel_1, Static.CodePanelHeight._110)
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._2, R.string.jmotor_text_5, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jmotor_codepanel_2, Static.CodePanelHeight._210)
        addSpace(Space._25)
        addTypingLabel(R.string.j_note_collide_false, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._3, R.string.jmotor_text_6, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addList_TypingLabel(R.array.jmotor_arr_2, symbol = AAbstractList.Static.Symbol.Bullet)
        addSpace(Space._25)
        addCodePanel(R.string.jmotor_codepanel_3, Static.CodePanelHeight._200)
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_2 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addTypingLabel(R.string.jmotor_text_7, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._4, R.string.jmotor_text_8, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addList_TypingLabel(R.array.jmotor_arr_3, symbol = AAbstractList.Static.Symbol.Bullet)
        addSpace(Space._25)
        addCodePanel(R.string.jmotor_codepanel_4, Static.CodePanelHeight._200)
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_3 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addTypingLabel(R.string.jmotor_text_9, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._5, R.string.jmotor_text_10, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jmotor_text_11, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addList_TypingLabel(R.array.jmotor_arr_4, symbol = AAbstractList.Static.Symbol.Bullet)
        addSpace(Space._25)
        addCodePanel(R.string.jmotor_codepanel_5, Static.CodePanelHeight._200)
        addSpace(Space._25)
        addNumberTypingLabel(Static.Number._6, R.string.jmotor_text_12, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jmotor_text_13, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jmotor_text_14, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jmotor_codepanel_6, Static.CodePanelHeight._200)
        addSpace(Space._25)
        addTypingLabel(R.string.j_note_mks, TypingLabelFontFamily.Inter_MediumBold_30) { click(R.string.j_note_mks, it) }
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_4 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addNumberTypingLabel(Static.Number._7, R.string.jmotor_text_15, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jmotor_text_16, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jmotor_codepanel_7, Static.CodePanelHeight._320)
        addSpace(Space._25)
        addTypingLabel(R.string.jmotor_text_17, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addCodePanel(R.string.jmotor_codepanel_8, Static.CodePanelHeight._210)
        addSpace(Space._25)
        addLongQuote(R.string.j_longquote_radian_сlockwise)
        addSpace(Space._25)
        addImageAnim(0.033f, spriteUtil.animVideo_5 , Animation.PlayMode.LOOP, 299f)
        addSpace(Space._80)
        addTypingLabel(R.string.jmotor_text_18, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addTypingLabel(R.string.jmotor_text_19, TypingLabelFontFamily.Inter_MediumBold_30)
        addSpace(Space._25)
        addImageAnim(0.050f, spriteUtil.mem1 , Animation.PlayMode.LOOP, 365f)
        addSpace(Space._80)
        addTypingLabel(R.string.main_source_info, LabelFont.Inter_Regular_35)
        addSpace(Space._25)
        addTypingLabel(R.string.PS_Vel_daN, LabelFont.Inter_Black_30)
        addSpace(Space._80)
        addBtnPanel(Practical_JMotorScreen::class.java.name)
        addSpace(Space._80)
        addImageAnim(0.050f, spriteUtil.mem2 , Animation.PlayMode.LOOP, 365f)
        addSpace(Space._80)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    override fun click(textId: Int, event: String) {
        when(textId) {
            R.string.jmotor_text_2, R.string.j_note_mks -> {
                navigateToGeneralInformation()
            }
            R.string.j_text_test_mouse -> {
                navigateToJMouse()
            }
        }
    }

}