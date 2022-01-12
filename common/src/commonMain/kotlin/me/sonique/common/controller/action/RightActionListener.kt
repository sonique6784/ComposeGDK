package me.sonique.common.controller.action


/**
 * RightActionListener
 * Listen for Right action, to trigger this action with objects
 * that should react to it.
 *  
 */
class RightActionListener: ActionListener() {

    /**
     * onAction
     * find all objects that implement IOnKeyRight
     * and trigger the action
     */
    override fun onAction() {
        this.objectList.filter { it is IOnKeyRight }.forEach { (it as IOnKeyRight).onKeyRight() }
    }

    interface IOnKeyRight {
        fun onKeyRight()
    }
}