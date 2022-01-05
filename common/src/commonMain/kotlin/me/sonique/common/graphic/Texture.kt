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
import me.sonique.common.core.CGDKObject

@Composable
fun Texture(fileName: String, modifier: Modifier = Modifier, CGDKObject: CGDKObject) {

    val bitmap = getImageBitmap(fileName)

    if (bitmap == null) return

//ImageBitmap.Companion?.imageResource
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
                CGDKObject.mutableSize.value.x.toFloat(),
                CGDKObject.mutableSize.value.y.toFloat() * 2
            ) {
                it.nativeCanvas.drawPaint(paint)
            }
        }
        paint.reset()
    }
}
