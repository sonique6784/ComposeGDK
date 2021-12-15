package me.sonique.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

expect fun getPlatformName(): String

expect fun getImageBitmap(resourcePath: String) : ImageBitmap?

