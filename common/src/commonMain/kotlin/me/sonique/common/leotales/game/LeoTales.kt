package me.sonique.common.leotales.game

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.IntSize
import me.sonique.common.core.CGDKGame
import me.sonique.common.Placement
import androidx.compose.ui.unit.dp
import toVector2
import rightShift

// 480 width, 320 height 
val WINDOW_SIZE = IntSize(480, 320)
// the Game Size is window size minus the Status bar
val GAME_SIZE = IntSize(WINDOW_SIZE.width, WINDOW_SIZE.height - 64)

class LeoTales : CGDKGame() {

    val gameTitle = mutableStateOf("Leo Tales")
    val level = mutableStateOf(1)
    val energy = mutableStateOf(1)

    val leo = Leo()
    val food = Food()
    val obstacle = Obstacle()


    val placementHelper = Placement(GAME_SIZE.toVector2())

    init {
        leo.mutablePosition.value = placementHelper.toBottomCenter(leo)
        this.addGObject(leo)

        food.mutablePosition.value = placementHelper.toBottomCenter(food).rightShift(170)
        this.addGObject(food)

        obstacle.mutablePosition.value = placementHelper.toBottomCenter(obstacle).rightShift(100)
        this.addGObject(obstacle)
    }
}