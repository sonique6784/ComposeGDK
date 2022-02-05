package me.sonique.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.sonique.common.core.ImageCGDKObject

/**
 * GameImage
 * renders an ImageCGDKObject
 * 
 * @param imageGameObject: ImageCGDKObject
 * @param modifier: Modifier
 */
@Composable
fun GameImage(
    imageGameObject: ImageCGDKObject,
    modifier: Modifier = Modifier
) {
      val mod = modifier
            .height(imageGameObject.mutableSize.value.y.dp)
            .width(imageGameObject.mutableSize.value.x.dp)
            .offset(
                imageGameObject.mutablePosition.value.x.dp,
                imageGameObject.mutablePosition.value.y.dp
            )
            //.background(Color(0x88ff0000))

    val bitmap = getImageBitmap(imageGameObject.imageFileName) 
    if(bitmap == null) { 
        print("Image ${imageGameObject.imageFileName} NOT FOUND")
        return 
    }
    Box(mod) {
        if (imageGameObject.isTexture) {
            Texture(imageGameObject = imageGameObject)
        } else {
            Image(
                bitmap,
                contentDescription = imageGameObject.imageFileName
            )
        }
    }
}

