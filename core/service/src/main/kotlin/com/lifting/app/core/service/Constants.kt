package com.lifting.app.core.service

/**
 * Created by bedirhansaricayir on 11.06.2025
 */

object Constants {
    const val ACTION_START              = "ACTION_START"
    const val ACTION_RESUME             = "ACTION_RESUME"
    const val ACTION_PAUSE              = "ACTION_PAUSE"
    const val ACTION_CANCEL             = "ACTION_CANCEL"
    const val ACTION_CANCEL_AND_RESET   = "ACTION_CANCEL_AND_RESET"
    const val ACTION_INITIALIZE_DATA    = "ACTION_INITIALIZE_DATA"
    const val ACTION_MUTE               = "ACTION_MUTE"
    const val ACTION_VIBRATE            = "ACTION_VIBRATE"
    const val ACTION_SOUND              = "ACTION_SOUND"
    const val ACTION_SHOW_MAIN_ACTIVITY = "ACTION_SHOW_MAIN_ACTIVITY"

    const val EXTRA_REPETITION          = "EXTRA_REPETITION"
    const val EXTRA_EXERCISETIME        = "EXTRA_EXERCISETIME"
    const val EXTRA_PAUSETIME           = "EXTRA_PAUSETIME"
    const val EXTRA_WORKOUT_ID          = "EXTRA_WORKOUT_ID"

    const val EXTRA_TOTAL_TIME          = "EXTRA_TOTAL_TIME"

    const val NOTIFICATION_CHANNEL_ID   = "timer_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Timer"
    const val NOTIFICATION_ID           = 1

    const val ONE_SECOND                = 1000L
    const val TIMER_UPDATE_INTERVAL     = 5L    //5ms
    const val TIMER_STARTING_IN_TIME    = 5000L //5s
}