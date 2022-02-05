package me.sonique.common.scroller

import me.sonique.common.core.CGDKObject
import me.sonique.common.core.Vector2


/**
 * HorizontalScroll
 * Help to scroll/move multiple objects at the same speed 
 * Ideal to move objects, maps, decors and background Horizontally
 *
 * @param initialPositionX : where the object should be placed
 * @param speed : at what speed the object travels (in DP / second)
 * @param direction : which direction the object travel to: left or right
 *
 */
abstract class HorizontalScroll(
    var speed: Int = 10,
    var direction: Direction = Direction.LEFT,
) {

    private var objectList: MutableList<CGDKObject> = mutableListOf()

    /**
     * addObject
     * add an object to move
     * @param CGDKObject
     */
    fun addObject(CGDKObject: CGDKObject) {
        objectList.add(CGDKObject)
    }

    /**
     * deleteObject
     * stop moving this object
     * @param CGDKObject
     */
    fun deleteObject(CGDKObject: CGDKObject) {
        objectList.remove(CGDKObject)
    }

    /**
     * removeNotVisibleObjects
     * remove all objects that are not visible anymore (no need to move them)
     */
    private fun removeNotVisibleObjects() {
        val objectIterator = objectList.iterator()
        while (objectIterator.hasNext()) {
            val gameObject = objectIterator.next()
            if ((gameObject.mutablePosition.value.x + gameObject.mutableSize.value.x) < 0) {
                objectIterator.remove()
            }
        }
    }

    /**
     * moveDistance
     * implement this method to tell this component
     * how much the items should move
     * @return Int (distance in dp)
     */
    abstract fun moveDistance(): Int

    /**
     * postMoveDistance
     * implement this method to tell this component
     * what to do once the distance has been provided
     * (you can reset the distance for instance)
     * @return Int (distance in dp)
     */
    abstract fun postMoveDistance()

    /**
     * update
     * process the update of all objects
     * move the objects according the parameters (speed and direction)
     */
    fun update() {
        val moveSize = moveDistance().run {
                when (direction) {
                Direction.LEFT -> this * -1
                else -> this
            }
        }

        this.postMoveDistance()

        // move to the left
        objectList.forEach {
            it.mutablePosition.value = Vector2(
                it.mutablePosition.value.x - moveSize.toInt(),
                it.mutablePosition.value.y
            )
        }

        removeNotVisibleObjects()
    }

    enum class Direction {
        LEFT,
        RIGHT
    }
}