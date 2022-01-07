package me.sonique.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import higher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.sonique.common.collision.CollisionHelper
import me.sonique.common.collision.OnCollision
import me.sonique.common.controller.DirectionGameController
import me.sonique.common.controller.IDirectionGameController
import me.sonique.common.controller.ProvideDirectionalController
import me.sonique.common.controller.event.IOnKeyUp
import me.sonique.common.core.CGDKGame
import me.sonique.common.core.CGDKObject
import me.sonique.common.core.ImageCGDKObject
import me.sonique.common.score.ScoreManager
import org.openrndr.math.Vector2
import rightShift
import toVector2
import kotlin.random.Random


val GAME_SIZE = IntSize(480, 320)
val WINDOW_SIZE = IntSize(GAME_SIZE.width, GAME_SIZE.height + 48)



class QuestionMarkBox() : ImageCGDKObject(
    imageFileName = "box.jpg",
    size = Vector2(25.dp.value.toDouble(), 25.dp.value.toDouble())
), OnCollision {
    private val mainScope = CoroutineScope(Dispatchers.Main)
    override fun onCollision(object2: CGDKObject) {
        if(object2 is Mario) {
            mainScope.launch {
                ScoreManager.ScoreManager.score.emit(1)
            }
        }
    }
}

class Mario: ImageCGDKObject(
    imageFileName = "mario.jpg",
    size = Vector2(50.dp.value.toDouble(), 30.dp.value.toDouble())
), IOnKeyUp {
    override fun onKeyUp() {
        AnimationHelper.jump(listOf(this), 45, AnimationHelper.SPEED_NORMAL)
    }
}

class Cloud: ImageCGDKObject(
    imageFileName = "cloud.jpg",
    size = Vector2(100.dp.value.toDouble(), 50.dp.value.toDouble())
)

class Grass: ImageCGDKObject(
    imageFileName = "grass.jpg",
    size = Vector2(100.dp.value.toDouble(), 25.dp.value.toDouble())
)

class Wall:  ImageCGDKObject(
    imageFileName = "wall.jpg",
    size = Vector2(25.dp.value.toDouble(), 25.dp.value.toDouble())
)

class MyCGDKGame : CGDKGame(), ProvideDirectionalController {

    val placementHelper = Placement(GAME_SIZE.toVector2())


    val horizontalCharacterScroll = HorizontalKeyboardScroll(speed = 7)
    val verticalCharacterScroll = VerticalKeyboardScroll(speed = 7)

    val F_Scroll = 60
    val horizontalFrontScroll = HorizontalAutoScroll(speed = F_Scroll)
    val horizontalMediumScroll = HorizontalAutoScroll(speed = 2)
    val horizontalBackScroll = HorizontalAutoScroll(speed = 1)

    val score = mutableStateOf(0)

    init {

        val directionGameController = DirectionGameController(
            leftCallback = {
                this.horizontalCharacterScroll.direction = HorizontalScroll.Direction.LEFT
                this.horizontalCharacterScroll.distanceToMove()
            }, rightCallback = {
                this.horizontalCharacterScroll.direction = HorizontalScroll.Direction.RIGHT
                this.horizontalCharacterScroll.distanceToMove()
            }, upCallback = {
                this.verticalCharacterScroll.direction = VerticalScroll.Direction.UP
                this.verticalCharacterScroll.distanceToMove()
            }, downCallback = {
                this.verticalCharacterScroll.direction = VerticalScroll.Direction.DOWN
                this.verticalCharacterScroll.distanceToMove()
            })

        this.gameControllers.add(directionGameController)

        val floor = ImageCGDKObject(
            imageFileName = "floor.jpg",
            isTexture = true,
            size = Vector2(6000.dp.value.toDouble(), 25.dp.value.toDouble())
        )
        floor.mutablePosition.value = placementHelper.toBottomLeft(floor)
        horizontalFrontScroll.addObject(floor)
        this.addGObject(floor)

        for(i in 0 .. 15) {
            val cloud3 = Cloud()
            cloud3.mutablePosition.value = placementHelper.toBottomCenter(cloud3)
                .rightShift( Random.nextInt(i * 300, (i * 300)+200))
                .higher( Random.nextInt(100, 250))
            horizontalBackScroll.addObject(cloud3)
            this.addGObject(cloud3)
        }

        for(i in 0 .. 15) {
            val wallCount = Random.nextInt(1,3)
            val initialPosition = Random.nextInt(30+(i * 300), (i * 300)+200).toDouble()
            for(j in 0 .. wallCount) {
                val wall = Wall()
                wall.mutablePosition.value = Vector2(initialPosition + j * 25.0, 200.dp.value.toDouble())
                horizontalFrontScroll.addObject(wall)
                this.addGObject(wall)
            }
        }

        for(i in 0 .. 15) {
            val questionMarkBox = QuestionMarkBox()
            val centerRight = Vector2(Random.nextInt(i * 200, i * 300 + 250).toDouble(), 200.dp.value.toDouble())
            questionMarkBox.mutablePosition.value = centerRight
            horizontalFrontScroll.addObject(questionMarkBox)
            this.addGObject(questionMarkBox)
        }

        for(i in 0 .. 15) {
            val grass = Grass()
            grass.mutablePosition.value = placementHelper.toBottomCenter(grass)
                .rightShift(Random.nextInt(30+(i * 300), (i * 300)+200))
            grass.mutablePosition.value = placementHelper.putObjectAOnTopB(grass, floor)
            horizontalMediumScroll.addObject(grass)
            this.addGObject(grass)
        }

        val mario = Mario()
        mario.mutablePosition.value = placementHelper.toBottomCenter(mario)
        mario.mutablePosition.value = placementHelper.putObjectAOnTopB(mario, floor)
        horizontalCharacterScroll.addObject(mario)
        verticalCharacterScroll.addObject(mario)
        this.addGObject(mario)


    }

    fun update() {
        horizontalCharacterScroll.update()
        verticalCharacterScroll.update()
        horizontalFrontScroll.update()
        horizontalMediumScroll.update()
        horizontalBackScroll.update()
        CollisionHelper.detectCollisions(this.gameObjects)
    }

    fun score(): MutableState<Int> {
        return ScoreManager.ScoreManager.mutableScore
    }

    override fun getDirectionalController(): IDirectionGameController {
        return this.gameControllers.first { it is IDirectionGameController } as IDirectionGameController
    }
}