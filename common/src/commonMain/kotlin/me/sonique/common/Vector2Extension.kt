import androidx.compose.ui.unit.IntSize
import org.openrndr.math.Vector2

// Extension func
fun Vector2.lower(distanceY: Int): Vector2 {
    return Vector2(this.x, this.y + distanceY)
}
fun Vector2.higher(distanceY: Int): Vector2 {
    return Vector2(this.x, this.y - distanceY)
}

fun Vector2.leftShift(distanceX: Int): Vector2 {
    return Vector2(this.x - distanceX, this.y)
}

fun Vector2.rightShift(distanceX: Int): Vector2 {
    return Vector2(this.x + distanceX, this.y)
}

fun IntSize.toVector2(): Vector2 {
    return Vector2(this.width.toDouble(), this.height.toDouble())
}