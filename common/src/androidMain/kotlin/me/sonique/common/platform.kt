package me.sonique.common

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Canvas
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Paint as Paintx
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.runtime.Composable
import me.sonique.common.core.ImageCGDKObject
import androidx.compose.foundation.Canvas as Canvasx

class ImageBitmapHelper {
    companion object {
        private var context: Context? = null
        fun setContext(context: Context) {
            this.context = context
        }
        fun getContext(): Context {
            return context ?: throw Exception("ImageBitmapHelper.context must be initialized")
        }
    }
}

actual fun getPlatformName(): String {
    return "Android"
}

actual fun getImageBitmap(resourcePath: String): ImageBitmap? {
    return try {
        val options = BitmapFactory.Options()
        options.inSampleSize = 3

        //val stream = requireContext().assets.open(resourcePath).buffered()

        //val bitmap = BitmapFactory.decodeFile(getPrefix()+resourcePath, options)
        val bitmap = BitmapFactory.decodeStream(ImageBitmapHelper.getContext().assets.open(resourcePath))
        bitmap.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()

        val width = 200
        val height = 150
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint()
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        canvas.drawPaint(paint)

        paint.color = Color.RED
        paint.isAntiAlias = true
        paint.textSize = 14f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("$resourcePath\nnot found", width / 2f, height / 2f, paint)

        bitmap.asImageBitmap()
    }
}

actual fun log(string: String) {
    Log.w("KMM", string)
}

@Composable
actual fun texture_paint(imageGameObject: ImageCGDKObject, bitmap: ImageBitmap,  modifier: Modifier) {
    Canvasx(
        modifier = modifier
    ) {

        val paint = Paintx().asFrameworkPaint()
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