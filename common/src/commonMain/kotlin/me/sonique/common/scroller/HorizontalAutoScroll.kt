package me.sonique.common.scroller

import kotlinx.datetime.Clock
import kotlin.math.absoluteValue


/**
 * HorizontalAutoScroll
 * Help to scroll multiple objects at the same speed
 * Ideal to move objects, maps, decors and background Horizontally
 * This Auto version scrolls automatically depending on the speed and direction
 *
 * @param speed : at what speed the object travels (in DP / second)
 * @param direction : which direction the object travel to: left or right
 */
class HorizontalAutoScroll(
    speed: Int = 10,
    direction: Direction = Direction.LEFT,
) : HorizontalScroll(speed, direction) {

    private var previousTime: Long = Clock.System.now().toEpochMilliseconds()

    override fun postMoveDistance() {
        // Nothing here
    }

    /**
     * moveDistance
     * implements moveDistance and based the distance
     * on the time elapsed
     * @return Int (distance in dp)
     */
    override fun moveDistance(): Int {
        val now = Clock.System.now().toEpochMilliseconds()
        val timeDiff = previousTime - now

        // Move by Speed DP / Second
        val moveSize = (timeDiff.toDouble() / 1000.0 * speed.toDouble())
        
        // we want to move at least 1DP
        // TODO: we could go down the a pixel, by checking the current DP and
        // TODO: replacing the "1" by 1 / DP, that would result in smoother animations
        if (moveSize.absoluteValue > 1) {
            previousTime = now
        }

        return moveSize.toInt()
    }
}