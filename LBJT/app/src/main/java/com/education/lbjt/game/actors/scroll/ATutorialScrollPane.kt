package com.education.lbjt.game.actors.scroll

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class ATutorialScrollPane constructor(
    override val screen: AdvancedScreen,
    val advancedGroup: AdvancedGroup,
): AdvancedGroup() {

    // Actor
    val scroll = ScrollPane(advancedGroup)

    // Field
    private var prevPercentY = 0f
    var scrollNextBlock      = Static.ScrollBlock { }

    override fun addActorsOnGroup() {
        addAndFillActor(scroll)

        checkIsScroll()
    }

    override fun dispose() {
        advancedGroup.dispose()
        super.dispose()
    }


    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun checkIsScroll() {
        postDrawArray.add(AdvancedGroup.Static.Drawer {
            scroll.scrollPercentY.also { percentY ->
                if (percentY != prevPercentY) {
                    scrollNextBlock.block(percentY > prevPercentY)
                    prevPercentY = percentY
                }
            }

        })
    }

    object Static {
        fun interface ScrollBlock {
            fun block(isScrollNext: Boolean)
        }
    }

}