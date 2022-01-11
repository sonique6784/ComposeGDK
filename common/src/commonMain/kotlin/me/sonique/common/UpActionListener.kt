package me.sonique.common

import me.sonique.common.core.CGDKObject
import me.sonique.common.controller.event.IOnKeyUp

/*
 * UpActionListener
 * Listen for Up action, to trigger this action with object
 * that should react to it.
 *  
 */
class UpActionListener: ActionListener() {

    /*
     * onAction
     * find all object that implements IOnKeyUp 
     * and trigger the action
     */
    override fun onAction() {
        this.objectList.filter { it is IOnKeyUp }.forEach { (it as IOnKeyUp).onKeyUp() }
    }
}