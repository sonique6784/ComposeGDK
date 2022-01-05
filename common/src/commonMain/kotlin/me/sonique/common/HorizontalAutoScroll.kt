package me.sonique.common

import java.time.Instant
import kotlin.math.absoluteValue


/**
 * HorizontalAutoScroll
 *
 * @param initialPositionX : where the object should be placed
 * @param speed : at what speed the object travels (in DP / second)
 * @param direction : which direction the object travel to: left or right
 */
class HorizontalAutoScroll(
    speed: Int = 10,
    direction: Direction = Direction.LEFT,
) : HorizontalScroll(speed, direction) {

    private var previousTime: Long = Instant.now().toEpochMilli()

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
        val now = Instant.now().toEpochMilli()
        val timeDiff = previousTime - now

        // Move by Speed DP / Second
        val moveSize = (timeDiff.toDouble() / 1000.0 * speed.toDouble()).run {
            // Apply travel direction
            when (direction) {
                Direction.LEFT -> this * -1
                else -> this
            }
        }

        // we want to move at least 1DP
        // TODO: we could go down the a pixel, by checking the current DP and
        // TODO: replacing the "1" by 1 / DP, that would result in smoother animations
        if (moveSize.absoluteValue > 1) {
            previousTime = now
        }

        return moveSize.toInt()
    }
}