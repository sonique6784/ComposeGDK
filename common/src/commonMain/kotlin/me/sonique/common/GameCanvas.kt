package me.sonique.common

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import org.openrndr.math.Vector2
import java.time.Instant
import kotlin.math.absoluteValue
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.*
import kotlin.coroutines.CoroutineContext


val GAME_SIZE = IntSize(480.dp.value.toInt(), 320.dp.value.toInt())
val WINDOW_SIZE = IntSize(GAME_SIZE.width, GAME_SIZE.height + 64)

fun IntSize.toVector2(): Vector2 {
    return Vector2(this.width.toDouble(), this.height.toDouble())
}

class ScoreManager() {
    val score = MutableSharedFlow<Int>()
    val mutableScore = mutableStateOf(0)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    init {
        //TODO: find a flow or channel the receive same value (like when we send 1 two times in the row)
        mainScope.launch {
            score.collect {
                mutableScore.value += it
                print("score: ${mutableScore.value} \n")
            }
        }

    }

    companion object {
        val ScoreManager = ScoreManager()
    }
}


class Wall() : ImageGameObject(
    imageFileName = "wall.jpg",
    size = Vector2(50.dp.value.toDouble(), 50.dp.value.toDouble())
), OnCollision {
    private val mainScope = CoroutineScope(Dispatchers.Main)
    override fun onCollision(object2: GameObject2) {
        print("${object2} in Collision with Wall\n")
        mainScope.launch {
            ScoreManager.ScoreManager.score.emit(1)
        }
    }
}

class MyGame : Game2() {

    val placementHelper = Placement(GAME_SIZE.toVector2())

    val horizontalCharacterScroll = HorizontalKeyboardScroll(speed = 7)
    val verticalCharacterScroll = VerticalKeyboardScroll(speed = 7)
    val horizontalFrontScroll = HorizontalAutoScroll(speed = 7)
    val horizontalMediumScroll = HorizontalKeyboardScroll(speed = 5)
    val horizontalBackScroll = HorizontalKeyboardScroll(speed = 2)

    val score = mutableStateOf(0)

    init {

        val floor = ImageGameObject(
            imageFileName = "floor.jpg",
            isTexture = true,
            size = Vector2(6000.dp.value.toDouble(), 50.dp.value.toDouble())
        )
        floor.mutablePosition.value = placementHelper.toBottomLeft(floor)
        horizontalFrontScroll.addObject(floor)
        this.addGObject(floor)

        val cloud = ImageGameObject(
            imageFileName = "cloud.jpg",
            size = Vector2(100.dp.value.toDouble(), 100.dp.value.toDouble())
        )
        cloud.mutablePosition.value = placementHelper.toCenterLeft(cloud)
        horizontalMediumScroll.addObject(cloud)
        this.addGObject(cloud)

        val cloud2 = ImageGameObject(
            imageFileName = "cloud.jpg",
            size = Vector2(50.dp.value.toDouble(), 100.dp.value.toDouble())
        )
        cloud2.mutablePosition.value = placementHelper.toTopCenter(cloud2)
        horizontalMediumScroll.addObject(cloud2)
        this.addGObject(cloud2)

        val cloud3 = ImageGameObject(
            imageFileName = "cloud.jpg",
            size = Vector2(100.dp.value.toDouble(), 50.dp.value.toDouble())
        )
        cloud3.mutablePosition.value = placementHelper.toBottomCenter(cloud3)
        cloud3.mutablePosition.value = placementHelper.putObjectAOnTopB(cloud3, floor)
        horizontalMediumScroll.addObject(cloud3)
        this.addGObject(cloud3)

        val box = ImageGameObject(
            imageFileName = "box.jpg",
            size = Vector2(1000.dp.value.toDouble(), 25.dp.value.toDouble())
        )
        box.mutablePosition.value = Vector2(100.dp.value.toDouble(), 100.dp.value.toDouble())
        horizontalFrontScroll.addObject(box)
        this.addGObject(box)

        val wall = Wall()
        wall.mutablePosition.value = placementHelper.toCenterRight(wall)
        horizontalFrontScroll.addObject(wall)
        this.addGObject(wall)

        val grass = GameObject2(size = Vector2(100.dp.value.toDouble(), 50.dp.value.toDouble()))
        grass.mutablePosition.value = placementHelper.toBottomRight(grass)
        grass.mutablePosition.value = placementHelper.putObjectAOnTopB(grass, floor)
        horizontalMediumScroll.addObject(grass)
        this.addGObject(grass)

        val mario = ImageGameObject(
            imageFileName = "mario.jpg",
            size = Vector2(50.dp.value.toDouble(), 30.dp.value.toDouble())
        )
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


class CollisionHelper {

    companion object {

        const val LIMIT = 100

        val collisions = mutableListOf<Triple<GameObject2, GameObject2, Long>>()
        val collisionsHash = hashMapOf<Pair<GameObject2, GameObject2>, Long>()

        fun detectCollisions(objectList: List<GameObject2>): List<Pair<GameObject2, GameObject2>> {

            val time = Instant.now().toEpochMilli()

            objectList.forEach { goA ->
                objectList.forEach { goB ->
                    if (goA != goB) {
                        // Overlap means the the center of the game objects are closer together than the sum of their radiuses
                        if (goA.mutablePosition.value.distanceTo(goB.mutablePosition.value) < (goA.mutableSize.value.y / 2 + goB.mutableSize.value.y / 2)) {
                            val collisionPair = Pair(goA, goB)
                            //val collisionPair2 = Pair(goB, goA)
                            if (!collisionsHash.containsKey(collisionPair)) {
                                //if(!collisions.contains(collisionPair) && !collisions.contains(collisionPair2)) {
                                collisionsHash.put(Pair(goA, goB), time)
                                if (goA is OnCollision) {
                                    goA.onCollision(goB)
                                    //goA.onCollision(goB, goA)
                                }
                            }
//                            if(goB is OnCollision) {
//                                goB.onCollision(goA)
//                                //goB.onCollision(goA, goB)
//                            }
                        }
                    }
                }
            }

            // purge old collision
            val collisionIterator = collisionsHash.iterator()
            while (collisionIterator.hasNext()) {
                val collision = collisionIterator.next()
                // we keep collision only for LIMIT, this is to prevent double count of the same collision.
                if (time - collision.value > LIMIT) {
                    collisionIterator.remove()
                }
            }

            return collisionsHash.keys.toMutableList()
        }
    }

}

/**
 * interface to implement to know when an object is in collision
 */
interface OnCollision {
    fun onCollision(object2: GameObject2)
    //fun onCollision(gameObjectA: GameObject2, gameObjectB: GameObject2)
}

open class Game2 {
    val gameObjects = mutableStateListOf<GameObject2>()

    fun addGObject(gameObject2: GameObject2) {
        gameObjects.add(gameObject2)
    }

    fun removeGObject(gameObject2: GameObject2) {
        gameObjects.remove(gameObject2)
    }

    fun render() {

    }
}

interface IKeyboardHandler {
    fun handleKey(event: KeyEvent): Boolean
}

class KeyboardMovementHelper(
    private val leftCallback: () -> Unit,
    private val rightCallback: () -> Unit,
    private val upCallback: () -> Unit,
    private val downCallback: () -> Unit,
) : IKeyboardHandler {

    @ExperimentalComposeUiApi
    override fun handleKey(event: KeyEvent): Boolean {
        print("Key press: ${event.type}  ${event.key}\n")
        return if (event.type == KeyEventType.KeyDown) {
            when (event.key) {
                Key.DirectionLeft -> isLeft()
                Key.DirectionUp -> isUp()
                Key.DirectionRight -> isRight()
                Key.DirectionDown -> isDown()
            }
            false
        } else {
            true
        }
    }

    private fun isLeft() {
        print("Left\n")
        leftCallback.invoke()
    }

    private fun isRight() {
        print("Right\n")
        rightCallback.invoke()
    }

    private fun isUp() {
        print("Up\n")
        upCallback.invoke()
    }

    private fun isDown() {
        print("Down\n")
        downCallback.invoke()
    }
}

@Composable
inline fun GameCanvas(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(255, 255, 255),
    keyboardHandler: IKeyboardHandler? = null, // Should we let the user add the handler themselve to the modifier?

    // Content must be last parameter
    content: @Composable BoxScope.() -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = modifier.onKeyEvent { event ->
            return@onKeyEvent keyboardHandler?.handleKey(event) ?: false
        }.clickable() {
            print("request focus\n")
        }
            .focusRequester(focusRequester)
            .background(backgroundColor)
            .fillMaxWidth()
            .fillMaxHeight()
            .focusable()
    ) {
        // Insert the content provided as last paramter
        content()

        // Allow us to request focus after composition
        SideEffect {
            focusRequester.requestFocus()
        }
    }
}


@Composable
fun GameImage(
    gameObject2: ImageGameObject,
    modifier: Modifier = Modifier
) {
    val mod = if (gameObject2 != null) {
        modifier
            .height(gameObject2.mutableSize.value.y.dp)
            .width(gameObject2.mutableSize.value.x.dp)
            .offset(
                gameObject2.mutablePosition.value.x.dp,
                gameObject2.mutablePosition.value.y.dp
            )
            //.clipToBounds()
            .run {
                if (gameObject2.isTexture) {
                    this.background(Color.Yellow)
                } else {
                    this
                }
            }
    } else {
        modifier.clipToBounds()
    }
//960003330350


    val bitmap = getImageBitmap(gameObject2.imageFileName)
    if (bitmap == null) return
    Box(mod) {
        if (gameObject2.isTexture) {
            Texture(gameObject2.imageFileName, gameObject2 = gameObject2)
        } else {
            Image(
                //painterResource(gameObject2.imageFileName),
                bitmap,
                contentDescription = gameObject2.imageFileName
            )
        }
    }
}

@Composable
fun Texture(fileName: String, modifier: Modifier = Modifier, gameObject2: GameObject2) {

    val bitmap = getImageBitmap(fileName)

    if (bitmap == null) return

//ImageBitmap.Companion?.imageResource
    Canvas(
        modifier = modifier
    ) {

        val paint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            shader =
                ImageShader(bitmap, TileMode.Repeated, TileMode.Repeated)
        }

        drawIntoCanvas {
            clipRect(
                0.0f,
                0.0f,
                gameObject2.mutableSize.value.x.toFloat(),
                gameObject2.mutableSize.value.y.toFloat() * 2
            ) {
                it.nativeCanvas.drawPaint(paint)
            }
        }
        paint.reset()
    }
}


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


/**
 * HorizontalAutoScroll
 *
 * @param speed : at what speed the object travels (in DP / second)
 * @param direction : which direction the object travel to: left or right
 *
 */
class HorizontalKeyboardScroll(
    speed: Int = 10,
    direction: Direction = Direction.LEFT,
) : HorizontalScroll(speed, direction) {


    private var distance: Int = 0

    fun distanceToMove(distance: Int = speed) {
        this.distance = distance
    }

    override fun postMoveDistance() {
        this.distance = 0
    }

    /**
     * implements moveDistance and based the distance
     * travelled with the keyboard
     * @return Int (distance in dp)
     */
    override fun moveDistance(): Int {
        return when (direction) {
            Direction.RIGHT -> {
                -distance
            }
            Direction.LEFT -> {
                distance
            }
        }
    }
}


val COMPOSE_GAME_DEBUG = true

/**
 * HorizontalAutoScroll
 *
 * @param initialPositionX : where the object should be placed
 * @param speed : at what speed the object travels (in DP / second)
 * @param direction : which direction the object travel to: left or right
 *
 */
abstract class HorizontalScroll(
    //private val initialPositionX: Int = 0,
    var speed: Int = 10,
    var direction: Direction = Direction.LEFT,
) {

    private var objectList: MutableList<GameObject2> = arrayListOf()

    /**
     * addObject
     * add an object to move
     * @param gameObject2
     */
    fun addObject(gameObject2: GameObject2) {
        objectList.add(gameObject2)
    }

    /**
     * deleteObject
     * stop moving this object
     * @param gameObject2
     */
    fun deleteObject(gameObject2: GameObject2) {
        objectList.remove(gameObject2)
    }

    /**
     * removeNotVisibleObjects
     * remove all objects that are not visible anymore (no need to move them)
     */
    private fun removeNotVisibleObjects() {
        val objectIterator = objectList.iterator()
        while (objectIterator.hasNext()) {
            val gameObject = objectIterator.next()
            if ((gameObject.mutablePosition.value.x + gameObject.mutableSize.value.x) < 0) {
                objectIterator.remove()
            }
        }
    }

    //var positionX = mutableStateOf(initialPositionX)

    /**
     * moveDistance
     * implement this method to tell this component
     * how much the items should move
     * @return Int (distance in dp)
     */
    abstract fun moveDistance(): Int

    /**
     * postMoveDistance
     * implement this method to tell this component
     * what to do once the distance has been provided
     * (you can reset the distance for instance)
     * @return Int (distance in dp)
     */
    abstract fun postMoveDistance()

    /**
     * update
     * process the update of all objects
     * move the objects according the parameters (speed and direction)
     */
    fun update() {
        val moveSize = moveDistance()
        this.postMoveDistance()


        // move to the left
        //positionX.value -= moveSize

        objectList.forEach {
            it.mutablePosition.value = Vector2(
                it.mutablePosition.value.x - moveSize.toInt(),
                it.mutablePosition.value.y
            )
            //it.actualSizeX.value += 50
        }

        removeNotVisibleObjects()
    }

    enum class Direction {
        LEFT,
        RIGHT
    }
}


//       y
//       ^
//       |
// ------+-------> x
//       |

class Placement(var gameCanvasSize: Vector2) {

    fun toCenter(gameObject: GameObject2): Vector2 {
        val y = (gameCanvasSize.y / 2) - (gameObject.mutableSize.value.y / 2)
        val x = (gameCanvasSize.x / 2) - (gameObject.mutableSize.value.x / 2)

        return Vector2(x, y)
    }

    fun toCenterLeft(gameObject: GameObject2): Vector2 {
        val x = 0.0
        val y = (gameCanvasSize.y / 2) - (gameObject.mutableSize.value.y / 2)

        return Vector2(x, y)
    }

    fun toBottomLeft(gameObject: GameObject2): Vector2 {
        val x = 0.0
        val y = gameCanvasSize.y - gameObject.mutableSize.value.y

        return Vector2(x, y)
    }

    fun toTopLeft(gameObject: GameObject2): Vector2 {
        val x = 0.0
        val y = 0.0

        return Vector2(x, y)
    }

    fun toTopRight(gameObject: GameObject2): Vector2 {
        val x = gameCanvasSize.x - gameObject.mutableSize.value.x
        val y = 0.0

        return Vector2(x, y)
    }

    fun toCenterRight(gameObject: GameObject2): Vector2 {
        val x = gameCanvasSize.x - gameObject.mutableSize.value.x
        val y = (gameCanvasSize.y / 2) - (gameObject.mutableSize.value.y / 2)

        return Vector2(x, y)
    }

    fun toTopCenter(gameObject: GameObject2): Vector2 {
        val x = (gameCanvasSize.x / 2) - (gameObject.mutableSize.value.x / 2)
        val y = 0.0

        return Vector2(x, y)
    }

    fun toBottomCenter(gameObject: GameObject2): Vector2 {
        val x = (gameCanvasSize.x / 2) - (gameObject.mutableSize.value.x / 2)
        val y = gameCanvasSize.y - gameObject.mutableSize.value.y

        return Vector2(x, y)
    }

    fun toBottomRight(gameObject: GameObject2): Vector2 {
        val x = gameCanvasSize.x - gameObject.mutableSize.value.x
        val y = gameCanvasSize.y - gameObject.mutableSize.value.y

        return Vector2(x, y)
    }

    fun putObjectAOnTopB(gameObjectA: GameObject2, gameObjectB: GameObject2): Vector2 {
        val x = gameObjectA.mutablePosition.value.x
        val y = gameObjectB.mutablePosition.value.y - gameObjectA.mutableSize.value.y

        return Vector2(x, y)
    }
}

/**
 * C G D K
 * Compose Game Development Kit
 * TODO rename to cgdkObject
 */
open class GameObject2(
    size: Vector2 = Vector2(0.0, 0.0),
    position: Vector2 = Vector2(0.0, 0.0)
) {
    val mutableSize = mutableStateOf<Vector2>(size)
    val mutablePosition = mutableStateOf<Vector2>(position)
    //var actualSizeX = mutableStateOf(size.x)
}

open class ImageGameObject(
    val imageFileName: String,
    val isTexture: Boolean = false,
    size: Vector2 = Vector2(0.0, 0.0),
    position: Vector2 = Vector2(0.0, 0.0)
) : GameObject2(size, position) {

    fun render() {

    }
}

open class CharacterGameObject(
    imageFileName: String,
    size: Vector2 = Vector2(0.0, 0.0),
    position: Vector2 = Vector2(0.0, 0.0)
) : ImageGameObject(
    imageFileName,
    false,
    size,
    position
) {
    private val mainScope = CoroutineScope(Dispatchers.Main)

    fun jump() {
        mainScope.launch {
            print("Start Jump \n")

            mutablePosition.value = Vector2(0.0, 0.0)
            delay(16)
            mutablePosition.value = Vector2(0.0, 10.0)
            delay(16)
            mutablePosition.value = Vector2(0.0, 20.0)
            delay(16)
            mutablePosition.value = Vector2(0.0, 30.0)
            delay(16)
            mutablePosition.value = Vector2(0.0, 40.0)
            delay(16)
            mutablePosition.value = Vector2(0.0, 50.0)
            delay(16)
            mutablePosition.value = Vector2(0.0, 60.0)
            delay(16)
            mutablePosition.value = Vector2(0.0, 70.0)
            delay(16)
            mutablePosition.value = Vector2(0.0, 80.0)
            delay(16)
            mutablePosition.value = Vector2(0.0, 70.0)
            delay(16)
            mutablePosition.value = Vector2(0.0, 60.0)
            delay(16)
            mutablePosition.value = Vector2(0.0, 50.0)
            delay(16)
            mutablePosition.value = Vector2(0.0, 40.0)
            delay(16)
            mutablePosition.value = Vector2(0.0, 30.0)
            delay(16)
            mutablePosition.value = Vector2(0.0, 20.0)
            delay(16)
            mutablePosition.value = Vector2(0.0, 10.0)
            delay(16)
            mutablePosition.value = Vector2(0.0, 0.0)
            delay(16)
        }
    }

}


/**
 * HorizontalAutoScroll
 *
 * @param initialPositionX : where the object should be placed
 * @param speed : at what speed the object travels (in DP / second)
 * @param direction : which direction the object travel to: left or right
 *
 */
class VerticalKeyboardScroll(
    speed: Int = 10,
    direction: Direction = Direction.UP,
) : VerticalScroll(speed, direction) {

    private val mainScope = CoroutineScope(Dispatchers.Main)

    private var distance: Int = 0

    fun distanceToMove(distance: Int = speed) {
        //this.distance = distance
        jumpAnimation()
    }

    override fun postMoveDistance() {
        this.distance = 0

    }

    fun jump(distance: Int) {
//        launch(Dispatchers.Default) {
//
//        }
    }

    val SPEED_1 = 160
    val SPEED_2 = 320
    val SPEED_3 = 640
    val SPEED_4 = 1280


    fun jump(objectList: List<GameObject2>, distance: Int, speed: Int) {

        val delay = speed / 40L


        objectList.forEach { gameObject ->
            mainScope.launch {
                print("Start Jump \n")
                // time: 8 * 16 = 128ms
                // distance:  80dp
                // 8 * 80 = 640dp/second
                for (i in 0..8) {
                    gameObject.mutablePosition.value = Vector2(
                        gameObject.mutablePosition.value.x,
                        gameObject.mutablePosition.value.y - 10
                    )
                    delay(delay)
                }
                for (i in 0..8) {
                    gameObject.mutablePosition.value = Vector2(
                        gameObject.mutablePosition.value.x,
                        gameObject.mutablePosition.value.y + 10
                    )
                    delay(delay)
                }
            }
        }
    }

    fun jumpAnimation() {
        objectList.forEach { gameObject ->
            mainScope.launch {
                print("Start Jump \n")
                // time: 8 * 16 = 128ms
                // distance:  80dp
                // 8 * 80 = 640dp/second
                for (i in 0..8) {
                    gameObject.mutablePosition.value = Vector2(
                        gameObject.mutablePosition.value.x,
                        gameObject.mutablePosition.value.y - 10
                    )
                    delay(16)
                }
                for (i in 0..8) {
                    gameObject.mutablePosition.value = Vector2(
                        gameObject.mutablePosition.value.x,
                        gameObject.mutablePosition.value.y + 10
                    )
                    delay(16)
                }
            }
        }
    }

    /**
     * implements moveDistance and based the distance
     * travelled with the keyboard
     * @return Int (distance in dp)
     */
    override fun moveDistance(): Int {
        return when (direction) {
            Direction.UP -> {
                distance
            }
            Direction.DOWN -> {
                -distance
            }
            else -> 0
        }
    }
}


/**
 * HorizontalAutoScroll
 *
 * @param initialPositionX : where the object should be placed
 * @param speed : at what speed the object travels (in DP / second)
 * @param direction : which direction the object travel to: left or right
 *
 */
abstract class VerticalScroll(
    //private val initialPositionY: Int = 0,
    var speed: Int = 10,
    var direction: Direction = Direction.UP,
) {

    protected val objectList: MutableList<GameObject2> = arrayListOf()

    /**
     * addObject
     * add an object to move
     * @param gameObject2
     */
    fun addObject(gameObject2: GameObject2) {
        objectList.add(gameObject2)
    }

    /**
     * deleteObject
     * stop moving this object
     * @param gameObject2
     */
    fun deleteObject(gameObject2: GameObject2) {
        objectList.remove(gameObject2)
    }

    /**
     * removeNotVisibleObjects
     * remove all objects that are not visible anymore (no need to move them)
     */
    private fun removeNotVisibleObjects() {
        val objectIterator = objectList.iterator()
        while (objectIterator.hasNext()) {
            val gameObject = objectIterator.next()
            if ((gameObject.mutablePosition.value.x + gameObject.mutableSize.value.x) < 0) {
                objectIterator.remove()
            }
        }
    }

    //var positionY = mutableStateOf(initialPositionY)

    /**
     * moveDistance
     * implement this method to tell this component
     * how much the items should move
     * @return Int (distance in dp)
     */
    abstract fun moveDistance(): Int

    /**
     * postMoveDistance
     * implement this method to tell this component
     * what to do once the distance has been provided
     * (you can reset the distance for instance)
     * @return Int (distance in dp)
     */
    abstract fun postMoveDistance()

    /**
     * update
     * process the update of all objects
     * move the objects according the parameters (speed and direction)
     */
    fun update() {
        val moveSize = moveDistance()
        this.postMoveDistance()

        // move to the left
        //positionY.value -= moveSize
        objectList.forEach {
            //print("moving UP : ${it.position.x} , ${it.position.y - moveSize.toInt()} (${moveSize.toInt()}) \n")
            it.mutablePosition.value =
                Vector2(it.mutablePosition.value.x, it.mutablePosition.value.y - moveSize.toInt())
        }

        //removeNotVisibleObjects()
    }

    enum class Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}