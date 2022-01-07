package me.sonique.common.controller

interface ITwoActionGameController: GameController {
    fun left()
    fun right()
    fun up()
    fun down()
    fun a()
    fun b()
}