package me.sonique.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.Modifier
//import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi

@Composable
@ExperimentalComposeUiApi
fun App() {
//    var text by remember { mutableStateOf("Hello, World!") }
//
//    Button(onClick = {
//        text = "Hello, ${getPlatformName()}"
//    }) {
//        Text(text)
//    }

    val game = remember { MyGame() }

    val horizontalCharacterScroll = remember { game.horizontalCharacterScroll }
    val verticalCharacterScroll = remember { game.verticalCharacterScroll }
    val horizontalFrontScroll = remember { game.horizontalFrontScroll }
    val horizontalMediumScroll = remember { game.horizontalMediumScroll }
    val horizontalBackScroll = remember { game.horizontalBackScroll }

    val placementHelper = Placement(GAME_SIZE.toVector2())

    val density = LocalDensity.current
    LaunchedEffect(Unit) {
        while (true) {
            withFrameMillis {
                horizontalCharacterScroll.update()
                verticalCharacterScroll.update()
                horizontalFrontScroll.update()
                horizontalMediumScroll.update()
                horizontalBackScroll.update()
                game.update()
            }
        }
    }





    Column(modifier = Modifier.background(Color(51, 153, 255)).fillMaxHeight()) {
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Compose Game Development Kit (DEMO)")
            Text("Score: ${game.score().value}")
        }
/*
pointerMoveFilter(onMove = {
//                with(density) {
//                    game.targetLocation = DpOffset(it.x.toDp(), it.y.toDp())
//                    print("mouse move: ${game.targetLocation.x} ${game.targetLocation.y} \n")
//                }
                false
            })
 */
        GameCanvas(
            modifier = Modifier, backgroundColor = Color(161, 174, 253),
            keyboardHandler = KeyboardMovementHelper(leftCallback = {
                horizontalCharacterScroll.direction = HorizontalScroll.Direction.LEFT
                horizontalCharacterScroll.distanceToMove()
            }, rightCallback = {
                horizontalCharacterScroll.direction = HorizontalScroll.Direction.RIGHT
                horizontalCharacterScroll.distanceToMove()
            }, upCallback = {
                verticalCharacterScroll.direction = VerticalScroll.Direction.UP
                verticalCharacterScroll.distanceToMove()
            }, downCallback = {
                verticalCharacterScroll.direction = VerticalScroll.Direction.DOWN
                verticalCharacterScroll.distanceToMove()
            })
        ) {

//                horizontalFrontScroll.positionX.value.dp
//                horizontalCharacterScroll.positionX.value.dp
//                verticalCharacterScroll.positionY.value.dp
//                horizontalMediumScroll.positionX.value.dp
//                horizontalBackScroll.positionX.value.dp

            //Text("Hello: ${horizontalCharacterScroll.positionX.value}")


            game.gameObjects.forEach {
                when(it) {
                    is ImageGameObject -> GameImage(it)
                    else -> Unit
                }
            }
        }
    }
}


