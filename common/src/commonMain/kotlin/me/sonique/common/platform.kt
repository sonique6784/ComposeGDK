package me.sonique.common

import androidx.compose.ui.graphics.ImageBitmap
import me.sonique.common.core.ImageCGDKObject
import androidx.compose.ui.Modifier

expect fun getPlatformName(): String

expect fun getImageBitmap(resourcePath: String) : ImageBitmap?

expect fun log(string: String)

expect fun texture_paint(imageGameObject: ImageCGDKObject, bitmap: ImageBitmap, modifier: Modifier) 