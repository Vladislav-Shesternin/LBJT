package com.education.lbjt.game.actors.scroll

import com.badlogic.gdx.scenes.scene2d.Actor
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class VerticalGroup(
    override val screen: AdvancedScreen,
    val gap      : Float = 0f,
    val startGap : Float = gap,
    val endGap   : Float = gap,
    val alignment: Static.Alignment = Static.Alignment.TOP,
    val direction: Static.Direction = Static.Direction.DOWN,
) : AdvancedGroup() {

    private var ny = 0f
    private var newHeight = 0f

    override fun getPrefWidth(): Float {
        return width
    }

    override fun getPrefHeight(): Float {
        newHeight = 0f
        children.onEach { newHeight += it.height + gap }

        newHeight -= gap
        newHeight += startGap + endGap

        if (alignment == Static.Alignment.TOP && parent.height > newHeight) newHeight = parent.height
        return newHeight
    }

    override fun addActorsOnGroup() {}

    override fun childrenChanged() {
        super.childrenChanged()
        placeChildren()
    }

    private fun placeChildren() {
        when (alignment) {
            Static.Alignment.TOP    -> {
                ny = prefHeight

                when (direction) {
                    Static.Direction.DOWN -> children.onEachIndexed { index, a -> a.moveFromTOP(index) }
                    Static.Direction.UP   -> children.reversed().onEachIndexed { index, a -> a.moveFromTOP(index) }
                }
            }
            Static.Alignment.BOTTOM -> {
                ny = 0f

                when (direction) {
                    Static.Direction.DOWN    -> children.reversed().onEachIndexed { index, a -> a.moveFromBOTTOM(index) }
                    Static.Direction.UP -> children.onEachIndexed { index, a -> a.moveFromBOTTOM(index) }
                }
            }
        }
    }

    private fun Int.gap() = (if (this==0) startGap else gap)

    private fun Actor.moveFromTOP(index: Int) {
        ny = ny - index.gap() - height
        y  = ny
    }

    private fun Actor.moveFromBOTTOM(index: Int) {
        ny += index.gap()
        y  = ny
        ny += height
    }

    object Static {
        enum class Direction {
            UP, DOWN
        }

        enum class Alignment {
            TOP, BOTTOM
        }
    }

}