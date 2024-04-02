package com.education.lbjt.game.box2d

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Joint
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Array

private val tmpArrayBody  = Array<Body>()
private val tmpArrayJoint = Array<Joint>()

fun World.bodies(): Array<Body> {
    getBodies(tmpArrayBody)
    return tmpArrayBody
}

fun World.joints(): Array<Joint> {
    getJoints(tmpArrayJoint)
    return tmpArrayJoint
}

fun Collection<Destroyable>.destroyAll() = onEach { it.destroy() }

interface Destroyable {
    fun destroy()
}