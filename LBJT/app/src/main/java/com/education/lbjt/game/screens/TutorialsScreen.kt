package com.education.lbjt.game.screens

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.education.lbjt.game.LibGDXGame
import com.education.lbjt.game.box2d.bodiesGroup.BGLift
import com.education.lbjt.game.box2d.destroyAll
import com.education.lbjt.game.screens.tutorialsScreen.GeneralInformationScreen
import com.education.lbjt.game.screens.tutorialsScreen.JDistanceScreen
import com.education.lbjt.game.screens.tutorialsScreen.JFrictionScreen
import com.education.lbjt.game.screens.tutorialsScreen.JGearScreen
import com.education.lbjt.game.screens.tutorialsScreen.JMouseScreen
import com.education.lbjt.game.screens.tutorialsScreen.JPrismaticScreen
import com.education.lbjt.game.screens.tutorialsScreen.JPulleyScreen
import com.education.lbjt.game.screens.tutorialsScreen.JRevoluteScreen
import com.education.lbjt.game.screens.tutorialsScreen.JRopeScreen
import com.education.lbjt.game.screens.tutorialsScreen.JWeldScreen
import com.education.lbjt.game.screens.tutorialsScreen.JWheelScreen
import com.education.lbjt.game.screens.tutorialsScreen.WillBeLaterScreen
import com.education.lbjt.game.utils.TIME_ANIM_SCREEN_ALPHA
import com.education.lbjt.game.utils.actor.animHide
import com.education.lbjt.game.utils.actor.animShow
import com.education.lbjt.game.utils.actor.setOnTouchListener
import com.education.lbjt.game.utils.advanced.AdvancedMouseScreen
import com.education.lbjt.game.utils.advanced.AdvancedStage
import com.education.lbjt.game.utils.region
import com.education.lbjt.game.screens.tutorialsScreen.JMotorScreen

class TutorialsScreen(override val game: LibGDXGame): AdvancedMouseScreen(game) {

    // Actor
    private val aHand = Image(game.themeUtil.assets.HAND_HINT)

    // BodyGroup
    private val bgLift = BGLift(this)

    override fun show() {
        stageUI.root.animHide()
        setUIBackground(game.themeUtil.assets.BACKGROUND.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createBG_Lift()

        addHand()

        stageUI.root.animShow(TIME_ANIM_SCREEN_ALPHA) {
            animHand { bgLift.destroyMotorJoint() }
        }
    }

    override fun dispose() {
        listOf(bgLift).destroyAll()
        super.dispose()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addHand() {
        addActor(aHand)
        aHand.apply {
            val regionHandFlip = TextureRegion(game.themeUtil.assets.HAND_HINT).apply { flip(true, false) }
            drawable = TextureRegionDrawable(regionHandFlip)

            setBounds(700f, 1112f, 100f, 116f)
            setOrigin(98f, 34f)
            animHide()
        }

    }

    // ------------------------------------------------------------------------
    // Create Body Group
    // ------------------------------------------------------------------------

    private fun createBG_Lift() {
        bgLift.create(20f,203f,654f,1244f)

        val screenNameList = listOf(
            GeneralInformationScreen::class.java.name,
            JMouseScreen::class.java.name,
            JDistanceScreen::class.java.name,
            JRevoluteScreen::class.java.name,
            JPrismaticScreen::class.java.name,
            JWheelScreen::class.java.name,
            JWeldScreen::class.java.name,
            JFrictionScreen::class.java.name,
            JRopeScreen::class.java.name,
            JPulleyScreen::class.java.name,
            JGearScreen::class.java.name,
            JMotorScreen::class.java.name,
        )

        bgLift.bgRegularBtns.bRegularBtnList.onEachIndexed { index, bRegularBtn ->
            bRegularBtn.actor?.setOnTouchListener(radius = 20) { navigateTo(screenNameList[index]) }
        }
    }

    // ---------------------------------------------------
    // Anim
    // ---------------------------------------------------

    private fun animHand(endBlock: () -> Unit) {
        aHand.addAction(
            Actions.sequence(
                Actions.parallel(
                    Actions.fadeIn(0.5f),
                    Actions.moveTo(622f, 1112f, 0.5f)
                ),
                Actions.delay(0.2f),
                Actions.parallel(
                    Actions.moveBy(15f, -50f, 0.6f),
                    Actions.rotateBy(30f, 0.4f)
                ),
                Actions.run(endBlock),
                Actions.moveBy(200f, 100f, 0.3f)
            ))
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun navigateTo(screenName: String) {
        stageUI.root.animHide(TIME_ANIM_SCREEN_ALPHA) { game.navigationManager.navigate(screenName, TutorialsScreen::class.java.name) }
    }

}