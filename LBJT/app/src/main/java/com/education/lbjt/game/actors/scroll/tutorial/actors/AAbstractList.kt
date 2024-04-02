package com.education.lbjt.game.actors.scroll.tutorial.actors

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.education.lbjt.game.actors.scroll.VerticalGroup
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen

abstract class AAbstractList(
    final override val screen : AdvancedScreen,
): AdvancedGroup() {

    abstract val strings   : List<String>
    abstract val align     : Static.Align
    abstract val symbol    : Static.Symbol
    abstract val symbolFont: BitmapFont

    // Actor
    protected val verticalGroup = VerticalGroup(screen)

    // Field
    private var number = 0

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun Static.Symbol.getSymbol(): String = when(this) {
        Static.Symbol.Bullet -> " • "
        Static.Symbol.Number -> " ${++number}. "
    }

    object Static {
        enum class Align {
            Left, Center
        }

        enum class Symbol {
            Bullet, Number
        }
    }

}