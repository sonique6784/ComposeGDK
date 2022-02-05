package me.sonique.common.leotales.game

import me.sonique.common.core.ImageCGDKObject
import me.sonique.common.core.Vector2
import me.sonique.common.AnimationHelper
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Leo: ImageCGDKObject("leo.png", size=Vector2(25.0, 17.0)) {
    private var isJumping = false
    private var jumping = Semaphore(permits = 1)
    private val mainScope = CoroutineScope(Dispatchers.Main)
    fun jump() {
        mainScope.launch {
            if(jumping.tryAcquire()) {
                AnimationHelper.jump2(listOf(this@Leo), distance = 80, speed = 120)
                jumping.release()
            }// else skip jump
        }
    }
}