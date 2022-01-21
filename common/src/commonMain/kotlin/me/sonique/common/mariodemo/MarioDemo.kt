package me.sonique.common.mariodemo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import higher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.sonique.common.AnimationHelper
import me.sonique.common.Placement
import me.sonique.common.collision.CollisionHelper
import me.sonique.common.collision.OnCollision
import me.sonique.common.controller.DirectionGameController
import me.sonique.common.controller.IDirectionGameController
import me.sonique.common.controller.ProvideDirectionalController
import me.sonique.common.controller.action.LeftActionListener
import me.sonique.common.controller.action.RightActionListener
import me.sonique.common.controller.action.UpActionListener
import me.sonique.common.core.CGDKGame
import me.sonique.common.core.CGDKObject
import me.sonique.common.core.ImageCGDKObject
import me.sonique.common.score.ScoreManager
import me.sonique.common.scroller.HorizontalAutoScroll
import me.sonique.common.scroller.VerticalAutoScroll
import org.openrndr.math.Vector2
import rightShift
import toVector2
import kotlin.random.Random


val GAME_SIZE = IntSize(480, 320)
val WINDOW_SIZE = IntSize(GAME_SIZE.width, GAME_SIZE.height + 64)



class QuestionMarkBox() : ImageCGDKObject(
    imageFileName = "box.jpg",
    size = Vector2(25.dp.value.toDouble(), 25.dp.value.toDouble())
)
// ), OnCollision {
//     private val mainScope = CoroutineScope(Dispatchers.Main)
//     override fun onCollision(object2: CGDKObject) {
//         if(object2 is Mario) {
//             mainScope.launch {
//                 ScoreManager.ScoreManager.score.emit(1)
//             }
//         }
//     }
// }

class Mario: ImageCGDKObject(
    imageFileName = "mario.jpg",
    size = Vector2(50.dp.value.toDouble(), 30.dp.value.toDouble())
), UpActionListener.IOnKeyUp, LeftActionListener.IOnKeyLeft, RightActionListener.IOnKeyRight {
    override fun onKeyUp() {
        AnimationHelper.jump(listOf(this), 45, AnimationHelper.SPEED_NORMAL)
    }

    override fun onKeyLeft() {
        print("left Key Mario \n")
        this.mutablePosition.value = Vector2(
            this.mutablePosition.value.x - 7,
            this.mutablePosition.value.y
        )
    }

    override fun onKeyRight() {
        print("right Key Mario \n")
        if(!isHalfOrOverScreen()) {
            this.mutablePosition.value = Vector2(
                this.mutablePosition.value.x + 7,
                this.mutablePosition.value.y
            )
        }
    }

    fun isHalfOrOverScreen(): Boolean {
        // Mario is Half screen if his midle pixels (this.mutableSize.value.x/2)
        // are other half screens (WINDOW_SIZE.width/2)
        return this.mutablePosition.value.x - (this.mutableSize.value.x/2) > GAME_SIZE.width/2
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

class MarioDemo : CGDKGame(), ProvideDirectionalController {

    private val placementHelper = Placement(GAME_SIZE.toVector2())
    private val upActionListener = UpActionListener()
    private val rightActionListener = RightActionListener()
    private val leftActionListener = LeftActionListener()

    private val mario = Mario()

    private val F_Scroll = 60
    private val horizontalFrontScroll = MarioHorizontalKeyboardScroll(speed = F_Scroll, mario = mario)
    private val horizontalMediumScroll = HorizontalAutoScroll(speed = 2)

    private val verticalCloudMediumScroll = VerticalAutoScroll(speed = 2)
    private val horizontalBackScroll = HorizontalAutoScroll(speed = 5)

    private val questionMarkBoxes = mutableListOf<QuestionMarkBox>()

    val score = mutableStateOf(0)

    private val mainScope = CoroutineScope(Dispatchers.Main)

    init {

        val directionGameController = DirectionGameController(
            leftCallback = {
                leftActionListener.onAction()
                horizontalFrontScroll.onKeyLeft()
            }, rightCallback = {
                rightActionListener.onAction()
                horizontalFrontScroll.onKeyRight()
            }, upCallback = {
                upActionListener.onAction()

            }, downCallback = {
                
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

        for(i in 0 .. 20) {
            val cloud3 = Cloud()
            cloud3.mutablePosition.value = placementHelper.toBottomCenter(cloud3)
                .rightShift( Random.nextInt(i * 150, (i * 150)+200))
                .higher( Random.nextInt(100, 250))
            if(i % 4 == 0) {
                verticalCloudMediumScroll.addObject(cloud3)
            } else {
                verticalCloudMediumScroll.addObject(cloud3)
                horizontalBackScroll.addObject(cloud3)
            }
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
            this.questionMarkBoxes.add(questionMarkBox)
        }

        for(i in 0 .. 15) {
            val grass = Grass()
            grass.mutablePosition.value = placementHelper.toBottomCenter(grass)
                .rightShift(Random.nextInt(30+(i * 300), (i * 300)+200))
            grass.mutablePosition.value = placementHelper.putObjectAOnTopB(grass, floor)
            horizontalMediumScroll.addObject(grass)
            this.addGObject(grass)
        }


        mario.mutablePosition.value = placementHelper.toBottomCenter(mario)
        mario.mutablePosition.value = placementHelper.putObjectAOnTopB(mario, floor)
        upActionListener.addObject(mario)
        rightActionListener.addObject(mario)
        leftActionListener.addObject(mario)
        this.addGObject(mario)


    }

    private val recentCollisions = mutableListOf<CGDKObject>()

    fun update() {
        //horizontalFrontScroll.update()
        horizontalMediumScroll.update()
        horizontalBackScroll.update()
        verticalCloudMediumScroll.update()
        val collisions = CollisionHelper.detectCollision(mario, this.questionMarkBoxes)
        if(collisions.isNotEmpty()) {
            // we assume 1 collision at the time for now
            if(!recentCollisions.contains(collisions.first())) {
                mainScope.launch {
                    ScoreManager.ScoreManager.score.emit(1)
                }
                recentCollisions.add(collisions.first())
            }
        } else {
            recentCollisions.clear()
        }
    }

    fun score(): MutableState<Int> {
        return ScoreManager.ScoreManager.mutableScore
    }

    override fun getDirectionalController(): IDirectionGameController {
        return this.gameControllers.first { it is IDirectionGameController } as IDirectionGameController
    }
}