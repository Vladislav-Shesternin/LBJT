package com.education.lbjt.game.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Completer(coroutineScope: CoroutineScope, count: Int, doAfterComplete: () -> Unit = {}) {

    private val flow = MutableStateFlow(0)

    var block: () -> Unit = doAfterComplete

    init {
        coroutineScope.launch {
            flow.collect { if (it == count) block() }
        }
    }

    fun complete() { flow.value += 1 }

}