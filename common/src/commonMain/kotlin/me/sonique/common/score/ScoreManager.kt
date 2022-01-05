package me.sonique.common.score

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ScoreManager() {
    val score = MutableSharedFlow<Int>()
    val mutableScore = mutableStateOf(0)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    init {
        //TODO: find a flow or channel the receive same value (like when we send 1 two times in the row)
        mainScope.launch {
            score.collect {
                mutableScore.value += it
                print("score: ${mutableScore.value} \n")
            }
        }

    }

    companion object {
        val ScoreManager = ScoreManager()
    }
}