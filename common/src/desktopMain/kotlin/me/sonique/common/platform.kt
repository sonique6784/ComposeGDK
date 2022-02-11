package me.sonique.common

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.runtime.Composable
import me.sonique.common.core.ImageCGDKObject
import androidx.compose.foundation.Canvas

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

actual fun log(string: String) {
    print(string)
}

@Composable
actual fun texture_paint(imageGameObject: ImageCGDKObject, bitmap: ImageBitmap,  modifier: Modifier) {
    Canvas(
        modifier = modifier
    ) {

        val paint = Paint().asFrameworkPaint()
            .apply {
            isAntiAlias = true
            shader =
                ImageShader(bitmap, TileMode.Repeated, TileMode.Repeated)
        }

        drawIntoCanvas {
            clipRect(
                0.0f,
                0.0f,
                imageGameObject.mutableSize.value.x.toFloat(),
                imageGameObject.mutableSize.value.y.toFloat()
            ) {
                it.nativeCanvas.drawPaint(paint)
            }
        }
        paint.reset()
    }
}