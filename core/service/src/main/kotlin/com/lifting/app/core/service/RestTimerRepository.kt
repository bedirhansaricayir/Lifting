package com.lifting.app.core.service

import kotlinx.coroutines.flow.Flow

/**
 * Created by bedirhansaricayir on 11.06.2025
 */
interface RestTimerRepository {
    fun startTimer(time: Long)
    fun pauseTimer()
    fun resumeTimer()
    fun cancelTimer()
    fun getTimerState(): Flow<TimerState>
    fun getElapsedTimeMillisEverySeconds(): Flow<Long?>
    fun getElapsedTimeMillis(): Flow<Long?>
    fun getTotalTimeMillis(): Flow<Long?>
}