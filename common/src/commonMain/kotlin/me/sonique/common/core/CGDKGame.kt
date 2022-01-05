package me.sonique.common.core

import androidx.compose.runtime.mutableStateListOf

open class CGDKGame {
    val gameObjects = mutableStateListOf<CGDKObject>()

    fun addGObject(CGDKObject: CGDKObject) {
        gameObjects.add(CGDKObject)
    }

    fun removeGObject(CGDKObject: CGDKObject) {
        gameObjects.remove(CGDKObject)
    }

    fun render() {

    }
}