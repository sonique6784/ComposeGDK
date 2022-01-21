package me.sonique.common.core

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import me.sonique.common.controller.GameController
import androidx.compose.runtime.Composable
import me.sonique.common.GameImage
import androidx.compose.runtime.*

/**
 * CGDKGame
 * base class for rendering game object
 * 
 * @param gameObjects: SnapshotStateList<CGDKObject>
 * @param gameControllers: MutableList<GameController>
 * @param showFPS: Boolean (False by default)
 */
open class CGDKGame(
    val gameObjects: SnapshotStateList<CGDKObject> = mutableStateListOf<CGDKObject>(),
    val gameControllers: MutableList<GameController> = mutableListOf<GameController>(),
    var showFPS: Boolean = false
) {

    /**
     * addGObject
     * add a Game Object to be rendered
     * 
     * @param CGDKObject: CGDKObject
     */
    fun addGObject(CGDKObject: CGDKObject) {
        gameObjects.add(CGDKObject)
    }

    /**
     * removeGObject
     * remove a Game Object to be rendered
     * 
     * @param CGDKObject: CGDKObject
     */
    fun removeGObject(CGDKObject: CGDKObject) {
        gameObjects.remove(CGDKObject)
    }

    /**
     * update
     * this function is call every times a new frame is processed
     * you can add your scrollers and collision detection here
     */
    open fun update() = Unit

    /**
     * render all game object
     */
    @Composable
    open fun render() {
        gameObjects.forEach {
            when (it) {
                // Depending on the type of GameObject, apply different processing
                is ImageCGDKObject -> GameImage(it)
                else -> Unit
            }
        }
    }

    /**
     * fpsMutable 
     * can be used to show the FPS on the UI
     */
    var fpsMutable = mutableStateOf(0)

    /**
     * startRefresher
     * call this function to refresh and render frames
     * set showFPS to show the Frame Per Second in the console
     */
    @Composable
    fun startRefresher() {
        
        var lastFrame = 0L
        var frameCount = 0
        var lastSecond = 0L

        LaunchedEffect(Unit) {
            while (true) {
                // We want to refresh the screen on a regular basis, so here we use a callback every millisecond
                withFrameMillis {
                    // to prevent too many frame drawn and optimise a bit the performance,
                    // we aim for ~60fps
                    if(it - lastFrame >= 15 ) { // aiming for 60Hz
                        if(showFPS) {
                            if (lastSecond == 0L) {
                                lastSecond = it
                            }
                            frameCount++

                            if (it - lastSecond >= 1000) {
                                lastSecond = it
                                print("frame per second : $frameCount\n")
                                fpsMutable.value = frameCount
                                frameCount = 0
                            }
                        }

                        lastFrame = it

                        update()
                    }
                }
            }
        }
    }
}