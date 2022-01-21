package me.sonique.common.core

import org.openrndr.math.Vector2

/**
 * ImageCGDKObject
 * Image game object, inherit from CGDKObject
 * 
 * @param imageFileName: String - path to the file relative to /resources
 * @param isTexture: Boolean = false - allow to repeat the image on the surface
 * @param size: Vector2
 * @param position: Vector2
 */
open class ImageCGDKObject(
    val imageFileName: String,
    val isTexture: Boolean = false,
    size: Vector2 = Vector2(0.0, 0.0),
    position: Vector2 = Vector2(0.0, 0.0)
) : CGDKObject(size, position)