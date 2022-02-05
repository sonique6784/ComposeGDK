package me.sonique.common.leotales.game

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.IntSize
import me.sonique.common.core.CGDKGame
import me.sonique.common.Placement
import me.sonique.common.scroller.HorizontalAutoScroll
import me.sonique.common.collision.CollisionHelper
import me.sonique.common.core.CGDKObject
import androidx.compose.ui.unit.dp
import toVector2
import me.sonique.common.controller.DirectionGameController
import me.sonique.common.controller.IDirectionGameController
import me.sonique.common.controller.ProvideDirectionalController

// 480 width, 320 height 
val WINDOW_SIZE = IntSize(480, 320)
// the Game Size is window size minus the Status bar
val GAME_SIZE = IntSize(WINDOW_SIZE.width, WINDOW_SIZE.height - 64)

class LeoTales : CGDKGame(), ProvideDirectionalController {

    val gameTitle = mutableStateOf("Leo Tales")
    val level = mutableStateOf(1)
    val energy = mutableStateOf(1)

    val leo = Leo()
    val food = Food()
    val obstacle = Obstacle()
    val decor = Decor()

    private val obstacles = mutableListOf(obstacle)
    private val foods = mutableListOf(food)

    private val horizontalAutoScroll = HorizontalAutoScroll(speed = 130)


    val placementHelper = Placement(GAME_SIZE.toVector2())

    init {

        val directionGameController = DirectionGameController(
            upCallback = {
                leo.jump()
            })

        this.gameControllers.add(directionGameController)


        decor.mutablePosition.value = placementHelper.toTopLeft()
        this.addGObject(decor)

        food.mutablePosition.value = placementHelper.toBottomCenter(food).rightShift(170).higher(10)
        this.addGObject(food)
        
        obstacle.mutablePosition.value = placementHelper.toBottomCenter(obstacle).rightShift(100).higher(10)
        this.addGObject(obstacle)

        leo.mutablePosition.value = placementHelper.toBottomCenter(leo).higher(10)
        this.addGObject(leo)

        horizontalAutoScroll.addObject(decor)
        horizontalAutoScroll.addObject(food)
        horizontalAutoScroll.addObject(obstacle)
    }

    private val lastCollisions = mutableListOf<CGDKObject>()

    override fun update() {
        horizontalAutoScroll.update()

        // Check if Leo enter in collision with an Obstacle
        val obstaclesCollisions = CollisionHelper.detectCollision(leo, this.obstacles)
        if(obstaclesCollisions.size > 0) {
            println("collision: ${obstaclesCollisions.size}")
            println("Obs: ${obstaclesCollisions.first().toString()}")
            println("Leo: ${leo.toString()}")
        }
        // Check if Leo enter in collision with some Food
        val foodCollisions = CollisionHelper.detectCollision(leo, this.foods)

        if(obstaclesCollisions.isEmpty() && foodCollisions.isEmpty()) {
            lastCollisions.clear()
        } else {
            if (obstaclesCollisions.isNotEmpty()) {
                if(!lastCollisions.contains(obstaclesCollisions.first())) {
                    // Action
                    this.energy.value = this.energy.value - 1

                    lastCollisions.add(obstaclesCollisions.first())
                }
            } 
            if (foodCollisions.isNotEmpty()) {
                if(!lastCollisions.contains(foodCollisions.first())) {
                    // Action
                    this.energy.value = this.energy.value + 1

                    // remove from the scene
                    this.gameObjects.remove(foodCollisions.first())
                    
                    lastCollisions.add(foodCollisions.first())
                }
            }
        }        
    }

    override fun getDirectionalController(): IDirectionGameController {
        return this.gameControllers.first { it is IDirectionGameController } as IDirectionGameController
    }
}