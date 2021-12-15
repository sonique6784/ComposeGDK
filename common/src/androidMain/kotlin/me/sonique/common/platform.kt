package me.sonique.common

import android.content.Context
import android.graphics.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap


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