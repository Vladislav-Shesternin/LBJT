package com.education.lbjt.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound

class SoundManager(var assetManager: AssetManager) {

    var loadableSoundList = mutableListOf<SoundData>()

    fun load() {
        loadableSoundList.onEach { assetManager.load(it.path, Sound::class.java) }
    }

    fun init() {
        loadableSoundList.onEach { it.sound = assetManager[it.path, Sound::class.java] }
        loadableSoundList.clear()
    }

    enum class EnumSound(val data: SoundData) {
        TOUCH_DOWN(SoundData("sound/touch.mp3")),

        ELECTRICITY(SoundData("sound/collision/electricity.mp3")),
        BORDER(     SoundData("sound/collision/border.mp3")     ),
        METAL(      SoundData("sound/collision/metal.mp3")      ),
        CLUNK(      SoundData("sound/collision/clunk.mp3")      ),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}