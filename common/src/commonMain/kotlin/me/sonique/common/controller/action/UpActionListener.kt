package me.sonique.common.controller.action


/**
 * UpActionListener
 * Listen for Up action, to trigger this action with objects
 * that should react to it.
 *  
 */
class UpActionListener: ActionListener() {

    /**
     * onAction
     * find all objects that implement IOnKeyUp
     * and trigger the action
     */
    override fun onAction() {
        this.objectList.filter { it is IOnKeyUp }.forEach { (it as IOnKeyUp).onKeyUp() }
    }

    interface IOnKeyUp {
        fun onKeyUp()
    }
}