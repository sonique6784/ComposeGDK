package me.sonique.common

import me.sonique.common.core.CGDKObject
import org.openrndr.math.Vector2


//       y
//       ^
//       |
// ------+-------> x
//       |

class Placement(var gameCanvasSize: Vector2) {

    fun toCenter(gameObject: CGDKObject): Vector2 {
        val y = (gameCanvasSize.y / 2) - (gameObject.mutableSize.value.y / 2)
        val x = (gameCanvasSize.x / 2) - (gameObject.mutableSize.value.x / 2)

        return Vector2(x, y)
    }

    fun toCenterLeft(gameObject: CGDKObject): Vector2 {
        val x = 0.0
        val y = (gameCanvasSize.y / 2) - (gameObject.mutableSize.value.y / 2)

        return Vector2(x, y)
    }

    fun toBottomLeft(gameObject: CGDKObject): Vector2 {
        val x = 0.0
        val y = gameCanvasSize.y - gameObject.mutableSize.value.y

        return Vector2(x, y)
    }

    fun toTopLeft(gameObject: CGDKObject): Vector2 {
        val x = 0.0
        val y = 0.0

        return Vector2(x, y)
    }

    fun toTopRight(gameObject: CGDKObject): Vector2 {
        val x = gameCanvasSize.x - gameObject.mutableSize.value.x
        val y = 0.0

        return Vector2(x, y)
    }

    fun toCenterRight(gameObject: CGDKObject): Vector2 {
        val x = gameCanvasSize.x - gameObject.mutableSize.value.x
        val y = (gameCanvasSize.y / 2) - (gameObject.mutableSize.value.y / 2)

        return Vector2(x, y)
    }

    fun toTopCenter(gameObject: CGDKObject): Vector2 {
        val x = (gameCanvasSize.x / 2) - (gameObject.mutableSize.value.x / 2)
        val y = 0.0

        return Vector2(x, y)
    }

    fun toBottomCenter(gameObject: CGDKObject): Vector2 {
        val x = (gameCanvasSize.x / 2) - (gameObject.mutableSize.value.x / 2)
        val y = gameCanvasSize.y - gameObject.mutableSize.value.y

        return Vector2(x, y)
    }

    fun toBottomRight(gameObject: CGDKObject): Vector2 {
        val x = gameCanvasSize.x - gameObject.mutableSize.value.x
        val y = gameCanvasSize.y - gameObject.mutableSize.value.y

        return Vector2(x, y)
    }

    fun putObjectAOnTopB(gameObjectA: CGDKObject, gameObjectB: CGDKObject): Vector2 {
        val x = gameObjectA.mutablePosition.value.x
        val y = gameObjectB.mutablePosition.value.y - gameObjectA.mutableSize.value.y

        return Vector2(x, y)
    }
}
