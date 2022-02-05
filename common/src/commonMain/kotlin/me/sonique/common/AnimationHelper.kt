package me.sonique.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.sonique.common.core.CGDKObject
import me.sonique.common.core.Vector2
import kotlin.math.ceil
import kotlinx.datetime.Clock

class AnimationHelper {
    companion object {
        const val SPEED_SLOWER = 160
        const val SPEED_SLOW = 320
        const val SPEED_NORMAL = 640
        const val SPEED_FAST = 1280
        const val SPEED_FASTER = 1920

        private val mainScope = CoroutineScope(Dispatchers.Main)
        private const val FRAME_LENGTH = 16L // 60Hz
        suspend fun jump(objectList: List<CGDKObject>, distance: Int, speed: Int) {

            val duration = distance.toDouble() / speed.toDouble() * 1000.0
            val stepsCount = ceil(duration / FRAME_LENGTH).toInt() / 2
            val stepSize = distance / stepsCount

            objectList.forEach { gameObject ->
                mainScope.launch {
                    for (i in 0..stepsCount) {
                        gameObject.mutablePosition.value = Vector2(
                            gameObject.mutablePosition.value.x,
                            gameObject.mutablePosition.value.y - stepSize
                        )
                        delay(FRAME_LENGTH)
                    }
                    for (i in 0..stepsCount) {
                        gameObject.mutablePosition.value = Vector2(
                            gameObject.mutablePosition.value.x,
                            gameObject.mutablePosition.value.y + stepSize
                        )
                        delay(FRAME_LENGTH)
                    }
                }
            }
        }

        suspend fun jump2(objectList: List<CGDKObject>, distance: Int, speed: Int) {

            val durationJump = distance.toDouble() / speed.toDouble() * 1000.0 * 0.25
            val durationFly = distance.toDouble() / speed.toDouble() * 1000.0 * 0.75            

            val stepsCount = ceil(durationJump / FRAME_LENGTH).toInt() 
            val stepSize = distance / stepsCount
           
            objectList.forEach { gameObject ->
                for (i in 0..stepsCount) {
                    gameObject.mutablePosition.value = Vector2(
                        gameObject.mutablePosition.value.x,
                        gameObject.mutablePosition.value.y - stepSize
                    )
                    delay(FRAME_LENGTH)
                }
                delay(durationFly.toLong())
                for (i in 0..stepsCount) {
                    gameObject.mutablePosition.value = Vector2(
                        gameObject.mutablePosition.value.x,
                        gameObject.mutablePosition.value.y + stepSize
                    )
                    delay(FRAME_LENGTH)
                }
            }
        }
    }
}