package com.education.lbjt.game.utils.advanced

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Disposable
import com.education.lbjt.game.utils.SizeStandardizer
import com.education.lbjt.game.utils.actor.setFillParent
import com.education.lbjt.game.utils.advanced.AdvancedGroup.Static.AlignmentHorizontal
import com.education.lbjt.game.utils.advanced.AdvancedGroup.Static.AlignmentVertical
import com.education.lbjt.game.utils.disposeAll
import com.education.lbjt.util.Once
import com.education.lbjt.util.cancelCoroutinesAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class AdvancedGroup : WidgetGroup(), Disposable {
    abstract val screen: AdvancedScreen

    open var standartW: Float = 1f

    var coroutine: CoroutineScope? = CoroutineScope(Dispatchers.Default)
        private set
    var isDisposed = false

    private val standardizer = SizeStandardizer()

    val Vector2.toStandart get() = standardizer.scope { toStandart }
    val Float.toStandart   get() = standardizer.scope { toStandart }

    val preDrawArray  = Array<Static.Drawer>()
    val postDrawArray = Array<Static.Drawer>()
    val disposableSet = mutableSetOf<Disposable>()

    private val onceInit = Once()

    abstract fun addActorsOnGroup()

    override fun draw(batch: Batch?, parentAlpha: Float) {
        preDrawArray.forEach { it.draw(parentAlpha * this@AdvancedGroup.color.a) }
        super.draw(batch, parentAlpha)
        postDrawArray.forEach { it.draw(parentAlpha * this@AdvancedGroup.color.a) }
    }


    override fun sizeChanged() {
        standardizer.standardize(standartW, width)
        onceInit.once { addActorsOnGroup() }
    }

    override fun dispose() {
        if (isDisposed.not()) {
            preDrawArray.clear()
            postDrawArray.clear()

            disposableSet.disposeAll()
            disposableSet.clear()

            disposeChildren()

            cancelCoroutinesAll(coroutine)
            coroutine = null

            isDisposed = true
        }
    }

    fun disposeChildren() {
        children.onEach { actor -> if (actor is Disposable) actor.dispose() }
        clearChildren()
    }

    override fun removeActorAt(index: Int, unfocus: Boolean): Actor {
        children[index].also { if(it is Disposable) it.dispose() }
        return super.removeActorAt(index, unfocus)
    }

    fun addAlignActor(
        actor: Actor,
        alignmentHorizontal: AlignmentHorizontal = AlignmentHorizontal.START,
        alignmentVertical: AlignmentVertical = AlignmentVertical.BOTTOM,
    ) {
        addActor(actor)

        // START | BOTTOM (DEFAULT)
        var newX = 0f
        var newY = 0f

        when (alignmentHorizontal to alignmentVertical) {
            AlignmentHorizontal.START to AlignmentVertical.CENTER  -> {
                newY = (height / 2) - (actor.height / 2)
            }
            AlignmentHorizontal.START to AlignmentVertical.TOP     -> {
                newY = height - actor.height
            }
            AlignmentHorizontal.CENTER to AlignmentVertical.BOTTOM -> {
                newX = (width / 2) - (actor.width / 2)
            }
            AlignmentHorizontal.CENTER to AlignmentVertical.CENTER -> {
                newX = (width / 2) - (actor.width / 2)
                newY = (height / 2) - (actor.height / 2)
            }
            AlignmentHorizontal.CENTER to AlignmentVertical.TOP    -> {
                newX = (width / 2) - (actor.width / 2)
                newY = height - actor.height
            }
            AlignmentHorizontal.END to AlignmentVertical.BOTTOM    -> {
                newX = width - actor.width
            }
            AlignmentHorizontal.END to AlignmentVertical.CENTER    -> {
                newX = width - actor.width
                newY = (height / 2) - (actor.height / 2)
            }
            AlignmentHorizontal.END to AlignmentVertical.TOP       -> {
                newX = width - actor.width
                newY = height - actor.height
            }
        }
        actor.setPosition(newX, newY)
    }

    fun addAndFillActor(actor: Actor) {
        addActor(actor)
        when (actor) {
            is Widget      -> actor.setFillParent(true)
            is WidgetGroup -> actor.setFillParent(true)
        }
    }

    fun addAndFillActors(actors: List<Actor>) {
        actors.forEach { addActor(it) }
        actors.setFillParent()
    }

    fun addAndFillActors(vararg actors: Actor) {
        actors.forEach { addActor(it) }
        actors.toList().setFillParent()
    }

    fun addActors(vararg actors: Actor) {
        actors.forEach { addActor(it) }
    }

    fun addActors(actors: List<Actor>) {
        actors.forEach { addActor(it) }
    }

    protected fun Actor.setBoundsStandart(x: Float, y: Float, width: Float, height: Float) {
        setBounds(x.toStandart, y.toStandart, width.toStandart, height.toStandart)
    }

    protected fun Actor.setBoundsStandart(position: Vector2, size: Vector2) {
        setBoundsStandart(position.x, position.y, size.x, size.y)
    }

    object Static {
        enum class AlignmentHorizontal { START, CENTER, END, }
        enum class AlignmentVertical { BOTTOM, CENTER, TOP, }

        fun interface Drawer {
            fun draw(alpha: Float)
        }
    }

}