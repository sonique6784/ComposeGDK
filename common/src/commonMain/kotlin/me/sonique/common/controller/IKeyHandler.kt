package me.sonique.common.controller

import androidx.compose.ui.input.key.KeyEvent

interface IKeyHandler {
    fun handleKey(event: KeyEvent): Boolean
}