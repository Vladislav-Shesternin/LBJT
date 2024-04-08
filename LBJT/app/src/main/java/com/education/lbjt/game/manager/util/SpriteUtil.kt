package com.education.lbjt.game.manager.util

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.education.lbjt.game.manager.SpriteManager
import com.education.lbjt.game.manager.SpriteManager.EnumTexture_GeneralInformation as GenInfo
import com.education.lbjt.game.manager.SpriteManager.EnumTexture_JointMouse as JMouse
import com.education.lbjt.game.manager.SpriteManager.EnumTexture_JointDistance as JDistance
import com.education.lbjt.game.manager.SpriteManager.EnumTexture_JointRevolute as JRevolute
import com.education.lbjt.game.manager.SpriteManager.EnumTexture_JointPrismatic as JPrismatic
import com.education.lbjt.game.manager.SpriteManager.EnumTexture_JointWheel as JWheel
import com.education.lbjt.game.manager.SpriteManager.EnumTexture_JointWeld as JWeld
import com.education.lbjt.game.manager.SpriteManager.EnumTexture_JointFriction as JFriction
import com.education.lbjt.game.manager.SpriteManager.EnumTexture_JointRope as JRope
import com.education.lbjt.game.manager.SpriteManager.EnumTexture_JointPulley as JPulley
import com.education.lbjt.game.manager.SpriteManager.EnumTexture_JointGear as JGear

class SpriteUtil {

     interface AllAssets {
          // game1
          val CIRCLE_BLUE                  : TextureRegion
          val FRAME_LANGUAGE               : TextureRegion
          val HAND_HELLO                   : TextureRegion
          val HAND_HINT                    : TextureRegion
          val LANGUAGE_EN                  : TextureRegion
          val LANGUAGE_UK                  : TextureRegion
          val REGULAR_BTN_DEF              : TextureRegion
          val REGULAR_BTN_PRESS            : TextureRegion
          val REGULAR_BTN_DISABLE          : TextureRegion
          val ROD_LOUDER                   : TextureRegion
          val ROD_QUIET                    : TextureRegion
          val USER                         : TextureRegion
          val VOLUME_LOUDER                : TextureRegion
          val VOLUME_QUIET                 : TextureRegion
          val YAN                          : TextureRegion
          val YIN_YAN_LIGHT                : TextureRegion
          val YIN                          : TextureRegion
          val ADS                          : TextureRegion
          val DESCRIPTION_PANEL            : TextureRegion
          val EL_DAN                       : TextureRegion
          val HAND_V                       : TextureRegion
          val MONETIZATION_BTN_DEF         : TextureRegion
          val MONETIZATION_BTN_PRESS       : TextureRegion
          val PS                           : TextureRegion
          val THANKS_FRAME                 : TextureRegion
          val DEBUG_BOX_DEF                : TextureRegion
          val DEBUG_BOX_CHECK              : TextureRegion
          val LOADER                       : TextureRegion
          val NO_WIFI                      : TextureRegion
          val FRAME_ICON                   : TextureRegion
          val ICON_DEF                     : TextureRegion
          val FRAME_ICON_EMPTY             : TextureRegion
          val C_DYNAMIC                    : TextureRegion
          val C_KINEMATIC                  : TextureRegion
          val C_STATIC                     : TextureRegion
          val H_DYNAMIC                    : TextureRegion
          val H_KINEMATIC                  : TextureRegion
          val H_STATIC                     : TextureRegion
          val V_DYNAMIC                    : TextureRegion
          val V_KINEMATIC                  : TextureRegion
          val V_STATIC                     : TextureRegion
          val LIFT_PLATFORM                : TextureRegion
          val LIFT_GEAR                    : TextureRegion
          val PRACTICAL_BTN                : TextureRegion
          val PRACTICAL_SETTINGS           : TextureRegion
          val PRACTICAL_DONE               : TextureRegion
          val PRACTICAL_PROGRESS_ARM       : TextureRegion
          val PRACTICAL_PROGRESS           : TextureRegion
          val PRACTICAL_PROGRESS_BACKGROUND: TextureRegion
          val PRACTICAL_PROGRESS_POINT     : TextureRegion
          val ANCHOR_POINT                 : TextureRegion
          val PRACTICAL_FALSE              : TextureRegion
          val PRACTICAL_TRUE               : TextureRegion
          val RESET_DEF                    : TextureRegion
          val RESET_PRESS                  : TextureRegion
          val UPDATE_BTN_DEF               : TextureRegion
          val UPDATE_BTN_PRESS             : TextureRegion
          val UPDATE_LIGHT                 : TextureRegion
          val UPDATE_X_DEF                 : TextureRegion
          val UPDATE_X_PRESS               : TextureRegion
          val TELEGRAM                     : TextureRegion
          val RATIO_H                      : TextureRegion
          val RATIO_V                      : TextureRegion
          // game2
          val PRACTICAL_DEGREES : TextureRegion
          val PRACTICAL_LINES   : TextureRegion

          val NUMBER_LIST : List<TextureRegion>

          val PANEL                  : NinePatch
          val CURSOR                 : NinePatch
          val SELECT                 : NinePatch
          val PANEL_WITH_LIGHT_WHITE : NinePatch
          val PANEL_WITH_LIGHT_RED   : NinePatch
          val BORDERS_BLUE           : NinePatch
          val PANEL_CODE             : NinePatch
          val PRACTICAL_FRAME_WHITE  : NinePatch

          val BACKGROUND  : Texture
          val MASK_ICON   : Texture
          val ICON_VELDAN : Texture
          val UPDATE_PANEL: Texture

          val PRACTICAL_PROGRESS_MASK: Texture
     }

     abstract class CommonAssets: AllAssets {
          protected fun getGame1Region(name: String): TextureRegion = SpriteManager.EnumAtlas.GAME1.data.atlas.findRegion(name)
          protected fun getGame2Region(name: String): TextureRegion = SpriteManager.EnumAtlas.GAME2.data.atlas.findRegion(name)
          protected fun getGamePath(name: String): NinePatch = SpriteManager.EnumAtlas.GAME1.data.atlas.createPatch(name)

          // game1
          override val CIRCLE_BLUE                   = getGame1Region("circle_blue")
          override val FRAME_LANGUAGE                = getGame1Region("frame_language")
          override val HAND_HELLO                    = getGame1Region("hand_hello")
          override val HAND_HINT                     = getGame1Region("hand_hint")
          override val LANGUAGE_EN                   = getGame1Region("language_en")
          override val LANGUAGE_UK                   = getGame1Region("language_uk")
          override val REGULAR_BTN_DEF               = getGame1Region("regular_btn_def")
          override val REGULAR_BTN_PRESS             = getGame1Region("regular_btn_press")
          override val REGULAR_BTN_DISABLE           = getGame1Region("regular_btn_disable")
          override val ROD_LOUDER                    = getGame1Region("rod_louder")
          override val ROD_QUIET                     = getGame1Region("rod_quiet")
          override val USER                          = getGame1Region("user")
          override val VOLUME_LOUDER                 = getGame1Region("volume_louder")
          override val VOLUME_QUIET                  = getGame1Region("volume_quiet")
          override val YAN                           = getGame1Region("yan")
          override val YIN_YAN_LIGHT                 = getGame1Region("yin_yan_light")
          override val YIN                           = getGame1Region("yin")
          override val ADS                           = getGame1Region("ads")
          override val DESCRIPTION_PANEL             = getGame1Region("description_panel")
          override val EL_DAN                        = getGame1Region("el_dan")
          override val HAND_V                        = getGame1Region("hand_v")
          override val MONETIZATION_BTN_DEF          = getGame1Region("monetization_btn_def")
          override val MONETIZATION_BTN_PRESS        = getGame1Region("monetization_btn_press")
          override val PS                            = getGame1Region("ps")
          override val THANKS_FRAME                  = getGame1Region("thanks_frame")
          override val DEBUG_BOX_DEF                 = getGame1Region("debug_box_def")
          override val DEBUG_BOX_CHECK               = getGame1Region("debug_box_check")
          override val LOADER                        = getGame1Region("loader")
          override val NO_WIFI                       = getGame1Region("no_wifi")
          override val FRAME_ICON                    = getGame1Region("frame_icon")
          override val ICON_DEF                      = getGame1Region("icon_def")
          override val FRAME_ICON_EMPTY              = getGame1Region("frame_icon_empty")
          override val C_DYNAMIC                     = getGame1Region("c_dynamic")
          override val C_KINEMATIC                   = getGame1Region("c_kinematic")
          override val C_STATIC                      = getGame1Region("c_static")
          override val H_DYNAMIC                     = getGame1Region("h_dynamic")
          override val H_KINEMATIC                   = getGame1Region("h_kinematic")
          override val H_STATIC                      = getGame1Region("h_static")
          override val V_DYNAMIC                     = getGame1Region("v_dynamic")
          override val V_KINEMATIC                   = getGame1Region("v_kinematic")
          override val V_STATIC                      = getGame1Region("v_static")
          override val LIFT_PLATFORM                 = getGame1Region("lift_platform")
          override val LIFT_GEAR                     = getGame1Region("lift_gear")
          override val PRACTICAL_BTN                 = getGame1Region("practical_btn")
          override val PRACTICAL_SETTINGS            = getGame1Region("practical_settings")
          override val PRACTICAL_DONE                = getGame1Region("practical_done")
          override val PRACTICAL_PROGRESS_ARM        = getGame1Region("practical_progress_arm")
          override val PRACTICAL_PROGRESS            = getGame1Region("practical_progress")
          override val PRACTICAL_PROGRESS_BACKGROUND = getGame1Region("practical_progress_background")
          override val PRACTICAL_PROGRESS_POINT      = getGame1Region("practical_progress_point")
          override val ANCHOR_POINT                  = getGame1Region("anchor_point")
          override val PRACTICAL_FALSE               = getGame1Region("practical_false")
          override val PRACTICAL_TRUE                = getGame1Region("practical_true")
          override val RESET_DEF                     = getGame1Region("reset_def")
          override val RESET_PRESS                   = getGame1Region("reset_press")
          override val UPDATE_BTN_DEF                = getGame1Region("update_btn_def")
          override val UPDATE_BTN_PRESS              = getGame1Region("update_btn_press")
          override val UPDATE_LIGHT                  = getGame1Region("update_light")
          override val UPDATE_X_DEF                  = getGame1Region("update_x_def")
          override val UPDATE_X_PRESS                = getGame1Region("update_x_press")
          override val TELEGRAM                      = getGame1Region("telegram")
          override val RATIO_H                       = getGame1Region("ratio_h")
          override val RATIO_V                       = getGame1Region("ratio_v")
          // game 2
          override val PRACTICAL_DEGREES = getGame2Region("practical_degrees")
          override val PRACTICAL_LINES   = getGame2Region("practical_lines")

          override val NUMBER_LIST = List(9) { getGame1Region("number ${it.inc()}") }

          override val PANEL                  = getGamePath("panel")
          override val CURSOR                 = getGamePath("cursor")
          override val SELECT                 = getGamePath("select")
          override val PANEL_WITH_LIGHT_WHITE = getGamePath("panel_with_light_white")
          override val PANEL_WITH_LIGHT_RED   = getGamePath("panel_with_light_red")
          override val BORDERS_BLUE           = getGamePath("borders_blue")
          override val PANEL_CODE             = getGamePath("panel_code")
          override val PRACTICAL_FRAME_WHITE  = getGamePath("practical_frame_white")

          override val MASK_ICON   = SpriteManager.EnumTexture.MASK_ICON.data.texture
          override val ICON_VELDAN = SpriteManager.EnumTexture.VELDAN_ICON.data.texture

          override val PRACTICAL_PROGRESS_MASK = SpriteManager.EnumTexture.PRACTICAL_PROGRESS_MASK.data.texture
          override val UPDATE_PANEL            = SpriteManager.EnumTexture.UPDATE_PANEL.data.texture
     }

     class YanAssets: CommonAssets() {
          override val BACKGROUND = SpriteManager.EnumTexture.YAN_BACKGROUND.data.texture
     }

     class YinAssets: CommonAssets() {
          override val BACKGROUND = SpriteManager.EnumTexture.YIN_BACKGROUND.data.texture
     }

     // ---------------------------------------------------
     // Tutorials
     // ---------------------------------------------------

     class GeneralInformation {
          private fun getRegion(name: String, atlasData: SpriteManager.AtlasData): TextureRegion = atlasData.atlas.findRegion(name)

          val animObama = List<TextureRegion>(63) { getRegion("anim_obama (${it.inc()})", SpriteManager.EnumAtlas_GeneralInformation.ANIM_OBAMA.data) }

          val I1  = GenInfo.I1.data.texture
          val I2  = GenInfo.I2.data.texture
          val I3  = GenInfo.I3.data.texture
          val I4  = GenInfo.I4.data.texture
          val I5  = GenInfo.I5.data.texture
          val I6  = GenInfo.I6.data.texture
          val I7  = GenInfo.I7.data.texture
          val I8  = GenInfo.I8.data.texture
          val I9  = GenInfo.I9.data.texture
          val I10 = GenInfo.I10.data.texture
          val I11 = GenInfo.I11.data.texture
     }

     class JointMouse: TutorialsAssets {
          private fun getRegion(name: String, atlasData: SpriteManager.AtlasData): TextureRegion = atlasData.atlas.findRegion(name)

          private val animVideo_1_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_1_0.data) }
          private val animVideo_1_1 = List<TextureRegion>(78) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_1_1.data) }
          private val animVideo_1_2 = List<TextureRegion>(78) { getRegion("video (${it.inc()+156})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_1_2.data) }
          private val animVideo_1_3 = List<TextureRegion>(27) { getRegion("video (${it.inc()+234})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_1_3.data) }

          private val animVideo_2_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_2_0.data) }
          private val animVideo_2_1 = List<TextureRegion>(78) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_2_1.data) }
          private val animVideo_2_2 = List<TextureRegion>(78) { getRegion("video (${it.inc()+156})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_2_2.data) }
          private val animVideo_2_3 = List<TextureRegion>(78) { getRegion("video (${it.inc()+234})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_2_3.data) }
          private val animVideo_2_4 = List<TextureRegion>(78) { getRegion("video (${it.inc()+312})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_2_4.data) }
          private val animVideo_2_5 = List<TextureRegion>(58) { getRegion("video (${it.inc()+390})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_2_5.data) }

          private val animVideo_3_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_3_0.data) }
          private val animVideo_3_1 = List<TextureRegion>(78) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_3_1.data) }
          private val animVideo_3_2 = List<TextureRegion>(78) { getRegion("video (${it.inc()+156})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_3_2.data) }
          private val animVideo_3_3 = List<TextureRegion>(78) { getRegion("video (${it.inc()+234})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_3_3.data) }
          private val animVideo_3_4 = List<TextureRegion>(78) { getRegion("video (${it.inc()+312})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_3_4.data) }
          private val animVideo_3_5 = List<TextureRegion>(78) { getRegion("video (${it.inc()+390})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_3_5.data) }
          private val animVideo_3_6 = List<TextureRegion>(18) { getRegion("video (${it.inc()+468})", SpriteManager.EnumAtlas_JointMouse.ANIM_VIDEO_3_6.data) }

          private val mem_1 = List<TextureRegion>(78) { getRegion("mem (${it.inc()})", SpriteManager.EnumAtlas_JointMouse.MEM_1.data) }
          private val mem_2 = List<TextureRegion>(35) { getRegion("mem (${it.inc()+78})", SpriteManager.EnumAtlas_JointMouse.MEM_2.data) }

          val animVideo_1 = animVideo_1_0+animVideo_1_1+animVideo_1_2+animVideo_1_3
          val animVideo_2 = animVideo_2_0+animVideo_2_1+animVideo_2_2+animVideo_2_3+animVideo_2_4+animVideo_2_5
          val animVideo_3 = animVideo_3_0+animVideo_3_1+animVideo_3_2+animVideo_3_3+animVideo_3_4+animVideo_3_5+animVideo_3_6

          val mem = mem_1+mem_2

          val I1  = JMouse.I1.data.texture
     }

     class JointDistance: TutorialsAssets {
          private fun getRegion(name: String, atlasData: SpriteManager.AtlasData): TextureRegion = atlasData.atlas.findRegion(name)

          private val animVideo_1_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointDistance.ANIM_VIDEO_1_0.data) }
          private val animVideo_1_1 = List<TextureRegion>(78) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointDistance.ANIM_VIDEO_1_1.data) }
          private val animVideo_1_2 = List<TextureRegion>(31) { getRegion("video (${it.inc()+156})", SpriteManager.EnumAtlas_JointDistance.ANIM_VIDEO_1_2.data) }

          private val animVideo_2_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointDistance.ANIM_VIDEO_2_0.data) }
          private val animVideo_2_1 = List<TextureRegion>(78) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointDistance.ANIM_VIDEO_2_1.data) }
          private val animVideo_2_2 = List<TextureRegion>(45) { getRegion("video (${it.inc()+156})", SpriteManager.EnumAtlas_JointDistance.ANIM_VIDEO_2_2.data) }

          private val animVideo_3_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointDistance.ANIM_VIDEO_3_0.data) }
          private val animVideo_3_1 = List<TextureRegion>(78) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointDistance.ANIM_VIDEO_3_1.data) }
          private val animVideo_3_2 = List<TextureRegion>(78) { getRegion("video (${it.inc()+156})", SpriteManager.EnumAtlas_JointDistance.ANIM_VIDEO_3_2.data) }
          private val animVideo_3_3 = List<TextureRegion>(35) { getRegion("video (${it.inc()+234})", SpriteManager.EnumAtlas_JointDistance.ANIM_VIDEO_3_3.data) }

          private val mem_1 = List<TextureRegion>(83) { getRegion("mem (${it.inc()})", SpriteManager.EnumAtlas_JointDistance.MEM_1.data) }
          private val mem_2 = List<TextureRegion>(29) { getRegion("mem (${it.inc()+83})", SpriteManager.EnumAtlas_JointDistance.MEM_2.data) }

          val animVideo_1 = animVideo_1_0+animVideo_1_1+animVideo_1_2
          val animVideo_2 = animVideo_2_0+animVideo_2_1+animVideo_2_2
          val animVideo_3 = animVideo_3_0+animVideo_3_1+animVideo_3_2+animVideo_3_3

          val mem = mem_1+mem_2

          val I1 = JDistance.I1.data.texture
          val I2 = JDistance.I2.data.texture
     }

     class JointRevolute: TutorialsAssets {
          private fun getRegion(name: String, atlasData: SpriteManager.AtlasData): TextureRegion = atlasData.atlas.findRegion(name)

          private val animVideo_1_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointRevolute.ANIM_VIDEO_1_0.data) }
          private val animVideo_1_1 = List<TextureRegion>(78) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointRevolute.ANIM_VIDEO_1_1.data) }
          private val animVideo_1_2 = List<TextureRegion>(78) { getRegion("video (${it.inc()+156})", SpriteManager.EnumAtlas_JointRevolute.ANIM_VIDEO_1_2.data) }
          private val animVideo_1_3 = List<TextureRegion>(13) { getRegion("video (${it.inc()+234})", SpriteManager.EnumAtlas_JointRevolute.ANIM_VIDEO_1_3.data) }

          private val animVideo_2_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointRevolute.ANIM_VIDEO_2_0.data) }
          private val animVideo_2_1 = List<TextureRegion>(61) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointRevolute.ANIM_VIDEO_2_1.data) }

          private val animVideo_3_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointRevolute.ANIM_VIDEO_3_0.data) }
          private val animVideo_3_1 = List<TextureRegion>(12) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointRevolute.ANIM_VIDEO_3_1.data) }

          private val animVideo_4_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointRevolute.ANIM_VIDEO_4_0.data) }
          private val animVideo_4_1 = List<TextureRegion>(78) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointRevolute.ANIM_VIDEO_4_1.data) }
          private val animVideo_4_2 = List<TextureRegion>(78) { getRegion("video (${it.inc()+156})", SpriteManager.EnumAtlas_JointRevolute.ANIM_VIDEO_4_2.data) }
          private val animVideo_4_3 = List<TextureRegion>(1) { getRegion("video (${it.inc()+234})", SpriteManager.EnumAtlas_JointRevolute.ANIM_VIDEO_4_3.data) }

          private val mem_1 = List<TextureRegion>(25) { getRegion("mem (${it.inc()})", SpriteManager.EnumAtlas_JointRevolute.MEM_1.data) }
          private val mem_2 = List<TextureRegion>(25) { getRegion("mem (${it.inc()+25})", SpriteManager.EnumAtlas_JointRevolute.MEM_2.data) }

          val animVideo_1 = animVideo_1_0+animVideo_1_1+animVideo_1_2+animVideo_1_3
          val animVideo_2 = animVideo_2_0+animVideo_2_1
          val animVideo_3 = animVideo_3_0+animVideo_3_1
          val animVideo_4 = animVideo_4_0+animVideo_4_1+animVideo_4_2+animVideo_4_3

          val mem = mem_1+mem_2

          val I1 = JRevolute.I1.data.texture
          val I2 = JRevolute.I2.data.texture
     }

     class JointPrismatic: TutorialsAssets {
          private fun getRegion(name: String, atlasData: SpriteManager.AtlasData): TextureRegion = atlasData.atlas.findRegion(name)

          private val animVideo_1_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointPrismatic.ANIM_VIDEO_1_0.data) }
          private val animVideo_1_1 = List<TextureRegion>(78) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointPrismatic.ANIM_VIDEO_1_1.data) }
          private val animVideo_1_2 = List<TextureRegion>(7) { getRegion("video (${it.inc()+156})", SpriteManager.EnumAtlas_JointPrismatic.ANIM_VIDEO_1_2.data) }

          private val animVideo_2_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointPrismatic.ANIM_VIDEO_2_0.data) }
          private val animVideo_2_1 = List<TextureRegion>(20) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointPrismatic.ANIM_VIDEO_2_1.data) }

          private val animVideo_3_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointPrismatic.ANIM_VIDEO_3_0.data) }
          private val animVideo_3_1 = List<TextureRegion>(13) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointPrismatic.ANIM_VIDEO_3_1.data) }

          private val animVideo_4_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointPrismatic.ANIM_VIDEO_4_0.data) }
          private val animVideo_4_1 = List<TextureRegion>(24) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointPrismatic.ANIM_VIDEO_4_1.data) }

          private val animVideo_5_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointPrismatic.ANIM_VIDEO_5_0.data) }
          private val animVideo_5_1 = List<TextureRegion>(78) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointPrismatic.ANIM_VIDEO_5_1.data) }
          private val animVideo_5_2 = List<TextureRegion>(49) { getRegion("video (${it.inc()+156})", SpriteManager.EnumAtlas_JointPrismatic.ANIM_VIDEO_5_2.data) }

          private val mem_1 = List<TextureRegion>(25) { getRegion("mem (${it.inc()})", SpriteManager.EnumAtlas_JointPrismatic.MEM_1.data) }

          val animVideo_1 = animVideo_1_0+animVideo_1_1+animVideo_1_2
          val animVideo_2 = animVideo_2_0+animVideo_2_1
          val animVideo_3 = animVideo_3_0+animVideo_3_1
          val animVideo_4 = animVideo_4_0+animVideo_4_1
          val animVideo_5 = animVideo_5_0+animVideo_5_1+animVideo_5_2

          val mem = mem_1

          val I1 = JPrismatic.I1.data.texture
          val I2 = JPrismatic.I2.data.texture
          val I3 = JPrismatic.I3.data.texture
          val I4 = JPrismatic.I4.data.texture
          val I5 = JPrismatic.I5.data.texture
     }

     class JointWheel: TutorialsAssets {
          private fun getRegion(name: String, atlasData: SpriteManager.AtlasData): TextureRegion = atlasData.atlas.findRegion(name)

          private val animVideo_1_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointWheel.ANIM_VIDEO_1_0.data) }
          private val animVideo_1_1 = List<TextureRegion>(78) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointWheel.ANIM_VIDEO_1_1.data) }
          private val animVideo_1_2 = List<TextureRegion>(47) { getRegion("video (${it.inc()+156})", SpriteManager.EnumAtlas_JointWheel.ANIM_VIDEO_1_2.data) }

          private val animVideo_2_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointWheel.ANIM_VIDEO_2_0.data) }
          private val animVideo_2_1 = List<TextureRegion>(19) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointWheel.ANIM_VIDEO_2_1.data) }

          private val mem_1 = List<TextureRegion>(28) { getRegion("mem (${it.inc()})", SpriteManager.EnumAtlas_JointWheel.MEM_1.data) }

          val animVideo_1 = animVideo_1_0+animVideo_1_1+animVideo_1_2
          val animVideo_2 = animVideo_2_0+animVideo_2_1

          val mem = mem_1

          val I1 = JWheel.I1.data.texture
     }

     class JointWeld: TutorialsAssets {
          private fun getRegion(name: String, atlasData: SpriteManager.AtlasData): TextureRegion = atlasData.atlas.findRegion(name)

          private val animVideo_1_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointWeld.ANIM_VIDEO_1_0.data) }
          private val animVideo_1_1 = List<TextureRegion>(48) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointWeld.ANIM_VIDEO_1_1.data) }

          private val animVideo_2_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointWeld.ANIM_VIDEO_2_0.data) }
          private val animVideo_2_1 = List<TextureRegion>(41) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointWeld.ANIM_VIDEO_2_1.data) }

          private val animVideo_3_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointWeld.ANIM_VIDEO_3_0.data) }
          private val animVideo_3_1 = List<TextureRegion>(43) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointWeld.ANIM_VIDEO_3_1.data) }

          private val animVideo_4_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointWeld.ANIM_VIDEO_4_0.data) }
          private val animVideo_4_1 = List<TextureRegion>(63) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointWeld.ANIM_VIDEO_4_1.data) }

          private val mem_1 = List<TextureRegion>(91) { getRegion("mem (${it.inc()})", SpriteManager.EnumAtlas_JointWeld.MEM_1.data) }

          val animVideo_1 = animVideo_1_0+animVideo_1_1
          val animVideo_2 = animVideo_2_0+animVideo_2_1
          val animVideo_3 = animVideo_3_0+animVideo_3_1
          val animVideo_4 = animVideo_4_0+animVideo_4_1

          val mem = mem_1

          val I1 = JWeld.I1.data.texture
          val I2 = JWeld.I2.data.texture
     }

     class JointFriction: TutorialsAssets {
          private fun getRegion(name: String, atlasData: SpriteManager.AtlasData): TextureRegion = atlasData.atlas.findRegion(name)

          private val animVideo_1_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointFriction.ANIM_VIDEO_1_0.data) }
          private val animVideo_1_1 = List<TextureRegion>(78) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointFriction.ANIM_VIDEO_1_1.data) }
          private val animVideo_1_2 = List<TextureRegion>(46) { getRegion("video (${it.inc()+156})", SpriteManager.EnumAtlas_JointFriction.ANIM_VIDEO_1_2.data) }

          private val animVideo_2_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointFriction.ANIM_VIDEO_2_0.data) }
          private val animVideo_2_1 = List<TextureRegion>(45) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointFriction.ANIM_VIDEO_2_1.data) }

          private val animVideo_3_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointFriction.ANIM_VIDEO_3_0.data) }
          private val animVideo_3_1 = List<TextureRegion>(78) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointFriction.ANIM_VIDEO_3_1.data) }
          private val animVideo_3_2 = List<TextureRegion>(78) { getRegion("video (${it.inc()+156})", SpriteManager.EnumAtlas_JointFriction.ANIM_VIDEO_3_2.data) }
          private val animVideo_3_3 = List<TextureRegion>(4) { getRegion("video (${it.inc()+234})", SpriteManager.EnumAtlas_JointFriction.ANIM_VIDEO_3_3.data) }

          private val animVideo_4_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointFriction.ANIM_VIDEO_4_0.data) }
          private val animVideo_4_1 = List<TextureRegion>(78) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointFriction.ANIM_VIDEO_4_1.data) }
          private val animVideo_4_2 = List<TextureRegion>(32) { getRegion("video (${it.inc()+156})", SpriteManager.EnumAtlas_JointFriction.ANIM_VIDEO_4_2.data) }

          private val mem_1 = List<TextureRegion>(48) { getRegion("mem (${it.inc()})", SpriteManager.EnumAtlas_JointFriction.MEM_1.data) }
          private val mem_2 = List<TextureRegion>(48) { getRegion("mem (${it.inc()+48})", SpriteManager.EnumAtlas_JointFriction.MEM_2.data) }
          private val mem_3 = List<TextureRegion>(23) { getRegion("mem (${it.inc()+96})", SpriteManager.EnumAtlas_JointFriction.MEM_3.data) }

          val animVideo_1 = animVideo_1_0+animVideo_1_1+animVideo_1_2
          val animVideo_2 = animVideo_2_0+animVideo_2_1
          val animVideo_3 = animVideo_3_0+animVideo_3_1+animVideo_3_2+animVideo_3_3
          val animVideo_4 = animVideo_4_0+animVideo_4_1+animVideo_4_2

          val mem = mem_1+mem_2+mem_3

          val I1 = JFriction.I1.data.texture
     }

     class JointRope: TutorialsAssets {
          private fun getRegion(name: String, atlasData: SpriteManager.AtlasData): TextureRegion = atlasData.atlas.findRegion(name)

          private val animVideo_1_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointRope.ANIM_VIDEO_1_0.data) }
          private val animVideo_1_1 = List<TextureRegion>(61) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointRope.ANIM_VIDEO_1_1.data) }

          private val mem_1 = List<TextureRegion>(14) { getRegion("mem (${it.inc()})", SpriteManager.EnumAtlas_JointRope.MEM_1.data) }

          val animVideo_1 = animVideo_1_0+animVideo_1_1

          val mem = mem_1

          val I1 = JRope.I1.data.texture
     }

     class JointPulley: TutorialsAssets {
          private fun getRegion(name: String, atlasData: SpriteManager.AtlasData): TextureRegion = atlasData.atlas.findRegion(name)

          private val animVideo_1_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointPulley.ANIM_VIDEO_1_0.data) }
          private val animVideo_1_1 = List<TextureRegion>(41) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointPulley.ANIM_VIDEO_1_1.data) }

          private val animVideo_2_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointPulley.ANIM_VIDEO_2_0.data) }
          private val animVideo_2_1 = List<TextureRegion>(14) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointPulley.ANIM_VIDEO_2_1.data) }

          private val animVideo_3_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointPulley.ANIM_VIDEO_3_0.data) }
          private val animVideo_3_1 = List<TextureRegion>(64) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointPulley.ANIM_VIDEO_3_1.data) }

          private val mem_1 = List<TextureRegion>(20) { getRegion("mem (${it.inc()})", SpriteManager.EnumAtlas_JointPulley.MEM_1.data) }
          private val mem_2 = List<TextureRegion>(20) { getRegion("mem (${it.inc()+20})", SpriteManager.EnumAtlas_JointPulley.MEM_2.data) }
          private val mem_3 = List<TextureRegion>(16) { getRegion("mem (${it.inc()+40})", SpriteManager.EnumAtlas_JointPulley.MEM_3.data) }

          val animVideo_1 = animVideo_1_0+animVideo_1_1
          val animVideo_2 = animVideo_2_0+animVideo_2_1
          val animVideo_3 = animVideo_3_0+animVideo_3_1

          val mem = mem_1+mem_2+mem_3

          val I1 = JPulley.I1.data.texture
          val I2 = JPulley.I2.data.texture
     }

     class JointGear: TutorialsAssets {
          private fun getRegion(name: String, atlasData: SpriteManager.AtlasData): TextureRegion = atlasData.atlas.findRegion(name)

          private val animVideo_1_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointGear.ANIM_VIDEO_1_0.data) }
          private val animVideo_1_1 = List<TextureRegion>(78) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointGear.ANIM_VIDEO_1_1.data) }
          private val animVideo_1_2 = List<TextureRegion>(78) { getRegion("video (${it.inc()+156})", SpriteManager.EnumAtlas_JointGear.ANIM_VIDEO_1_2.data) }
          private val animVideo_1_3 = List<TextureRegion>(37) { getRegion("video (${it.inc()+234})", SpriteManager.EnumAtlas_JointGear.ANIM_VIDEO_1_3.data) }

          private val animVideo_2_0 = List<TextureRegion>(78) { getRegion("video (${it.inc()})", SpriteManager.EnumAtlas_JointGear.ANIM_VIDEO_2_0.data) }
          private val animVideo_2_1 = List<TextureRegion>(75) { getRegion("video (${it.inc()+78})", SpriteManager.EnumAtlas_JointGear.ANIM_VIDEO_2_1.data) }

          private val mem_1 = List<TextureRegion>(48) { getRegion("mem (${it.inc()})", SpriteManager.EnumAtlas_JointGear.MEM_1.data) }
          private val mem_2 = List<TextureRegion>(18) { getRegion("mem (${it.inc()+48})", SpriteManager.EnumAtlas_JointGear.MEM_2.data) }

          val animVideo_1 = animVideo_1_0+animVideo_1_1+animVideo_1_2+animVideo_1_3
          val animVideo_2 = animVideo_2_0+animVideo_2_1

          val mem = mem_1+mem_2

          val I1 = JGear.I1.data.texture
     }

     class JointMotor: TutorialsAssets {
          private fun getRegion(name: String, atlasData: SpriteManager.AtlasData): TextureRegion = atlasData.atlas.findRegion(name)

          private val animVideo_1_0 = List<TextureRegion>(78) { getRegion("video ${it.inc()}", SpriteManager.EnumAtlas_JointMotor.ANIM_VIDEO_1_0.data) }
          private val animVideo_1_1 = List<TextureRegion>(78) { getRegion("video ${it.inc()+78}", SpriteManager.EnumAtlas_JointMotor.ANIM_VIDEO_1_1.data) }
          private val animVideo_1_2 = List<TextureRegion>(78) { getRegion("video ${it.inc()+156}", SpriteManager.EnumAtlas_JointMotor.ANIM_VIDEO_1_2.data) }
          private val animVideo_1_3 = List<TextureRegion>(55) { getRegion("video ${it.inc()+234}", SpriteManager.EnumAtlas_JointMotor.ANIM_VIDEO_1_3.data) }

          private val animVideo_2_0 = List<TextureRegion>(78) { getRegion("video ${it.inc()}", SpriteManager.EnumAtlas_JointMotor.ANIM_VIDEO_2_0.data) }
          private val animVideo_2_1 = List<TextureRegion>(25) { getRegion("video ${it.inc()+78}", SpriteManager.EnumAtlas_JointMotor.ANIM_VIDEO_2_1.data) }

          private val animVideo_3_0 = List<TextureRegion>(78) { getRegion("video ${it.inc()}", SpriteManager.EnumAtlas_JointMotor.ANIM_VIDEO_3_0.data) }
          private val animVideo_3_1 = List<TextureRegion>(78) { getRegion("video ${it.inc()+78}", SpriteManager.EnumAtlas_JointMotor.ANIM_VIDEO_3_1.data) }
          private val animVideo_3_2 = List<TextureRegion>(6) { getRegion("video ${it.inc()+156}", SpriteManager.EnumAtlas_JointMotor.ANIM_VIDEO_3_2.data) }

          private val animVideo_4_0 = List<TextureRegion>(78) { getRegion("video ${it.inc()}", SpriteManager.EnumAtlas_JointMotor.ANIM_VIDEO_4_0.data) }
          private val animVideo_4_1 = List<TextureRegion>(54) { getRegion("video ${it.inc()+78}", SpriteManager.EnumAtlas_JointMotor.ANIM_VIDEO_4_1.data) }

          private val animVideo_5_0 = List<TextureRegion>(78) { getRegion("video ${it.inc()}", SpriteManager.EnumAtlas_JointMotor.ANIM_VIDEO_5_0.data) }
          private val animVideo_5_1 = List<TextureRegion>(25) { getRegion("video ${it.inc()+78}", SpriteManager.EnumAtlas_JointMotor.ANIM_VIDEO_5_1.data) }


          private val mem_1_1 = List<TextureRegion>(28) { getRegion("mem ${it.inc()}", SpriteManager.EnumAtlas_JointMotor.MEM_1_1.data) }
          private val mem_1_2 = List<TextureRegion>(28) { getRegion("mem ${it.inc()+28}", SpriteManager.EnumAtlas_JointMotor.MEM_1_2.data) }
          private val mem_1_3 = List<TextureRegion>(28) { getRegion("mem ${it.inc()+56}", SpriteManager.EnumAtlas_JointMotor.MEM_1_3.data) }
          private val mem_1_4 = List<TextureRegion>(28) { getRegion("mem ${it.inc()+84}", SpriteManager.EnumAtlas_JointMotor.MEM_1_4.data) }
          private val mem_1_5 = List<TextureRegion>(28) { getRegion("mem ${it.inc()+112}", SpriteManager.EnumAtlas_JointMotor.MEM_1_5.data) }
          private val mem_1_6 = List<TextureRegion>(23) { getRegion("mem ${it.inc()+140}", SpriteManager.EnumAtlas_JointMotor.MEM_1_6.data) }

          private val mem_2_1 = List<TextureRegion>(28) { getRegion("mem ${it.inc()}", SpriteManager.EnumAtlas_JointMotor.MEM_2_1.data) }
          private val mem_2_2 = List<TextureRegion>(28) { getRegion("mem ${it.inc()+28}", SpriteManager.EnumAtlas_JointMotor.MEM_2_2.data) }
          private val mem_2_3 = List<TextureRegion>(28) { getRegion("mem ${it.inc()+56}", SpriteManager.EnumAtlas_JointMotor.MEM_2_3.data) }
          private val mem_2_4 = List<TextureRegion>(28) { getRegion("mem ${it.inc()+84}", SpriteManager.EnumAtlas_JointMotor.MEM_2_4.data) }
          private val mem_2_5 = List<TextureRegion>(15) { getRegion("mem ${it.inc()+112}", SpriteManager.EnumAtlas_JointMotor.MEM_2_5.data) }

          val animVideo_1 = animVideo_1_0+animVideo_1_1+animVideo_1_2+animVideo_1_3
          val animVideo_2 = animVideo_2_0+animVideo_2_1
          val animVideo_3 = animVideo_3_0+animVideo_3_1+animVideo_3_2
          val animVideo_4 = animVideo_4_0+animVideo_4_1
          val animVideo_5 = animVideo_5_0+animVideo_5_1

          val mem1 = mem_1_1+mem_1_2+mem_1_3+mem_1_4+mem_1_5+mem_1_6
          val mem2 = mem_2_1+mem_2_2+mem_2_3+mem_2_4+mem_2_5
     }


     interface TutorialsAssets
}