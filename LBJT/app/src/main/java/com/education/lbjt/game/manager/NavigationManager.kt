package com.education.lbjt.game.manager

import com.badlogic.gdx.Gdx
import com.education.lbjt.game.LibGDXGame
import com.education.lbjt.game.screens.AboutAuthorScreen
import com.education.lbjt.game.screens.CommentsScreen
import com.education.lbjt.game.screens.LoaderScreen
import com.education.lbjt.game.screens.MenuScreen
import com.education.lbjt.game.screens.SettingsScreen
import com.education.lbjt.game.screens.TutorialIntroductionScreen
import com.education.lbjt.game.screens.TutorialsScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JDistanceScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JFrictionScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JGearScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JMouseScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JPrismaticScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JPulleyScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JRevoluteScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JRopeScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JWeldScreen
import com.education.lbjt.game.screens.practicalScreen.Practical_JWheelScreen
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
import com.education.lbjt.game.utils.advanced.AdvancedScreen
import com.education.lbjt.game.utils.runGDX
import com.education.lbjt.game.screens.tutorialsScreen.JMotorScreen

class NavigationManager(val game: LibGDXGame) {

    private val backStack = mutableListOf<String>()
    var key: Int? = null
        private set

    fun navigate(toScreenName: String, fromScreenName: String? = null, key: Int? = null) = runGDX {
        this.key = key

        game.updateScreen(getScreenByName(toScreenName))
        backStack.filter { name -> name == toScreenName }.onEach { name -> backStack.remove(name) }
        fromScreenName?.let { fromName ->
            backStack.filter { name -> name == fromName }.onEach { name -> backStack.remove(name) }
            backStack.add(fromName)
        }
    }

    fun back(key: Int? = null) = runGDX {
        this.key = key

        if (isBackStackEmpty()) exit() else game.updateScreen(getScreenByName(backStack.removeLast()))
    }


    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoaderScreen              ::class.java.name -> LoaderScreen(game)
        MenuScreen                ::class.java.name -> MenuScreen(game)
        TutorialIntroductionScreen::class.java.name -> TutorialIntroductionScreen(game)
        SettingsScreen            ::class.java.name -> SettingsScreen(game)
        AboutAuthorScreen         ::class.java.name -> AboutAuthorScreen(game)
        CommentsScreen            ::class.java.name -> CommentsScreen(game)
        TutorialsScreen           ::class.java.name -> TutorialsScreen(game)
        // Tutorials Screens
        GeneralInformationScreen::class.java.name -> GeneralInformationScreen(game)
        JMouseScreen            ::class.java.name -> JMouseScreen(game)
        JDistanceScreen         ::class.java.name -> JDistanceScreen(game)
        JRevoluteScreen         ::class.java.name -> JRevoluteScreen(game)
        JPrismaticScreen        ::class.java.name -> JPrismaticScreen(game)
        JWheelScreen            ::class.java.name -> JWheelScreen(game)
        JWeldScreen             ::class.java.name -> JWeldScreen(game)
        JFrictionScreen         ::class.java.name -> JFrictionScreen(game)
        JRopeScreen             ::class.java.name -> JRopeScreen(game)
        JPulleyScreen           ::class.java.name -> JPulleyScreen(game)
        JGearScreen             ::class.java.name -> JGearScreen(game)
        JMotorScreen            ::class.java.name -> JMotorScreen(game)

        WillBeLaterScreen::class.java.name -> WillBeLaterScreen(game)
        // Practical Screens
        Practical_JMouseScreen    ::class.java.name -> Practical_JMouseScreen(game)
        Practical_JDistanceScreen ::class.java.name -> Practical_JDistanceScreen(game)
        Practical_JRevoluteScreen ::class.java.name -> Practical_JRevoluteScreen(game)
        Practical_JPrismaticScreen::class.java.name -> Practical_JPrismaticScreen(game)
        Practical_JWheelScreen    ::class.java.name -> Practical_JWheelScreen(game)
        Practical_JWeldScreen     ::class.java.name -> Practical_JWeldScreen(game)
        Practical_JFrictionScreen ::class.java.name -> Practical_JFrictionScreen(game)
        Practical_JRopeScreen     ::class.java.name -> Practical_JRopeScreen(game)
        Practical_JPulleyScreen   ::class.java.name -> Practical_JPulleyScreen(game)
        Practical_JGearScreen     ::class.java.name -> Practical_JGearScreen(game)



        else -> MenuScreen(game)
    }

}