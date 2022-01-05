package me.sonique.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import higher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lower
import me.sonique.common.collision.CollisionHelper
import me.sonique.common.collision.OnCollision
import me.sonique.common.controller.event.IOnKeyUp
import me.sonique.common.core.CGDKGame
import me.sonique.common.core.CGDKObject
import me.sonique.common.core.ImageCGDKObject
import me.sonique.common.score.ScoreManager
import org.openrndr.math.Vector2
import rightShift
import toVector2



val GAME_SIZE = IntSize(480, 320)
val WINDOW_SIZE = IntSize(GAME_SIZE.width, GAME_SIZE.height + 48)



class QuestionMarkBox() : ImageCGDKObject(
    imageFileName = "box.jpg",
    size = Vector2(25.dp.value.toDouble(), 25.dp.value.toDouble())
), OnCollision {
    private val mainScope = CoroutineScope(Dispatchers.Main)
    override fun onCollision(object2: CGDKObject) {
        print("${object2} in Collision with Wall\n")
        mainScope.launch {
            ScoreManager.ScoreManager.score.emit(1)
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

class MyCGDKGame : CGDKGame() {

    val placementHelper = Placement(GAME_SIZE.toVector2())

    val horizontalCharacterScroll = HorizontalKeyboardScroll(speed = 7)
    val verticalCharacterScroll = VerticalKeyboardScroll(speed = 7)
    val horizontalFrontScroll = HorizontalAutoScroll(speed = 7)
    val horizontalMediumScroll = HorizontalAutoScroll(speed = 5)
    val horizontalBackScroll = HorizontalAutoScroll(speed = 2)

    val score = mutableStateOf(0)

    init {

        val floor = ImageCGDKObject(
            imageFileName = "floor.jpg",
            isTexture = true,
            size = Vector2(6000.dp.value.toDouble(), 25.dp.value.toDouble())
        )
        floor.mutablePosition.value = placementHelper.toBottomLeft(floor)
        horizontalFrontScroll.addObject(floor)
        this.addGObject(floor)

        val cloud = ImageCGDKObject(
            imageFileName = "cloud.jpg",
            size = Vector2(100.dp.value.toDouble(), 100.dp.value.toDouble())
        )
        cloud.mutablePosition.value = placementHelper.toCenterLeft(cloud)
        horizontalMediumScroll.addObject(cloud)
        this.addGObject(cloud)

        val cloud2 = ImageCGDKObject(
            imageFileName = "cloud.jpg",
            size = Vector2(50.dp.value.toDouble(), 100.dp.value.toDouble())
        )
        cloud2.mutablePosition.value = placementHelper.toTopCenter(cloud2).lower(20)
        horizontalMediumScroll.addObject(cloud2)
        this.addGObject(cloud2)

        val cloud3 = ImageCGDKObject(
            imageFileName = "cloud.jpg",
            size = Vector2(100.dp.value.toDouble(), 50.dp.value.toDouble())
        )

        val position0 = placementHelper.toBottomCenter(cloud3)
        val position1 = placementHelper.toBottomCenter(cloud3).rightShift(300)
        val position2 = placementHelper.toBottomCenter(cloud3).rightShift(300).higher(200)

        print(position0)
        print(position1)
        print(position2)

        cloud3.mutablePosition.value = position2 //placementHelper.toBottomCenter(cloud3).rightShift(300).higher(200)
//        cloud3.mutablePosition.value = placementHelper.putObjectAOnTopB(cloud3, floor)
        horizontalBackScroll.addObject(cloud3)
        this.addGObject(cloud3)


        val cloud4 = ImageCGDKObject(
            imageFileName = "cloud.jpg",
            size = Vector2(100.dp.value.toDouble(), 50.dp.value.toDouble())
        )
        cloud4.mutablePosition.value = placementHelper.toCenterRight(cloud4)//.rightShift(400)//.higher(500)
        cloud4.mutablePosition.value = placementHelper.putObjectAOnTopB(cloud4, floor)
        horizontalBackScroll.addObject(cloud4)
        this.addGObject(cloud4)

        val wall = ImageCGDKObject(
            imageFileName = "wall.jpg",
            size = Vector2(25.dp.value.toDouble(), 25.dp.value.toDouble())
        )
        wall.mutablePosition.value = Vector2(100.dp.value.toDouble(), 100.dp.value.toDouble())
        horizontalFrontScroll.addObject(wall)
        this.addGObject(wall)

        val wall1 = ImageCGDKObject(
            imageFileName = "wall.jpg",
            size = Vector2(25.dp.value.toDouble(), 25.dp.value.toDouble())
        )
        wall1.mutablePosition.value = Vector2(125.dp.value.toDouble(), 100.dp.value.toDouble())
        horizontalFrontScroll.addObject(wall1)
        this.addGObject(wall1)

        val questionMarkBox = QuestionMarkBox()
        val centerRight = placementHelper.toCenterRight(questionMarkBox).lower(40)
        questionMarkBox.mutablePosition.value = centerRight
        horizontalFrontScroll.addObject(questionMarkBox)
        this.addGObject(questionMarkBox)

        val grass =  ImageCGDKObject(
            imageFileName = "grass.jpg",
            size = Vector2(100.dp.value.toDouble(), 25.dp.value.toDouble())
        )
        grass.mutablePosition.value = placementHelper.toBottomCenter(grass)
        grass.mutablePosition.value = placementHelper.putObjectAOnTopB(grass, floor)
        horizontalMediumScroll.addObject(grass)
        this.addGObject(grass)

        val mario = Mario()
        mario.mutablePosition.value = placementHelper.toBottomCenter(mario)
        mario.mutablePosition.value = placementHelper.putObjectAOnTopB(mario, floor)
        horizontalCharacterScroll.addObject(mario)
        verticalCharacterScroll.addObject(mario)
        this.addGObject(mario)

    }

    fun update() {
        CollisionHelper.detectCollisions(this.gameObjects)
    }

    fun score(): MutableState<Int> {
        return ScoreManager.ScoreManager.mutableScore
    }
}