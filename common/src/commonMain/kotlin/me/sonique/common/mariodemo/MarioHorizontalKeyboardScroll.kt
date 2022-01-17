package me.sonique.common.mariodemo

import me.sonique.common.controller.action.LeftActionListener
import me.sonique.common.controller.action.RightActionListener
import me.sonique.common.scroller.HorizontalScroll


/**
 * MarioHorizontalKeyboardScroll
 * Example of how we can implement horizontal scroller to move decors in Mario demo
 *
 * @param distance
 * @param speed : at what speed the object travels (in DP / second)
 * @param direction : which direction the object travel to: left or right
 *
 */
class MarioHorizontalKeyboardScroll(
    private var distance: Int = 7,
    private val mario: Mario,
    speed: Int = 10,
    direction: Direction = Direction.LEFT,
) : HorizontalScroll(speed, direction), LeftActionListener.IOnKeyLeft, RightActionListener.IOnKeyRight {


    fun setDistance(distance: Int) {
        this.distance = distance
    }

    override fun postMoveDistance() = Unit

    override fun onKeyLeft() {
        // Decor don't scroll back left in Mario, Mario can only go forward
    }

    override fun onKeyRight() {
        if(mario.isHalfOrOverScreen()) {
            // Decors scroll in the opposite direction of the character
            direction = Direction.LEFT
            update()

            // TODO: Decor shouldn't scroll untill Mario reach half of the screen width
        }
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