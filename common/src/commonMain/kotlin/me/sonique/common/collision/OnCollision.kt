package me.sonique.common.collision

import me.sonique.common.core.CGDKObject

/**
 * interface to implement to know when an object is in collision
 */
interface OnCollision {
    fun onCollision(object2: CGDKObject)
    //fun onCollision(gameObjectA: GameObject2, gameObjectB: GameObject2)
}