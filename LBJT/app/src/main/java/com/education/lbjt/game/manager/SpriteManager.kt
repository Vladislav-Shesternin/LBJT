package com.education.lbjt.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class SpriteManager(var assetManager: AssetManager) {

    var loadableAtlasList   = mutableListOf<AtlasData>()
    var loadableTextureList = mutableListOf<TextureData>()

    fun loadAtlas() {
        loadableAtlasList.onEach { assetManager.load(it.path, TextureAtlas::class.java) }
    }

    fun loadTexture() {
        loadableTextureList.onEach {
            assetManager.load(it.path, Texture::class.java, TextureLoader.TextureParameter().apply {
                minFilter = Texture.TextureFilter.Linear
                magFilter = Texture.TextureFilter.Linear
                genMipMaps = true
            })
        }
    }

    fun initAtlas() {
        loadableAtlasList.onEach { it.atlas = assetManager[it.path, TextureAtlas::class.java] }
        loadableAtlasList.clear()
    }

    fun initTexture() {
        loadableTextureList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTextureList.clear()
    }

    fun initAtlasAndTexture() {
        initAtlas()
        initTexture()
    }


    enum class EnumAtlas(val data: AtlasData) {
        GAME1(AtlasData("atlas/game1.atlas")),
        GAME2(AtlasData("atlas/game2.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        YAN_BACKGROUND(TextureData("textures/yan_background.png")),
        YIN_BACKGROUND(TextureData("textures/yin_background.png")),
        MASK_ICON     (TextureData("textures/mask_icon.png")     ),
        VELDAN_ICON   (TextureData("textures/veldan_icon.png")   ),
        UPDATE_PANEL  (TextureData("textures/update_panel.png")   ),

        PRACTICAL_PROGRESS_MASK(TextureData("textures/practical_progress_mask.png")),
    }

    // ---------------------------------------------------
    // Tutorials
    // ---------------------------------------------------

    enum class EnumAtlas_GeneralInformation(val data: AtlasData) {
        ANIM_OBAMA(AtlasData("atlas/anim/tutorials/general_information/obama.atlas")),
    }
    enum class EnumTexture_GeneralInformation(val data: TextureData) {
        I1( TextureData("textures/tutorials/general_information/i1.png") ),
        I2( TextureData("textures/tutorials/general_information/i2.png") ),
        I3( TextureData("textures/tutorials/general_information/i3.png") ),
        I4( TextureData("textures/tutorials/general_information/i4.png") ),
        I5( TextureData("textures/tutorials/general_information/i5.png") ),
        I6( TextureData("textures/tutorials/general_information/i6.png") ),
        I7( TextureData("textures/tutorials/general_information/i7.png") ),
        I8( TextureData("textures/tutorials/general_information/i8.png") ),
        I9( TextureData("textures/tutorials/general_information/i9.png") ),
        I10(TextureData("textures/tutorials/general_information/i10.png")),
        I11(TextureData("textures/tutorials/general_information/i11.png")),
    }

    enum class EnumAtlas_JointMouse(val data: AtlasData) {
        ANIM_VIDEO_1_0(AtlasData("atlas/anim/tutorials/joint_mouse/video_1/video_1_0.atlas")),
        ANIM_VIDEO_1_1(AtlasData("atlas/anim/tutorials/joint_mouse/video_1/video_1_1.atlas")),
        ANIM_VIDEO_1_2(AtlasData("atlas/anim/tutorials/joint_mouse/video_1/video_1_2.atlas")),
        ANIM_VIDEO_1_3(AtlasData("atlas/anim/tutorials/joint_mouse/video_1/video_1_3.atlas")),

        ANIM_VIDEO_2_0(AtlasData("atlas/anim/tutorials/joint_mouse/video_2/video_2_0.atlas")),
        ANIM_VIDEO_2_1(AtlasData("atlas/anim/tutorials/joint_mouse/video_2/video_2_1.atlas")),
        ANIM_VIDEO_2_2(AtlasData("atlas/anim/tutorials/joint_mouse/video_2/video_2_2.atlas")),
        ANIM_VIDEO_2_3(AtlasData("atlas/anim/tutorials/joint_mouse/video_2/video_2_3.atlas")),
        ANIM_VIDEO_2_4(AtlasData("atlas/anim/tutorials/joint_mouse/video_2/video_2_4.atlas")),
        ANIM_VIDEO_2_5(AtlasData("atlas/anim/tutorials/joint_mouse/video_2/video_2_5.atlas")),

        ANIM_VIDEO_3_0(AtlasData("atlas/anim/tutorials/joint_mouse/video_3/video_3_0.atlas")),
        ANIM_VIDEO_3_1(AtlasData("atlas/anim/tutorials/joint_mouse/video_3/video_3_1.atlas")),
        ANIM_VIDEO_3_2(AtlasData("atlas/anim/tutorials/joint_mouse/video_3/video_3_2.atlas")),
        ANIM_VIDEO_3_3(AtlasData("atlas/anim/tutorials/joint_mouse/video_3/video_3_3.atlas")),
        ANIM_VIDEO_3_4(AtlasData("atlas/anim/tutorials/joint_mouse/video_3/video_3_4.atlas")),
        ANIM_VIDEO_3_5(AtlasData("atlas/anim/tutorials/joint_mouse/video_3/video_3_5.atlas")),
        ANIM_VIDEO_3_6(AtlasData("atlas/anim/tutorials/joint_mouse/video_3/video_3_6.atlas")),

        MEM_1(AtlasData("atlas/anim/tutorials/joint_mouse/mem/mem_1.atlas")),
        MEM_2(AtlasData("atlas/anim/tutorials/joint_mouse/mem/mem_2.atlas")),
    }
    enum class EnumTexture_JointMouse(val data: TextureData) {
        I1(TextureData("textures/tutorials/joint_mouse/i1.png")),
    }

    enum class EnumAtlas_JointDistance(val data: AtlasData) {
        ANIM_VIDEO_1_0(AtlasData("atlas/anim/tutorials/joint_distance/video_1/video_1_0.atlas")),
        ANIM_VIDEO_1_1(AtlasData("atlas/anim/tutorials/joint_distance/video_1/video_1_1.atlas")),
        ANIM_VIDEO_1_2(AtlasData("atlas/anim/tutorials/joint_distance/video_1/video_1_2.atlas")),

        ANIM_VIDEO_2_0(AtlasData("atlas/anim/tutorials/joint_distance/video_2/video_2_0.atlas")),
        ANIM_VIDEO_2_1(AtlasData("atlas/anim/tutorials/joint_distance/video_2/video_2_1.atlas")),
        ANIM_VIDEO_2_2(AtlasData("atlas/anim/tutorials/joint_distance/video_2/video_2_2.atlas")),

        ANIM_VIDEO_3_0(AtlasData("atlas/anim/tutorials/joint_distance/video_3/video_3_0.atlas")),
        ANIM_VIDEO_3_1(AtlasData("atlas/anim/tutorials/joint_distance/video_3/video_3_1.atlas")),
        ANIM_VIDEO_3_2(AtlasData("atlas/anim/tutorials/joint_distance/video_3/video_3_2.atlas")),
        ANIM_VIDEO_3_3(AtlasData("atlas/anim/tutorials/joint_distance/video_3/video_3_3.atlas")),

        MEM_1(AtlasData("atlas/anim/tutorials/joint_distance/mem/mem_1.atlas")),
        MEM_2(AtlasData("atlas/anim/tutorials/joint_distance/mem/mem_2.atlas")),
    }
    enum class EnumTexture_JointDistance(val data: TextureData) {
        I1(TextureData("textures/tutorials/joint_distance/i1.png")),
        I2(TextureData("textures/tutorials/joint_distance/i2.png")),
    }

    enum class EnumAtlas_JointRevolute(val data: AtlasData) {
        ANIM_VIDEO_1_0(AtlasData("atlas/anim/tutorials/joint_revolute/video_1/video_1_0.atlas")),
        ANIM_VIDEO_1_1(AtlasData("atlas/anim/tutorials/joint_revolute/video_1/video_1_1.atlas")),
        ANIM_VIDEO_1_2(AtlasData("atlas/anim/tutorials/joint_revolute/video_1/video_1_2.atlas")),
        ANIM_VIDEO_1_3(AtlasData("atlas/anim/tutorials/joint_revolute/video_1/video_1_3.atlas")),

        ANIM_VIDEO_2_0(AtlasData("atlas/anim/tutorials/joint_revolute/video_2/video_2_0.atlas")),
        ANIM_VIDEO_2_1(AtlasData("atlas/anim/tutorials/joint_revolute/video_2/video_2_1.atlas")),

        ANIM_VIDEO_3_0(AtlasData("atlas/anim/tutorials/joint_revolute/video_3/video_3_0.atlas")),
        ANIM_VIDEO_3_1(AtlasData("atlas/anim/tutorials/joint_revolute/video_3/video_3_1.atlas")),

        ANIM_VIDEO_4_0(AtlasData("atlas/anim/tutorials/joint_revolute/video_4/video_4_0.atlas")),
        ANIM_VIDEO_4_1(AtlasData("atlas/anim/tutorials/joint_revolute/video_4/video_4_1.atlas")),
        ANIM_VIDEO_4_2(AtlasData("atlas/anim/tutorials/joint_revolute/video_4/video_4_2.atlas")),
        ANIM_VIDEO_4_3(AtlasData("atlas/anim/tutorials/joint_revolute/video_4/video_4_3.atlas")),

        MEM_1(AtlasData("atlas/anim/tutorials/joint_revolute/mem/mem_1.atlas")),
        MEM_2(AtlasData("atlas/anim/tutorials/joint_revolute/mem/mem_2.atlas")),
    }
    enum class EnumTexture_JointRevolute(val data: TextureData) {
        I1(TextureData("textures/tutorials/joint_revolute/i1.png")),
        I2(TextureData("textures/tutorials/joint_revolute/i2.png")),
    }

    enum class EnumAtlas_JointPrismatic(val data: AtlasData) {
        ANIM_VIDEO_1_0(AtlasData("atlas/anim/tutorials/joint_prismatic/video_1/video_1_0.atlas")),
        ANIM_VIDEO_1_1(AtlasData("atlas/anim/tutorials/joint_prismatic/video_1/video_1_1.atlas")),
        ANIM_VIDEO_1_2(AtlasData("atlas/anim/tutorials/joint_prismatic/video_1/video_1_2.atlas")),

        ANIM_VIDEO_2_0(AtlasData("atlas/anim/tutorials/joint_prismatic/video_2/video_2_0.atlas")),
        ANIM_VIDEO_2_1(AtlasData("atlas/anim/tutorials/joint_prismatic/video_2/video_2_1.atlas")),

        ANIM_VIDEO_3_0(AtlasData("atlas/anim/tutorials/joint_prismatic/video_3/video_3_0.atlas")),
        ANIM_VIDEO_3_1(AtlasData("atlas/anim/tutorials/joint_prismatic/video_3/video_3_1.atlas")),

        ANIM_VIDEO_4_0(AtlasData("atlas/anim/tutorials/joint_prismatic/video_4/video_4_0.atlas")),
        ANIM_VIDEO_4_1(AtlasData("atlas/anim/tutorials/joint_prismatic/video_4/video_4_1.atlas")),

        ANIM_VIDEO_5_0(AtlasData("atlas/anim/tutorials/joint_prismatic/video_5/video_5_0.atlas")),
        ANIM_VIDEO_5_1(AtlasData("atlas/anim/tutorials/joint_prismatic/video_5/video_5_1.atlas")),
        ANIM_VIDEO_5_2(AtlasData("atlas/anim/tutorials/joint_prismatic/video_5/video_5_2.atlas")),

        MEM_1(AtlasData("atlas/anim/tutorials/joint_prismatic/mem/mem_1.atlas")),
    }
    enum class EnumTexture_JointPrismatic(val data: TextureData) {
        I1(TextureData("textures/tutorials/joint_prismatic/i1.png")),
        I2(TextureData("textures/tutorials/joint_prismatic/i2.png")),
        I3(TextureData("textures/tutorials/joint_prismatic/i3.png")),
        I4(TextureData("textures/tutorials/joint_prismatic/i4.png")),
        I5(TextureData("textures/tutorials/joint_prismatic/i5.png")),
    }

    enum class EnumAtlas_JointWheel(val data: AtlasData) {
        ANIM_VIDEO_1_0(AtlasData("atlas/anim/tutorials/joint_wheel/video_1/video_1_0.atlas")),
        ANIM_VIDEO_1_1(AtlasData("atlas/anim/tutorials/joint_wheel/video_1/video_1_1.atlas")),
        ANIM_VIDEO_1_2(AtlasData("atlas/anim/tutorials/joint_wheel/video_1/video_1_2.atlas")),

        ANIM_VIDEO_2_0(AtlasData("atlas/anim/tutorials/joint_wheel/video_2/video_2_0.atlas")),
        ANIM_VIDEO_2_1(AtlasData("atlas/anim/tutorials/joint_wheel/video_2/video_2_1.atlas")),

        MEM_1(AtlasData("atlas/anim/tutorials/joint_wheel/mem/mem_1.atlas")),
    }
    enum class EnumTexture_JointWheel(val data: TextureData) {
        I1(TextureData("textures/tutorials/joint_wheel/i1.png")),
    }

    enum class EnumAtlas_JointWeld(val data: AtlasData) {
        ANIM_VIDEO_1_0(AtlasData("atlas/anim/tutorials/joint_weld/video_1/video_1_0.atlas")),
        ANIM_VIDEO_1_1(AtlasData("atlas/anim/tutorials/joint_weld/video_1/video_1_1.atlas")),

        ANIM_VIDEO_2_0(AtlasData("atlas/anim/tutorials/joint_weld/video_2/video_2_0.atlas")),
        ANIM_VIDEO_2_1(AtlasData("atlas/anim/tutorials/joint_weld/video_2/video_2_1.atlas")),

        ANIM_VIDEO_3_0(AtlasData("atlas/anim/tutorials/joint_weld/video_3/video_3_0.atlas")),
        ANIM_VIDEO_3_1(AtlasData("atlas/anim/tutorials/joint_weld/video_3/video_3_1.atlas")),

        ANIM_VIDEO_4_0(AtlasData("atlas/anim/tutorials/joint_weld/video_4/video_4_0.atlas")),
        ANIM_VIDEO_4_1(AtlasData("atlas/anim/tutorials/joint_weld/video_4/video_4_1.atlas")),

        MEM_1(AtlasData("atlas/anim/tutorials/joint_weld/mem/mem_1.atlas")),
    }
    enum class EnumTexture_JointWeld(val data: TextureData) {
        I1(TextureData("textures/tutorials/joint_weld/i1.png")),
        I2(TextureData("textures/tutorials/joint_weld/i2.png")),
    }

    enum class EnumAtlas_JointFriction(val data: AtlasData) {
        ANIM_VIDEO_1_0(AtlasData("atlas/anim/tutorials/joint_friction/video_1/video_1_0.atlas")),
        ANIM_VIDEO_1_1(AtlasData("atlas/anim/tutorials/joint_friction/video_1/video_1_1.atlas")),
        ANIM_VIDEO_1_2(AtlasData("atlas/anim/tutorials/joint_friction/video_1/video_1_2.atlas")),

        ANIM_VIDEO_2_0(AtlasData("atlas/anim/tutorials/joint_friction/video_2/video_2_0.atlas")),
        ANIM_VIDEO_2_1(AtlasData("atlas/anim/tutorials/joint_friction/video_2/video_2_1.atlas")),

        ANIM_VIDEO_3_0(AtlasData("atlas/anim/tutorials/joint_friction/video_3/video_3_0.atlas")),
        ANIM_VIDEO_3_1(AtlasData("atlas/anim/tutorials/joint_friction/video_3/video_3_1.atlas")),
        ANIM_VIDEO_3_2(AtlasData("atlas/anim/tutorials/joint_friction/video_3/video_3_2.atlas")),
        ANIM_VIDEO_3_3(AtlasData("atlas/anim/tutorials/joint_friction/video_3/video_3_3.atlas")),

        ANIM_VIDEO_4_0(AtlasData("atlas/anim/tutorials/joint_friction/video_4/video_4_0.atlas")),
        ANIM_VIDEO_4_1(AtlasData("atlas/anim/tutorials/joint_friction/video_4/video_4_1.atlas")),
        ANIM_VIDEO_4_2(AtlasData("atlas/anim/tutorials/joint_friction/video_4/video_4_2.atlas")),

        MEM_1(AtlasData("atlas/anim/tutorials/joint_friction/mem/mem_1.atlas")),
        MEM_2(AtlasData("atlas/anim/tutorials/joint_friction/mem/mem_2.atlas")),
        MEM_3(AtlasData("atlas/anim/tutorials/joint_friction/mem/mem_3.atlas")),
    }
    enum class EnumTexture_JointFriction(val data: TextureData) {
        I1(TextureData("textures/tutorials/joint_friction/i1.png")),
    }

    enum class EnumAtlas_JointRope(val data: AtlasData) {
        ANIM_VIDEO_1_0(AtlasData("atlas/anim/tutorials/joint_rope/video_1/video_1_0.atlas")),
        ANIM_VIDEO_1_1(AtlasData("atlas/anim/tutorials/joint_rope/video_1/video_1_1.atlas")),

        MEM_1(AtlasData("atlas/anim/tutorials/joint_rope/mem/mem_1.atlas")),

    }
    enum class EnumTexture_JointRope(val data: TextureData) {
        I1(TextureData("textures/tutorials/joint_rope/i1.png")),
    }

    enum class EnumAtlas_JointPulley(val data: AtlasData) {
        ANIM_VIDEO_1_0(AtlasData("atlas/anim/tutorials/joint_pulley/video_1/video_1_0.atlas")),
        ANIM_VIDEO_1_1(AtlasData("atlas/anim/tutorials/joint_pulley/video_1/video_1_1.atlas")),

        ANIM_VIDEO_2_0(AtlasData("atlas/anim/tutorials/joint_pulley/video_2/video_2_0.atlas")),
        ANIM_VIDEO_2_1(AtlasData("atlas/anim/tutorials/joint_pulley/video_2/video_2_1.atlas")),

        ANIM_VIDEO_3_0(AtlasData("atlas/anim/tutorials/joint_pulley/video_3/video_3_0.atlas")),
        ANIM_VIDEO_3_1(AtlasData("atlas/anim/tutorials/joint_pulley/video_3/video_3_1.atlas")),

        MEM_1(AtlasData("atlas/anim/tutorials/joint_pulley/mem/mem_1.atlas")),
        MEM_2(AtlasData("atlas/anim/tutorials/joint_pulley/mem/mem_2.atlas")),
        MEM_3(AtlasData("atlas/anim/tutorials/joint_pulley/mem/mem_3.atlas")),

    }
    enum class EnumTexture_JointPulley(val data: TextureData) {
        I1(TextureData("textures/tutorials/joint_pulley/i1.png")),
        I2(TextureData("textures/tutorials/joint_pulley/i2.png")),
    }

    enum class EnumAtlas_JointGear(val data: AtlasData) {
        ANIM_VIDEO_1_0(AtlasData("atlas/anim/tutorials/joint_gear/video_1/video_1_0.atlas")),
        ANIM_VIDEO_1_1(AtlasData("atlas/anim/tutorials/joint_gear/video_1/video_1_1.atlas")),
        ANIM_VIDEO_1_2(AtlasData("atlas/anim/tutorials/joint_gear/video_1/video_1_2.atlas")),
        ANIM_VIDEO_1_3(AtlasData("atlas/anim/tutorials/joint_gear/video_1/video_1_3.atlas")),

        ANIM_VIDEO_2_0(AtlasData("atlas/anim/tutorials/joint_gear/video_2/video_2_0.atlas")),
        ANIM_VIDEO_2_1(AtlasData("atlas/anim/tutorials/joint_gear/video_2/video_2_1.atlas")),

        MEM_1(AtlasData("atlas/anim/tutorials/joint_gear/mem/mem_1.atlas")),
        MEM_2(AtlasData("atlas/anim/tutorials/joint_gear/mem/mem_2.atlas")),
    }
    enum class EnumTexture_JointGear(val data: TextureData) {
        I1(TextureData("textures/tutorials/joint_gear/i1.png")),
    }

    enum class EnumAtlas_JointMotor(val data: AtlasData) {
        ANIM_VIDEO_1_0(AtlasData("atlas/anim/tutorials/joint_motor/video_1/video_1_0.atlas")),
        ANIM_VIDEO_1_1(AtlasData("atlas/anim/tutorials/joint_motor/video_1/video_1_1.atlas")),
        ANIM_VIDEO_1_2(AtlasData("atlas/anim/tutorials/joint_motor/video_1/video_1_2.atlas")),
        ANIM_VIDEO_1_3(AtlasData("atlas/anim/tutorials/joint_motor/video_1/video_1_3.atlas")),

        ANIM_VIDEO_2_0(AtlasData("atlas/anim/tutorials/joint_motor/video_2/video_2_0.atlas")),
        ANIM_VIDEO_2_1(AtlasData("atlas/anim/tutorials/joint_motor/video_2/video_2_1.atlas")),

        ANIM_VIDEO_3_0(AtlasData("atlas/anim/tutorials/joint_motor/video_3/video_3_0.atlas")),
        ANIM_VIDEO_3_1(AtlasData("atlas/anim/tutorials/joint_motor/video_3/video_3_1.atlas")),
        ANIM_VIDEO_3_2(AtlasData("atlas/anim/tutorials/joint_motor/video_3/video_3_2.atlas")),

        ANIM_VIDEO_4_0(AtlasData("atlas/anim/tutorials/joint_motor/video_4/video_4_0.atlas")),
        ANIM_VIDEO_4_1(AtlasData("atlas/anim/tutorials/joint_motor/video_4/video_4_1.atlas")),

        ANIM_VIDEO_5_0(AtlasData("atlas/anim/tutorials/joint_motor/video_5/video_5_0.atlas")),
        ANIM_VIDEO_5_1(AtlasData("atlas/anim/tutorials/joint_motor/video_5/video_5_1.atlas")),

        MEM_1_1(AtlasData("atlas/anim/tutorials/joint_motor/mem/mem_1.atlas")),
        MEM_1_2(AtlasData("atlas/anim/tutorials/joint_motor/mem/mem_2.atlas")),
        MEM_1_3(AtlasData("atlas/anim/tutorials/joint_motor/mem/mem_3.atlas")),
        MEM_1_4(AtlasData("atlas/anim/tutorials/joint_motor/mem/mem_4.atlas")),
        MEM_1_5(AtlasData("atlas/anim/tutorials/joint_motor/mem/mem_5.atlas")),
        MEM_1_6(AtlasData("atlas/anim/tutorials/joint_motor/mem/mem_6.atlas")),

        MEM_2_1(AtlasData("atlas/anim/tutorials/joint_motor/mem_1/mem_1.atlas")),
        MEM_2_2(AtlasData("atlas/anim/tutorials/joint_motor/mem_1/mem_2.atlas")),
        MEM_2_3(AtlasData("atlas/anim/tutorials/joint_motor/mem_1/mem_3.atlas")),
        MEM_2_4(AtlasData("atlas/anim/tutorials/joint_motor/mem_1/mem_4.atlas")),
        MEM_2_5(AtlasData("atlas/anim/tutorials/joint_motor/mem_1/mem_5.atlas")),
    }
    enum class EnumTexture_JointMotor(val data: TextureData) {
//        I1(TextureData("textures/tutorials/joint_gear/i1.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}