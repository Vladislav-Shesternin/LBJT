package com.education.lbjt.game.utils

import com.badlogic.gdx.math.Vector2

class SizeStandardizer {

    private var standart = 1f

    val Vector2.toStandart get() = this.divOr0(standart)
    val Float.toStandart   get() = this.divOr0(standart)
    val Vector2.toNew      get() = this.scl(standart)
    val Float.toNew        get() = this * standart

    fun standardize(standartW: Float, newW: Float) {
        standart = standartW / newW
    }

    inline fun<T> scope(block: SizeStandardizer.() -> T) = block(this)
}