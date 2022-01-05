package me.sonique.common.core

import androidx.compose.runtime.mutableStateOf
import org.openrndr.math.Vector2

/**
 * C G D K
 * Compose Game Development Kit
 * TODO rename to cgdkObject
 */
open class CGDKObject(
    size: Vector2 = Vector2(0.0, 0.0),
    position: Vector2 = Vector2(0.0, 0.0)
) {
    val mutableSize = mutableStateOf<Vector2>(size)
    val mutablePosition = mutableStateOf<Vector2>(position)
}