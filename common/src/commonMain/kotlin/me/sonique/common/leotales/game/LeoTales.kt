package me.sonique.common.leotales.game

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.IntSize
import me.sonique.common.core.CGDKGame


val WINDOW_SIZE = IntSize(480, 320)

class LeoTales : CGDKGame() {

    val gameTitle = mutableStateOf("Leo Tales")
    val level = mutableStateOf(1)
    val energy = mutableStateOf(1)


}