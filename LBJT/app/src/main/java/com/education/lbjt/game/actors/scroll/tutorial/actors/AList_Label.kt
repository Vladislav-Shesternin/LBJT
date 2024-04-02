package com.education.lbjt.game.actors.scroll.tutorial.actors

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.education.lbjt.game.actors.scroll.HorizontalGroup
import com.education.lbjt.game.utils.GameColor
import com.education.lbjt.game.utils.advanced.AdvancedScreen

class AList_Label(
    _screen : AdvancedScreen,
    font        : BitmapFont,
    override val strings: List<String>,
    override val align  : Static.Align  = Static.Align.Left,
    override val symbol : Static.Symbol = Static.Symbol.Bullet,
    override val symbolFont: BitmapFont
): AAbstractList(_screen) {

    // Actor
    private val labels = List(strings.size) { Label(strings[it], Label.LabelStyle(font, GameColor.textGreen)) }

    // Field
    private val symbolLblStyle = Label.LabelStyle(symbolFont, GameColor.textGreen)


    override fun addActorsOnGroup() {
        addAndFillActor(verticalGroup)

        var tmpHorizontalGroup: HorizontalGroup
        var tmpSymbolLbl      : Label

        labels.onEach { lbl ->
            tmpHorizontalGroup = HorizontalGroup(screen)
            tmpSymbolLbl       = Label(symbol.getSymbol(), symbolLblStyle)

            when (align) {
                Static.Align.Left   -> {
                    tmpHorizontalGroup.width = width
                    lbl.width                = (width - tmpSymbolLbl.prefWidth)
                }
                Static.Align.Center -> {
                    tmpHorizontalGroup.width = width * 0.7f
                    tmpHorizontalGroup.x     = width * 0.3f
                    lbl.width                = ((width * 0.7f) - tmpSymbolLbl.prefWidth)
                }
            }
            lbl.wrap                  = true
            lbl.height                = lbl.prefHeight
            tmpHorizontalGroup.height = lbl.prefHeight
            tmpSymbolLbl.apply {
                width  = prefWidth
                height = lbl.prefHeight
                setAlignment(Align.top)
            }

            tmpHorizontalGroup.addActors(tmpSymbolLbl, lbl)
            verticalGroup.addActor(tmpHorizontalGroup)
        }

        height = verticalGroup.prefHeight
    }

}