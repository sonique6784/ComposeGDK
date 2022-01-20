package me.sonique.common.leotales.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import me.sonique.common.graphic.GameCanvas
import me.sonique.common.leotales.game.LeoTales


@Composable
@ExperimentalComposeUiApi
fun Game() {

    val game = remember { LeoTales() }
    var lastFrame = 0L

    LaunchedEffect(Unit) {
        while (true) {
            // We want to refresh the screen on a regular basis, so here we use a callback every millisecond
            withFrameMillis {
                // to prevent too many frame drawn and optimise a bit the performance,
                // we aim for ~60fps
                if(it - lastFrame >= 15 ) { // aiming for 60Hz
                    // if(SHOW_FPS) {
                    //     if (lastSecond == 0L) {
                    //         lastSecond = it
                    //     }
                    //     frameCount++

                    //     if (it - lastSecond >= 1000) {
                    //         lastSecond = it
                    //         print("frame per second : $frameCount\n")
                    //         fpsMutable.value = frameCount
                    //         frameCount = 0
                    //     }
                    // }

                    lastFrame = it

                    game.update()
                }
            }
        }
    }


    Column(modifier = Modifier.fillMaxHeight()) {
        Row(modifier = Modifier.fillMaxWidth().height(42.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(game.gameTitle.value, modifier = Modifier.padding(8.dp))
            Text(
                "Level:  ${game.level.value}",
                modifier = Modifier.padding(8.dp)
            )
            Text("Energy: ${game.energy.value}", modifier = Modifier.padding(8.dp))
        }
        GameCanvas {
            game.render()
        }
    }
}