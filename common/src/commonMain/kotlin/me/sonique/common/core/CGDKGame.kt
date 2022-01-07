package me.sonique.common.core

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import me.sonique.common.controller.GameController

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
}