package com.lifting.app.core.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.lifting.app.core.service.Constants.ACTION_CANCEL
import com.lifting.app.core.service.Constants.ACTION_PAUSE
import com.lifting.app.core.service.Constants.ACTION_RESUME
import com.lifting.app.core.service.Constants.NOTIFICATION_CHANNEL_ID
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

/**
 * Created by bedirhansaricayir on 11.06.2025
 */

@Module
@InstallIn(ServiceComponent::class)
object RestTimerModule {

    private val flags =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

    @ClickPendingIntent
    @ServiceScoped
    @Provides
    fun provideClickPendingIntent(
        @ApplicationContext app: Context
    ): PendingIntent = PendingIntent.getActivity(
        app,
        0,
        app.packageManager.getLaunchIntentForPackage(app.packageName),
        flags
    )

    @CancelActionPendingIntent
    @ServiceScoped
    @Provides
    fun provideCancelActionPendingIntent(
        @ApplicationContext app: Context
    ): PendingIntent = PendingIntent.getService(
        app,
        1,
        Intent(app, RestTimerService::class.java).also {
            it.action = ACTION_CANCEL
        },
        flags
    )


    @ResumeActionPendingIntent
    @ServiceScoped
    @Provides
    fun provideResumeActionPendingIntent(
        @ApplicationContext app: Context
    ): PendingIntent = PendingIntent.getService(
        app,
        2,
        Intent(app, RestTimerService::class.java).also {
            it.action = ACTION_RESUME
        },
        flags
    )


    @PauseActionPendingIntent
    @ServiceScoped
    @Provides
    fun providePauseActionPendingIntent(
        @ApplicationContext app: Context
    ): PendingIntent = PendingIntent.getService(
        app,
        3,
        Intent(app, RestTimerService::class.java).also {
            it.action = ACTION_PAUSE
        },
        flags
    )


    @ServiceScoped
    @Provides
    fun provideBaseNotificationBuilder(
        @ApplicationContext app: Context,
        @ClickPendingIntent pendingIntent: PendingIntent
    ): NotificationCompat.Builder = NotificationCompat.Builder(app, NOTIFICATION_CHANNEL_ID)
        .setAutoCancel(false)
        .setOngoing(true)
        .setSmallIcon(R.drawable.ic_alarm)
        .setContentTitle("INTime")
        .setContentText("00:00:00")
        .setContentIntent(pendingIntent)

    @ServiceScoped
    @Provides
    fun provideVibrator(
        @ApplicationContext app: Context
    ): Vibrator = app.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    @ServiceScoped
    @Provides
    fun provideNotificationManager(
        @ApplicationContext app: Context
    ): NotificationManager = app.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager

}