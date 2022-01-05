package me.sonique.common.controller

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*


class KeyboardDirectionControllerHelper(
    private val directionGameController: IDirectionGameController
) : IKeyHandler {

    @ExperimentalComposeUiApi
    override fun handleKey(event: KeyEvent): Boolean {
        print("Key pressed: ${event.type}  ${event.key}\n")
        return if (event.type == KeyEventType.KeyDown) {
            when (event.key) {
                Key.DirectionLeft -> isLeft()
                Key.DirectionUp -> isUp()
                Key.DirectionRight -> isRight()
                Key.DirectionDown -> isDown()
            }
            false
        } else {
            true
        }
    }

    private fun isLeft() {
        print("Left\n")
        directionGameController.left()
    }

    private fun isRight() {
        print("Right\n")
        directionGameController.right()
    }

    private fun isUp() {
        print("Up\n")
        directionGameController.up()
    }

    private fun isDown() {
        print("Down\n")
        directionGameController.down()
    }
}