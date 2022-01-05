import me.sonique.common.App
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import me.sonique.common.GAME_SIZE
import me.sonique.common.WINDOW_SIZE

@ExperimentalComposeUiApi
fun main() = application {
    Window(onCloseRequest = ::exitApplication,
        state = WindowState(size = DpSize(WINDOW_SIZE.width.dp, WINDOW_SIZE.height.dp)),
        resizable = false) {
        MaterialTheme {
            App()
        }
    }
}