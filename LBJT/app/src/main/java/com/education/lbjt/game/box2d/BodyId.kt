package com.education.lbjt.game.box2d

object BodyId {
    const val NONE      = "none"
    const val BORDERS   = "borders"
    const val SEPARATOR = "separator"

    object Menu {
        const val STATIC = "menu.static"
        const val BUTTON = "menu.button"
    }

    object Settings {
        const val VOLUME   = "settings.volume"
        const val START    = "settings.start"
        const val LANGUAGE = "settings.language"
    }

    object AboutAuthor {
        const val ITEM = "author.item"
    }

    object Comment {
        const val ITEM   = "comment.item"
        const val DIALOG = "comment.dialog"
    }

    object TestStand {
        const val STATIC   = "test_stand.static"
        const val DYNAMIC  = "test_stand.dynamic"
    }

    object Tutorials {
        const val KINEMATIC = "tutorials.kinematic"
        const val DYNAMIC   = "tutorials.dynamic"
    }

    object Practical {
        const val STATIC  = "practical.static"
        const val DYNAMIC = "practical.dynamic"
    }
}