package me.sonique.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import me.sonique.common.controller.event.IOnKeyUp


/**
 * VerticalKeyboardScroll
 *
 * @param initialPositionX : where the object should be placed
 * @param speed : at what speed the object travels (in DP / second)
 * @param direction : which direction the object travel to: left or right
 *
 */
class VerticalKeyboardScroll(
    speed: Int = 10,
    direction: Direction = Direction.UP,
) : VerticalScroll(speed, direction) {

    private val mainScope = CoroutineScope(Dispatchers.Main)

    private var distance: Int = 0

    fun distanceToMove(distance: Int = speed) {
        //this.distance = distance
        for (cgdkObject in this.objectList) {
            if( cgdkObject is IOnKeyUp) {
                cgdkObject.onKeyUp()
            }
        }
    }

    override fun postMoveDistance() {
        this.distance = 0

    }

//
//    fun jumpAnimation() {
//        AnimationHelper.jump(objectList, 45, AnimationHelper.SPEED_NORMAL)
//    }

    /**
     * implements moveDistance and based the distance
     * travelled with the keyboard
     * @return Int (distance in dp)
     */
    override fun moveDistance(): Int {
        return when (direction) {
            Direction.UP -> {
                distance
            }
            Direction.DOWN -> {
                -distance
            }
            else -> 0
        }
    }
}