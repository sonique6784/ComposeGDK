package me.sonique.common.controller

interface GameController {
    fun hasDirectionalPad(): Boolean
    fun actionKeyCount(): Int
    fun actionKeyNames(): List<String>
}