package com.lifting.app.core.service

import android.content.Context
import android.content.Intent
import androidx.lifecycle.asFlow
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 11.06.2025
 */

class RestTimerRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : RestTimerRepository {

    private fun sendCommandToService(action: String, time: Long? = null) {
        Intent(context, RestTimerService::class.java).also {
            it.action = action
            if (time != null) {
                it.putExtra(Constants.EXTRA_TOTAL_TIME, time)
            }
            context.startService(it)
        }
    }

    override fun startTimer(time: Long) {
        sendCommandToService(Constants.ACTION_START, time)
    }

    override fun pauseTimer() {
        sendCommandToService(Constants.ACTION_PAUSE)
    }

    override fun resumeTimer() {
        sendCommandToService(Constants.ACTION_RESUME)
    }

    override fun cancelTimer() {
        sendCommandToService(Constants.ACTION_CANCEL)
    }

    override fun getTimerState(): Flow<TimerState> = RestTimerService.currentTimerState.asFlow()

    override fun getElapsedTimeMillisEverySeconds(): Flow<Long?> = RestTimerService.elapsedTimeInMillisEverySecond.asFlow()

    override fun getElapsedTimeMillis(): Flow<Long?> = RestTimerService.elapsedTimeInMillis.asFlow()

    override fun getTotalTimeMillis(): Flow<Long?> = RestTimerService.totalTimeInMillis.asFlow()
}