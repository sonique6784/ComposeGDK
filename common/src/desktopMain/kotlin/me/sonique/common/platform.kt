package me.sonique.common

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource

actual fun getPlatformName(): String {
    return "Desktop"
}

actual fun getImageBitmap(resourcePath: String) : ImageBitmap? {
    return try {
        useResource(resourcePath) { loadImageBitmap(it) }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

actual fun getPrefix(): String {
    return ""
}