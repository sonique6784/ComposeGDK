package me.sonique.common.graphic

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.key.*
import org.openrndr.math.Vector2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.sonique.common.controller.IKeyHandler
import me.sonique.common.core.ImageCGDKObject


@Composable
inline fun GameCanvas(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(255, 255, 255),
    keyboardHandler: IKeyHandler? = null, // Should we let the user add the handler themselve to the modifier?

    // Content must be last parameter
    content: @Composable BoxScope.() -> Unit,
) {
    // Allow the canvas to receive KeyEvents
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = modifier.onKeyEvent { event ->
            return@onKeyEvent keyboardHandler?.handleKey(event) ?: false
        }.clickable() {
            print("request focus\n")
        }
            .focusRequester(focusRequester)
            .background(backgroundColor)
            .fillMaxWidth()
            .fillMaxHeight()
            .focusable()
    ) {
        // Insert the content provided as last paramter
        content()

        // Allow us to request focus after composition
        SideEffect {
            focusRequester.requestFocus()
        }
    }
}











