package com.education.lbjt.game.box2d.bodiesGroup

import com.education.lbjt.game.box2d.AbstractBodyGroup
import com.education.lbjt.game.box2d.BodyId
import com.education.lbjt.game.box2d.bodies.BHorizontal
import com.education.lbjt.game.box2d.bodies.BVertical
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen

class BGBorders(override val screenBox2d: AdvancedBox2dScreen) : AbstractBodyGroup() {

    override val standartW = 700f

    val bTop   = BHorizontal(screenBox2d)
    val bDown  = BHorizontal(screenBox2d)
    val bLeft  = BVertical(screenBox2d)
    val bRight = BVertical(screenBox2d)

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        initB_Borders()

        createHorizontal()
        createVertical()
    }


    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Borders() {
        arrayOf(bTop, bDown, bLeft, bRight).onEach { it.apply {
            id = BodyId.BORDERS
            collisionList.addAll(arrayOf(
                BodyId.Menu.BUTTON,
                BodyId.Settings.VOLUME,
                BodyId.Settings.LANGUAGE,
                BodyId.AboutAuthor.ITEM,
                BodyId.Comment.ITEM,
                BodyId.Comment.DIALOG,
            ))
        } }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createHorizontal() {
        createBody(bTop, 0f, 1400f, 700f, 10f)
        createBody(bDown, 0f, -10f, 700f, 10f)
    }

    private fun createVertical() {
        createBody(bLeft, -10f, 0f, 10f, 1400f)
        createBody(bRight, 700f, 0f, 10f, 1400f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------


}