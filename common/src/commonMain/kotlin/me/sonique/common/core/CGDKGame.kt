package me.sonique.common.core

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import me.sonique.common.controller.GameController
import androidx.compose.runtime.Composable
import me.sonique.common.GameImage


open class CGDKGame(
    val gameObjects: SnapshotStateList<CGDKObject> = mutableStateListOf<CGDKObject>(),
    val gameControllers: MutableList<GameController> = mutableListOf<GameController>()
) {

    fun addGObject(CGDKObject: CGDKObject) {
        gameObjects.add(CGDKObject)
    }

    fun removeGObject(CGDKObject: CGDKObject) {
        gameObjects.remove(CGDKObject)
    }

    @Composable
    open fun render() {
        gameObjects.forEach {
            when (it) {
                // Depending on the type of GameObject, apply different processing
                is ImageCGDKObject -> GameImage(it)
                else -> Unit
            }
        }
    }
}