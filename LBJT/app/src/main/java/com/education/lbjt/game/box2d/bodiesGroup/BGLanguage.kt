package com.education.lbjt.game.box2d.bodiesGroup

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.WeldJoint
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.education.lbjt.game.box2d.AbstractBodyGroup
import com.education.lbjt.game.box2d.AbstractJoint
import com.education.lbjt.game.box2d.BodyId.Settings.LANGUAGE
import com.education.lbjt.game.box2d.bodies.standart.BDynamic
import com.education.lbjt.game.box2d.bodies.BLanguage
import com.education.lbjt.game.box2d.bodies.standart.BStatic
import com.education.lbjt.game.box2d.util.CheckGroupBGLanguage

import com.education.lbjt.game.utils.JOINT_WIDTH
import com.education.lbjt.game.utils.SizeStandardizer
import com.education.lbjt.game.utils.actor.setOnTouchListener
import com.education.lbjt.game.utils.advanced.AdvancedBox2dScreen
import com.education.lbjt.game.utils.advanced.AdvancedGroup
import com.education.lbjt.game.utils.toUI
import com.education.lbjt.util.Once

class BGLanguage(
    override val screenBox2d: AdvancedBox2dScreen,
    val type: BLanguage.Static.Type,
): AbstractBodyGroup() {

    override val standartW = 202f

    // Body
    private val bDynamic  = BDynamic(screenBox2d)
    private val bStatic   = BStatic(screenBox2d)
    private val bLanguage = BLanguage(screenBox2d, type)

    // Joint
    private val jWeld_DynamicLanguage = AbstractJoint<WeldJoint, WeldJointDef>(screenBox2d)
    private val jWeld_DynamicStatic   = AbstractJoint<WeldJoint, WeldJointDef>(screenBox2d)

    // Field
    private val drawer = screenBox2d.drawerUtil.drawer

    private val onceFirstSizeStandardizer     = Once()
    private val firstSizeStandardizer         = SizeStandardizer()
    private val Vector2.toFirstStandart get() = firstSizeStandardizer.scope { toStandart }
    private val Float.toFirstStandart   get() = firstSizeStandardizer.scope { toStandart }

    private var onCheckBlock: (Boolean) -> Unit = {}
    var checkGroupBGLanguage: CheckGroupBGLanguage? = null
    var isCheck = true
        private set

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        onceFirstSizeStandardizer.once { firstSizeStandardizer.standardize(standartW, size.x) }

        initB_Dynamic()
        initB_Language()

        createB_Dynamic()
        createB_Static()
        createB_Language()

        createJ_Weld_DynamicLanguage()
        createJ_Weld_DynamicStatic()
    }

    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Dynamic() {
        bDynamic.apply {
            fixtureDef.density = 15f
        }
    }

    private fun initB_Language() {
        bLanguage.apply {
            id  = LANGUAGE
            collisionList.addAll(arrayOf(LANGUAGE))
        }

        bLanguage.actor?.setOnTouchListener {
            if (checkGroupBGLanguage != null) {
                if (isCheck.not()) check()
            } else {
                if (isCheck) uncheck() else check()
            }
        }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Dynamic() {
        createBody(bDynamic, 66f, if (isCheck) 330f else 410f, 70f, 70f)
    }

    private fun createB_Static() {
        createBody(bStatic, 101f, 0f, 1f, 1f)
    }

    private fun createB_Language() {
        createBody(bLanguage, 0f, if (isCheck) 233f else 313f, 202f, 265f)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Weld_DynamicStatic() {
        createJoint(jWeld_DynamicStatic, WeldJointDef().apply {
            bodyA = bStatic.body
            bodyB = bDynamic.body

            localAnchorB.set(Vector2(35f, if (isCheck) -330f else -410f).subCenter(bodyB))

            frequencyHz  = 10f
            dampingRatio = 0.7f

            bLanguage.actor?.apply {
                preDrawArray.clear()
                preDrawArray.add(AdvancedGroup.Static.Drawer { alpha -> drawer.line(bodyA.position.toUI, bodyB.position.toUI, colorJoint.apply { a = alpha }, JOINT_WIDTH) })
            }
        })
    }

    private fun createJ_Weld_DynamicLanguage() {
        createJoint(jWeld_DynamicLanguage, WeldJointDef().apply {
            bodyA = bDynamic.body
            bodyB = bLanguage.body

            localAnchorB.set(Vector2(101f, 132f).subCenter(bodyB))

            frequencyHz  = 1f
            dampingRatio = 0.5f
        })
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun setOnCheckBlock(block: (Boolean) -> Unit) {
        onCheckBlock = block
    }

    fun check(isInvokeCheckBlock: Boolean = true) {
        if (isCheck) return

        checkGroupBGLanguage?.let {
            it.currentCheckedBGLanguage?.uncheck(isInvokeCheckBlock)
            it.currentCheckedBGLanguage = this
        }

        bLanguage.increase {
            if (isInvokeCheckBlock) onCheckBlock(true)

            // (isDisposed = true) - означає що актора очищене і при destroy() групи актора не буде очищено
            bLanguage.getActor()?.isDisposed = true
            bLanguage.isDestroyActor = false
            destroy()

            isCheck = true

            val pos = position.sub(Vector2(25f, 0f).toFirstStandart)
            val siz = size.add(Vector2(51f, 66f).toFirstStandart)

            create(pos.x, pos.y, siz.x, siz.y)
            // (isDisposed = false) - означає що актора можна очищати при destroy() групи
            bLanguage.getActor()?.isDisposed = false

            bLanguage.actor?.addAction(Actions.scaleTo(1f, 1f))
            if (isInvokeCheckBlock) screenBox2d.game.soundUtil.apply { play(ELECTRICITY) }
        }
    }

    fun uncheck(isInvokeCheckBlock: Boolean = true) {
        if (isCheck.not()) return

        bLanguage.decrease {
            if (isInvokeCheckBlock) onCheckBlock(false)

            // (isDisposed = true) - означає що актора очищене і при destroy() групи актора не буде очищено
            bLanguage.getActor()?.isDisposed = true
            bLanguage.isDestroyActor = false
            destroy()

            isCheck = false

            val pos = position.add(Vector2(25f, 0f).toFirstStandart)
            val siz = size.sub(Vector2(51f, 66f).toFirstStandart)

            create(pos.x, pos.y, siz.x, siz.y)
            // (isDisposed = false) - означає що актора можна очищати при destroy() групи
            bLanguage.getActor()?.isDisposed = false

            bLanguage.actor?.addAction(Actions.scaleTo(1f, 1f))
        }
    }

}