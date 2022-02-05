package me.sonique.common.core
import kotlin.math.sqrt

class Vector2(val x: Double, val y: Double) {

    fun distanceTo(other: Vector2): Double {
        val dx = other.x - x
        val dy = other.y - y
        return sqrt(dx * dx + dy * dy)
    }

    fun lower(distanceY: Int): Vector2 {
        return Vector2(this.x, this.y + distanceY)
    }
    fun higher(distanceY: Int): Vector2 {
        return Vector2(this.x, this.y - distanceY)
    }

    fun leftShift(distanceX: Int): Vector2 {
        return Vector2(this.x - distanceX, this.y)
    }

    fun rightShift(distanceX: Int): Vector2 {
        return Vector2(this.x + distanceX, this.y)
    }
}