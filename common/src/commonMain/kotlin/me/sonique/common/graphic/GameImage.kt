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

@Composable
fun GameImage(
    gameObject2: ImageCGDKObject,
    modifier: Modifier = Modifier
) {
    val mod = if (gameObject2 != null) {
        modifier
            .height(gameObject2.mutableSize.value.y.dp)
            .width(gameObject2.mutableSize.value.x.dp)
            .offset(
                gameObject2.mutablePosition.value.x.dp,
                gameObject2.mutablePosition.value.y.dp
            )
            //.clipToBounds()
            .run {
                if (gameObject2.isTexture) {
                    this.background(Color.Yellow)
                } else {
                    this
                }
            }
    } else {
        modifier.clipToBounds()
    }
//960003330350


    val bitmap = getImageBitmap(gameObject2.imageFileName)
    if (bitmap == null) return
    Box(mod) {
        if (gameObject2.isTexture) {
            Texture(gameObject2.imageFileName, CGDKObject = gameObject2)
        } else {
            Image(
                //painterResource(gameObject2.imageFileName),
                bitmap,
                contentDescription = gameObject2.imageFileName
            )
        }
    }
}

