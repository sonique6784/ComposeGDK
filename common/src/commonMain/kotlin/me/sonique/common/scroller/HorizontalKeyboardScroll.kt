package me.sonique.common.scroller

import me.sonique.common.scroller.HorizontalScroll


/**
 * HorizontalAutoScroll
 *
 * @param speed : at what speed the object travels (in DP / second)
 * @param direction : which direction the object travel to: left or right
 *
 */
class HorizontalKeyboardScroll(
    speed: Int = 10,
    direction: Direction = Direction.LEFT,
) : HorizontalScroll(speed, direction) {


    private var distance: Int = 0

    fun distanceToMove(distance: Int = speed) {
        this.distance = distance
    }

    override fun postMoveDistance() {
        this.distance = 0
    }

    /**
     * implements moveDistance and based the distance
     * travelled with the keyboard
     * @return Int (distance in dp)
     */
    override fun moveDistance(): Int {
        return -distance
    }
}