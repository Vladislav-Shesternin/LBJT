package com.education.lbjt.game.screens.practicalScreen

import com.education.lbjt.game.LibGDXGame
import com.education.lbjt.game.box2d.bodiesGroup.practical.AbstractBGPractical
import com.education.lbjt.game.box2d.bodiesGroup.practical.BGPractical_JMouse
import com.education.lbjt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.advanced.AdvancedPracticalScreen
import com.education.lbjt.game.utils.region

class Practical_JMouseScreen(override val game: LibGDXGame): AdvancedPracticalScreen(game) {

    // BodyGroup
    override val bgPractical: AbstractBGPractical = BGPractical_JMouse(this)

    override fun show() {
        stageUI.root.animHide()
        setUIBackground(game.themeUtil.assets.BACKGROUND.region)
        super.show()

        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA)
    }

}