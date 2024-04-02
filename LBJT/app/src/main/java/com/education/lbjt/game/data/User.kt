package com.education.lbjt.game.data

import com.badlogic.gdx.graphics.Texture

data class User(
    var id      : String?  = null,
    var nickname: String?  = null,
    var comment : String?  = null,
    var icon    : Texture? = null,
) {

    companion object {
        const val NICKNAME = "nickname"
    }

}