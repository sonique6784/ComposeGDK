package me.sonique.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.Modifier
//import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.dp
import me.sonique.common.controller.KeyboardDirectionControllerHelper
import me.sonique.common.core.ImageCGDKObject
import me.sonique.common.graphic.GameCanvas
import me.sonique.common.graphic.UIVirtualArrowController

@Composable
@ExperimentalComposeUiApi
fun Game() {

    val game = remember { MarioDemo() }

    var fpsMutable = mutableStateOf(0)
    var fps = remember { fpsMutable }

    val SHOW_FPS = true
    var lastFrame = 0L
    var frameCount = 0
    var lastSecond = 0L
    val density = LocalDensity.current
    LaunchedEffect(Unit) {
        while (true) {
            // We want to refresh the screen on a regular basis, so here we use a callback every millisecond
            withFrameMillis {
                // to prevent too many frame drawn and optimise a bit the performance,
                // we aim for ~60fps
                if(it - lastFrame >= 15 ) { // aiming for 60Hz
                    if(SHOW_FPS) {
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

                    game.update()
                }
            }
        }
    }

    Column(modifier = Modifier.background(Color(51, 153, 255)).fillMaxHeight()) {
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Compose Game Development Kit (DEMO)", modifier = Modifier.padding(8.dp))
            Text("Score: ${game.score().value}", color = Color.Yellow, modifier = Modifier.background(Color.Black).padding(8.dp))
            Text("FPS: ${fps.value}")
        }
/*
            // Mouse handling
            pointerMoveFilter(onMove = {
                with(density) {
                    game.targetLocation = DpOffset(it.x.toDp(), it.y.toDp())
                    print("mouse move: ${game.targetLocation.x} ${game.targetLocation.y} \n")
                }
                false
            })
 */


        GameCanvas(
            modifier = Modifier,
            backgroundColor = Color(161, 174, 253),
            keyboardHandler = KeyboardDirectionControllerHelper(game.getDirectionalController())
        ) {


            game.gameObjects.forEach {
                when (it) {
                    // Depending on the type of GameObject, apply different processing
                    is ImageCGDKObject -> GameImage(it)
                    else -> Unit
                }
            }

            //UIVirtualArrowController(game.getDirectionalController())
            //UIJoystickController(game.getDirectionalController())
        }
    }
}


