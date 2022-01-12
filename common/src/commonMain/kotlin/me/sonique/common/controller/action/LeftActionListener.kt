package me.sonique.common.controller.action


/**
 * LeftActionListener
 * Listen for Left action, to trigger this action with objects
 * that should react to it.
 *  
 */
class LeftActionListener: ActionListener() {

    /**
     * onAction
     * find all objects that implement IOnKeyLeft
     * and trigger the action
     */
    override fun onAction() {
        this.objectList.filter { it is IOnKeyLeft }.forEach { (it as IOnKeyLeft).onKeyLeft() }
    }

    interface IOnKeyLeft {
        fun onKeyLeft()
    }
}