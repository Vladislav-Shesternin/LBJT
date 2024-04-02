package com.education.lbjt.game.box2d

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.Joint
import com.badlogic.gdx.physics.box2d.JointDef
import com.badlogic.gdx.utils.Array
import com.education.lbjt.game.utils.*
import com.education.lbjt.game.utils.actor.setBounds
import com.education.lbjt.game.utils.actor.setOrigin
import com.education.lbjt.game.utils.actor.setPosition
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.util.cancelCoroutinesAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class AbstractBody: Destroyable {

    abstract val screenBox2d: AdvancedBox2dScreen
    abstract val name       : String
    abstract val bodyDef    : BodyDef
    abstract val fixtureDef : FixtureDef

    open var actor     : AdvancedGroup? = null
    open val collisionList          = mutableListOf<String>()

    open var originalId: String = BodyId.NONE
    open var id        : String = BodyId.NONE
        set(value) {
            if (isSetId.not()) {
                isSetId = true
                originalId = value
            }
            field = value
        }

    private var isSetId = false
    private val tmpVector2 = Vector2()

    val size       = Vector2()
    val position   = Vector2()

    var body: Body? = null
        private set
    var scale = 0f
        private set
    var center = tmpVector2
        private set
    var coroutine: CoroutineScope? = null
        private set

    var beginContactBlockArray = Array<ContactBlock>()
    var endContactBlockArray   = Array<ContactBlock>()
    var renderBlockArray       = Array<RenderBlock>()

    var isDestroyActor = true

    open fun render(deltaTime: Float) {
        renderBlockArray.onEach { it.block(deltaTime) }
        transformActor()
    }

    open fun beginContact(contactBody: AbstractBody) = beginContactBlockArray.onEach { it.block(contactBody) }

    open fun endContact(contactBody: AbstractBody) = endContactBlockArray.onEach { it.block(contactBody) }

    override fun destroy() {
        if (body != null) {
            id = BodyId.NONE

            cancelCoroutinesAll(coroutine)
            coroutine = null

            if (isDestroyActor) {
                actor?.dispose()
                actor = null
            }

            body?.jointList?.map { (it.joint.userData as AbstractJoint<out Joint, out JointDef>) }?.destroyAll()

            screenBox2d.worldUtil.world.destroyBody(body)
            body = null

            collisionList.clear()
            renderBlockArray.clear()
            beginContactBlockArray.clear()
            endContactBlockArray.clear()
        }
    }

    fun create(x: Float, y: Float, w: Float, h: Float) {
        if (body == null) {
            position.set(x, y)
            size.set(w, h)
            scale  = size.x.toB2
            center = screenBox2d.worldUtil.bodyEditor.getOrigin(name, scale)

            bodyDef.position.set(tmpVector2.set(position).toB2.add(center))

            body = screenBox2d.worldUtil.world.createBody(bodyDef).apply { userData = this@AbstractBody }
            screenBox2d.worldUtil.bodyEditor.attachFixture(body!!, name, fixtureDef, scale)

            coroutine = CoroutineScope(Dispatchers.Default)
            addActor()

            isDestroyActor = true
        }
    }

    fun create(position: Vector2, size: Vector2) {
        create(position.x, position.y, size.x, size.y)
    }

    private fun addActor() {
        actor?.apply {
            screenBox2d.stageUI.addActor(this)
            setBounds(position, size)
        }
    }

    private fun transformActor() {
        body?.let { b ->
            actor?.apply {
                setPosition(tmpVector2.set(b.position).sub(center).toUI)
                setOrigin(tmpVector2.set(center).toUI)
                rotation = b.angle * RADTODEG
            }
        }
    }

    fun setNoneId() {
        id = BodyId.NONE
    }

    fun setOriginalId() {
        id = originalId
    }

    // ---------------------------------------------------
    // SAM
    // ---------------------------------------------------

    fun interface ContactBlock { fun block(body: AbstractBody) }
    fun interface RenderBlock { fun block(deltaTime: Float) }

}