package com.education.lbjt.game.data

data class Comment(
    var user_id : String?        = null,
    var comment : String?        = null,
) {
    companion object {
        const val COMMENT = "comment"
    }
}