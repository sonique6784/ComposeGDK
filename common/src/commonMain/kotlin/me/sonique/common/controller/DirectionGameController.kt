package me.sonique.common.controller

import me.sonique.common.controller.IDirectionGameController

class DirectionGameController(
    private val leftCallback: () -> Unit,
    private val rightCallback: () -> Unit,
    private val upCallback: () -> Unit,
    private val downCallback: () -> Unit,
) : IDirectionGameController {
    override fun left() {
        leftCallback.invoke()
    }

    override fun right() {
        rightCallback.invoke()
    }

    override fun up() {
        upCallback.invoke()
    }

    override fun down() {
        downCallback.invoke()
    }
}