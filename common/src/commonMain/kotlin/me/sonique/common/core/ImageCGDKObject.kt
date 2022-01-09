package me.sonique.common.core

import org.openrndr.math.Vector2

open class ImageCGDKObject(
    val imageFileName: String,
    val isTexture: Boolean = false,
    size: Vector2 = Vector2(0.0, 0.0),
    position: Vector2 = Vector2(0.0, 0.0)
) : CGDKObject(size, position)