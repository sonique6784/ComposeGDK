package me.sonique.common.graphic

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import me.sonique.common.controller.IDirectionGameController
import me.sonique.common.log

@Composable
fun UIVirtualArrowController(directionGameController: IDirectionGameController) {
    Box(modifier = Modifier
        .width(200.dp)
        .height(200.dp)
        .background(Color.Green)
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = { offset ->
                    val w = 200.dp.toPx()
                    log("w: $w")

                    log("Position change: ${offset.print()}, drag: ${offset.print()} \n")
                    /*
                      |UP|
                    LF|__|RG
                      |DW|

                       w/2
                        |
                    ____c____ w
                        |
                        |
                    c = w/2, x/2
                    */

                    val x = offset.x
                    val y = offset.y

                    when {
                        // Up
                        y < w / 2 && x > w / 3 && x < (w / 3) * 2 -> {
                            log("UP")
                            directionGameController.up()
                        }
                        // DOWN
                        y > w / 2 && x > w / 3 && x < (w / 3) * 2 -> {
                            log("DOWN")
                            directionGameController.down()
                        }
                        // LEFT
                        x < w / 2 && y > w / 3 && y < (w / 3) * 2 -> {
                            log("LEFT")
                            directionGameController.left()
                        }
                        // RIgHT
                        x > w / 2 && y > w / 3 && y < (w / 3) * 2 -> {
                            log("RIGHT")
                            directionGameController.right()
                        }
                        else -> Unit
                    }

                    //...
                })
        }) {

    }
}

@Composable
fun UIJoystickController(directionGameController: IDirectionGameController) {
    Box(modifier = Modifier.width(200.dp).height(200.dp).background(Color.Red).pointerInput(Unit) {

        detectDragGestures { change, dragAmount ->
            val w = 200.dp.toPx()
            log("w: $w")

            log("Position change: ${change.position.print()}, drag: ${dragAmount.print()} \n")
            /*
              |UP|
            LF|__|RG
              |DW|

               w/2
                |
            ____c____ w
                |
                |
            c = w/2, x/2
            */

            val x = change.position.x
            val y = change.position.y



            when {
                // Up
                y < w / 2 && x > w / 3 && x < (w / 3) * 2 -> {
                    directionGameController.up()
                    log("UP")
                }
                // DOWN
                y > w / 2 && x > w / 3 && x < (w / 3) * 2 -> {
                    directionGameController.down()
                    log("DOWN")
                }
                // LEFT
                x < w / 2 && y > w / 3 && y < (w / 3) * 2 -> {
                    directionGameController.left()
                    log("LEFT")
                }
                // RIgHT
                x > w / 2 && y > w / 3 && y < (w / 3) * 2 -> {
                    directionGameController.right()
                    log("RIGHT")
                }
                else -> Unit
            }




            change.consumeAllChanges()
            //...
        }
    }) {

    }
}

fun Offset.print(): String {
    return "x: ${this.x} | y: ${this.y}"
}