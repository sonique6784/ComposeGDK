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
import me.sonique.common.log


@Composable
fun UITouchController() {
    Box(modifier = Modifier.width(200.dp).height(200.dp).background(Color.Red).pointerInput(Unit) {
//        detectTapGestures(
//            onPress = {/* Called when the gesture starts */ offset ->
//                log("onPress: ${offset.print()} \n")
//                      },
//            onDoubleTap = { /* Called on Double Tap */
//                          },
//            onLongPress = { /* Called on Long Press */
//                          },
//            onTap = { /* Called on Tap */
//                    offset ->
//                log("onTap: ${offset.print()} \n")
//            }
//        )
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
                y < w/2 && x > w/3 && x < (w/3) * 2 -> {
                    log("UP")
                }
                // DOWN
                y > w/2 && x > w/3 && x < (w/3) * 2-> {
                    log("DOWN")
                }
                // LEFT
                x < w/2 && y > w/3 && y < (w/3) * 2 -> {
                    log("LEFT")
                }
                // RIgHT
                x > w/2 && y > w/3 && y < (w/3) * 2 -> {
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