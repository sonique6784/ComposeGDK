package me.sonique.common.controller.action

import me.sonique.common.core.CGDKObject

/**
 * ActionListener
 * execute onAction for all object in objectList
 */
abstract class ActionListener() {
    protected val objectList: MutableList<CGDKObject> = mutableListOf()

    /**
     * addObject
     * add an object to move
     * @param CGDKObject
     */
    fun addObject(CGDKObject: CGDKObject) {
        objectList.add(CGDKObject)
    }

    /**
     * deleteObject
     * stop moving this object
     * @param CGDKObject
     */
    fun deleteObject(CGDKObject: CGDKObject) {
        objectList.remove(CGDKObject)
    }

    abstract fun onAction()

} 