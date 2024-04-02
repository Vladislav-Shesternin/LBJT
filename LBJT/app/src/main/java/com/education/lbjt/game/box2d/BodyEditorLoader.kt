package com.education.lbjt.game.box2d

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue

class BodyEditorLoader {

    val internalModel: Model

    private val vectorPool: MutableList<Vector2?> = ArrayList()
    private val polygonShape = PolygonShape()
    private val circleShape  = CircleShape()

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------
    constructor(file: FileHandle?) {
        if (file == null) throw NullPointerException("file is null")
        internalModel = readJson(file.readString())
    }

    constructor(str: String?) {
        if (str == null) throw NullPointerException("str is null")
        internalModel = readJson(str)
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------
    fun attachFixture(body: Body, name: String, fd: FixtureDef, scale: Float = 1f) {
        val rbModel = internalModel.rigidBodies[name] ?: throw RuntimeException("Name '$name' was not found.")
        val origin = Vector2().set(rbModel.origin).scl(scale)
        run {
            var i = 0
            val n = rbModel.polygons.size
            while (i < n) {
                val polygon = rbModel.polygons[i]
                val vertices = polygon.buffer
                run {
                    var ii = 0
                    val nn = vertices.size
                    while (ii < nn) {
                        vertices[ii] = newVec().set(polygon.vertices[ii]).scl(scale)
                        vertices[ii]?.sub(origin)
                        ii++
                    }
                }
                polygonShape.set(vertices)
                fd.shape = polygonShape
                body.createFixture(fd)
                var ii = 0
                val nn = vertices.size
                while (ii < nn) {
                    free(vertices[ii])
                    ii++
                }
                i++
            }
        }
        var i = 0
        val n = rbModel.circles.size
        while (i < n) {
            val circle = rbModel.circles[i]
            val center = newVec().set(circle.center).scl(scale)
            val radius = circle.radius * scale
            circleShape.position = center
            circleShape.radius = radius
            fd.shape = circleShape
            body.createFixture(fd)
            free(center)
            i++
        }
    }

    fun getImagePath(name: String): String? {
        val rbModel = internalModel.rigidBodies[name] ?: throw RuntimeException("Name '$name' was not found.")
        return rbModel.imagePath
    }

    fun getOrigin(name: String, scale: Float = 1f): Vector2 {
        val rbModel = internalModel.rigidBodies[name] ?: throw RuntimeException("Name '$name' was not found.")
        return Vector2(rbModel.origin).scl(scale)
    }

    // -------------------------------------------------------------------------
    // Json Models
    // -------------------------------------------------------------------------
    class Model {
        val rigidBodies: MutableMap<String?, RigidBodyModel> = HashMap()
    }

    class RigidBodyModel {
        var name: String? = null
        var imagePath: String? = null
        val origin = Vector2()
        val polygons: MutableList<PolygonModel> = ArrayList()
        val circles: MutableList<CircleModel> = ArrayList()

        override fun toString(): String {
            return "name - $name | imagePath - $imagePath | origin - $origin | polygons - ${polygons.joinToString()} | circles - ${circles.joinToString()}"
        }
    }

    class PolygonModel {
        val vertices: MutableList<Vector2> = ArrayList()
        var buffer = arrayOf<Vector2?>()

        override fun toString(): String {
            return "vertices - ${vertices.joinToString()} | buffer - ${buffer.joinToString()}"
        }
    }

    class CircleModel {
        val center = Vector2()
        var radius = 0f

        override fun toString(): String {
            return "center - $center | radius - $radius"
        }
    }

    // -------------------------------------------------------------------------
    // Json reading process
    // -------------------------------------------------------------------------
    private fun readJson(str: String): Model {
        val m = Model()
        val map = JsonReader().parse(str)
        var bodyElem = map.getChild("rigidBodies")
        while (bodyElem != null) {
            val rbModel = readRigidBody(bodyElem)
            m.rigidBodies[rbModel.name] = rbModel
            bodyElem = bodyElem.next()
        }
        return m
    }

    private fun readRigidBody(bodyElem: JsonValue): RigidBodyModel {
        val rbModel = RigidBodyModel()
        rbModel.name = bodyElem.getString("name")
        rbModel.imagePath = bodyElem.getString("imagePath")
        val originElem = bodyElem["origin"]
        rbModel.origin.x = originElem.getFloat("x")
        rbModel.origin.y = originElem.getFloat("y")

        // polygons
        var polygonsElem = bodyElem.getChild("polygons")
        while (polygonsElem != null) {
            val polygon = PolygonModel()
            rbModel.polygons.add(polygon)
            var vertexElem = polygonsElem.child()
            while (vertexElem != null) {
                val x = vertexElem.getFloat("x")
                val y = vertexElem.getFloat("y")
                polygon.vertices.add(Vector2(x, y))
                vertexElem = vertexElem.next()
            }
            polygon.buffer = arrayOfNulls(polygon.vertices.size)
            polygonsElem = polygonsElem.next()
        }

        // circles
        var circleElem = bodyElem.getChild("circles")
        while (circleElem != null) {
            val circle = CircleModel()
            rbModel.circles.add(circle)
            circle.center.x = circleElem.getFloat("cx")
            circle.center.y = circleElem.getFloat("cy")
            circle.radius = circleElem.getFloat("r")
            circleElem = circleElem.next()
        }
        return rbModel
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------
    private fun newVec(): Vector2 {
        return if (vectorPool.isEmpty()) Vector2() else vectorPool.removeAt(0)!!
    }

    private fun free(v: Vector2?) {
        vectorPool.add(v)
    }

}