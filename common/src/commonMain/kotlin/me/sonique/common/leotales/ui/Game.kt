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
import me.sonique.common.graphic.GameCanvas
import me.sonique.common.leotales.game.LeoTales

@Composable
@ExperimentalComposeUiApi
fun Game() {

    val game = remember { LeoTales() }


    Column(modifier = Modifier.fillMaxHeight()) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(game.gameTitle.value, modifier = Modifier.padding(8.dp))
            Text(
                "Level:  ${game.level.value}",
                modifier = Modifier.padding(8.dp)
            )
            Text("Energy: ${game.energy.value}", modifier = Modifier.padding(8.dp))
        }
        GameCanvas {

        }
    }
}