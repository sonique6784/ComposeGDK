import androidx.compose.ui.unit.IntSize
import me.sonique.common.core.Vector2

// Extension func

fun IntSize.toVector2(): Vector2 {
    return Vector2(this.width.toDouble(), this.height.toDouble())
}