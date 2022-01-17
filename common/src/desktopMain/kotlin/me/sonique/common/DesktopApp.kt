package me.sonique.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import me.sonique.common.mariodemo.Game

@Preview
@Composable
@ExperimentalComposeUiApi
fun AppPreview() {
    Game()
}