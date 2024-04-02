package com.education.lbjt.game.manager.util

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.utils.Disposable
import com.education.lbjt.game.manager.AudioManager
import com.education.lbjt.game.manager.MusicManager
import com.education.lbjt.game.utils.runGDX
import com.education.lbjt.util.cancelCoroutinesAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MusicUtil: Disposable {

    private val coroutine = CoroutineScope(Dispatchers.Default)

    val DEFAULT_1 = MusicManager.EnumMusic.DEFAULT_1.data.music
    val DEFAULT_2 = MusicManager.EnumMusic.DEFAULT_2.data.music

    val musicList                = listOf(DEFAULT_1, DEFAULT_2)
    val firebaseStorageMusicList = mutableListOf(DEFAULT_1, DEFAULT_2)

    // 0..100
    val volumeLevelFlow = MutableStateFlow(AudioManager.volumeLevelPercent / 100f)

    private var _music: Music? = null
    var music: Music?
        get() = _music
        set(value) { runGDX {
            if (value != null) {
                if (_music != value) {
                    _music?.stop()
                    _music = value
                    _music?.volume = volumeLevelFlow.value
                    _music?.play()
                }
            } else {
                _music?.stop()
                _music = null
            }
        } }

    init {
        coroutine.launch { volumeLevelFlow.collect { level -> runGDX { _music?.volume = level } } }
    }

    override fun dispose() {
        cancelCoroutinesAll(coroutine)
        _music = null
        music  = null
    }

}