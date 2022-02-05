package me.sonique.common.core

import androidx.compose.runtime.mutableStateOf
import me.sonique.common.core.Vector2

/**
 * CGDKObject
 * most basic game object
 * 
 * @param size: Vector2
 * @param position: Vector2
 */
open class CGDKObject(
    size: Vector2 = Vector2(0.0, 0.0),
    position: Vector2 = Vector2(0.0, 0.0)
) {
    val mutableSize = mutableStateOf<Vector2>(size)
    val mutablePosition = mutableStateOf<Vector2>(position)

    override fun toString(): String {
        return "size (w: ${mutableSize.value.x}, h: ${mutableSize.value.y}) ; position(x: ${mutablePosition.value.x}, y: ${mutablePosition.value.y})"
    }
}