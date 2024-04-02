package com.education.lbjt.game.actors.scroll

import com.badlogic.gdx.scenes.scene2d.Actor
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class HorizontalGroup(
    override val screen: AdvancedScreen,
    val gap      : Float = 0f,
    val startGap : Float = gap,
    val endGap   : Float = gap,
    val alignment: Static.Alignment = Static.Alignment.START,
    val direction: Static.Direction = Static.Direction.RIGHT,
) : AdvancedGroup() {

    private var nx = 0f
    private var newWidth = 0f

    override fun getPrefWidth(): Float {
        newWidth = 0f
        children.onEach { newWidth += it.width + gap }

        newWidth -= gap
        newWidth += startGap + endGap

        if (alignment == Static.Alignment.END && parent.width > newWidth) newWidth = parent.width
        return newWidth
    }

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

    override fun childrenChanged() {
        super.childrenChanged()
        placeChildren()
    }

    private fun placeChildren() {
        when (alignment) {
            Static.Alignment.END                        -> {
                nx = prefWidth

                when (direction) {
                    Static.Direction.LEFT  -> children.onEachIndexed { index, a -> a.moveFromEND(index) }
                    Static.Direction.RIGHT -> children.reversed().onEachIndexed { index, a -> a.moveFromEND(index) }
                }
            }
            Static.Alignment.START -> {
                nx = 0f

                when (direction) {
                    Static.Direction.LEFT  -> children.reversed().onEachIndexed { index, a -> a.moveFromSTART(index) }
                    Static.Direction.RIGHT -> children.onEachIndexed { index, a -> a.moveFromSTART(index) }
                }
            }
        }
    }

    private fun Int.gap() = (if (this==0) startGap else gap)

    private fun Actor.moveFromEND(index: Int) {
        nx = nx - index.gap() - width
        x  = nx
    }

    private fun Actor.moveFromSTART(index: Int) {
        nx += index.gap()
        x  = nx
        nx += width
    }

    object Static {
        enum class Direction {
            LEFT, RIGHT
        }

        enum class Alignment {
            START, END
        }
    }

}