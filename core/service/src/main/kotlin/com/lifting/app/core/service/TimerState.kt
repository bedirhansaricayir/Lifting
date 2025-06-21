package com.lifting.app.core.service

enum class TimerState(val stateName: String) {
    RUNNING("RUNNING"),
    PAUSED("PAUSED"),
    EXPIRED("EXPIRED");
}

fun TimerState.isRunning() = this != TimerState.EXPIRED