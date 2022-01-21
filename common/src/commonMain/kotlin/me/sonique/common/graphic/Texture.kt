package me.sonique.common

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import me.sonique.common.core.ImageCGDKObject

/**
 * Texture
 * render a bitmap across the surface
 * 
 * @param imageGameObject: ImageCGDKObject - image Game object to render
 * @param modifier: Modifier
 */
@Composable
fun Texture(imageGameObject: ImageCGDKObject, modifier: Modifier = Modifier) {

    val bitmap = getImageBitmap(imageGameObject.imageFileName)

    if (bitmap == null) return

    Canvas(
        modifier = modifier
    ) {

        val paint = Paint().asFrameworkPaint().apply {
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
