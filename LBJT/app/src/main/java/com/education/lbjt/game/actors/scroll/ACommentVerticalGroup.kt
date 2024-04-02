package com.education.lbjt.game.actors.scroll

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class ACommentVerticalGroup(
    override val screen: AdvancedScreen,
    val gap      : Float = 0f,
    val startGap : Float = gap,
    val endGap   : Float = gap,
) : AdvancedGroup() {

    private var ny = 0f
    private var newHeight = 0f

    override fun getPrefHeight(): Float {
        newHeight = 0f
        children.onEach { newHeight += it.height + gap }

        newHeight -= gap
        newHeight += startGap + endGap

        if (parent.height > newHeight) newHeight = parent.height
        return newHeight
    }

    override fun addActorsOnGroup() {}

    private var lastActor1: Actor? = null
    private var lastActor2: Actor? = null

    override fun addActor(actor: Actor?) {
        actor?.animHide()

        super.addActor(actor)
        placeChildren(true)

        actor?.apply {
            x += ((width/2))
            addAction(Actions.parallel(
                Actions.fadeIn(0.2f),
                Actions.moveBy(-(width/2), 0f, 0.2f)
            ))
        }
    }

    override fun removeActorAt(index: Int, unfocus: Boolean): Actor {
        val isRemoved = super.removeActorAt(index, unfocus)
        placeChildren(false)
        return isRemoved
    }

    private fun placeChildren(isAdd: Boolean) {
        if (isAdd && children.size > 1) {
            lastActor1 = children.removeIndex(children.size - 1)
            lastActor2 = children.removeIndex(children.size - 1)

            children.add(lastActor1, lastActor2)

            lastActor1 = null
            lastActor2 = null
        }

        ny = prefHeight
        children.reversed().onEachIndexed { index, a -> a.moveFromTOP(index) }
    }

    private fun Int.gap() = (if (this == 0) startGap else gap)

    private fun Actor.moveFromTOP(index: Int) {
        ny = ny - index.gap() - height
        y  = ny
    }

}