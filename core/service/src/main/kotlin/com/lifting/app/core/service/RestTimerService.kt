package com.lifting.app.core.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.os.PowerManager
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.lifting.app.core.service.Constants.ACTION_CANCEL
import com.lifting.app.core.service.Constants.ACTION_CANCEL_AND_RESET
import com.lifting.app.core.service.Constants.ACTION_INITIALIZE_DATA
import com.lifting.app.core.service.Constants.ACTION_MUTE
import com.lifting.app.core.service.Constants.ACTION_PAUSE
import com.lifting.app.core.service.Constants.ACTION_RESUME
import com.lifting.app.core.service.Constants.ACTION_SOUND
import com.lifting.app.core.service.Constants.ACTION_START
import com.lifting.app.core.service.Constants.ACTION_VIBRATE
import com.lifting.app.core.service.Constants.EXTRA_TOTAL_TIME
import com.lifting.app.core.service.Constants.NOTIFICATION_CHANNEL_ID
import com.lifting.app.core.service.Constants.NOTIFICATION_CHANNEL_NAME
import com.lifting.app.core.service.Constants.TIMER_UPDATE_INTERVAL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 11.06.2025
 */

@AndroidEntryPoint
class RestTimerService : LifecycleService(), TextToSpeech.OnInitListener {

    @Inject
    lateinit var baseNotificationBuilder: NotificationCompat.Builder
    lateinit var currentNotificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var notificationManager: NotificationManager

    @ClickPendingIntent
    @Inject
    lateinit var clickPendingIntent: PendingIntent

    @ResumeActionPendingIntent
    @Inject
    lateinit var resumeActionPendingIntent: PendingIntent

    @PauseActionPendingIntent
    @Inject
    lateinit var pauseActionPendingIntent: PendingIntent

    @CancelActionPendingIntent
    @Inject
    lateinit var cancelActionPendingIntent: PendingIntent

    private var isInitialized = false
    private var isKilled = true
    private var isBound = false

    private var timer: CountDownTimer? = null
    private var millisToCompletion = 0L
    private var lastSecondTimestamp = 0L
    private var timerIndex = 0
    private var timerMaxRepetitions = 0

    @Inject
    lateinit var vibrator: Vibrator
    private var tts: TextToSpeech? = null

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    private var wakeLock: PowerManager.WakeLock? = null

    companion object {
        val currentTimerState = MutableLiveData<TimerState>()
        val elapsedTimeInMillis = MutableLiveData<Long?>()
        val elapsedTimeInMillisEverySecond = MutableLiveData<Long?>()
        val totalTimeInMillis = MutableLiveData<Long?>()
    }


    override fun onCreate() {
        super.onCreate()
        currentNotificationBuilder = baseNotificationBuilder
        tts = TextToSpeech(this, this)
        setupObservers()
    }

    override fun onInit(status: Int) {
        initializeTTS(status = status)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        intent?.let {
            when (it.action) {
                ACTION_INITIALIZE_DATA -> {
                    /*Is called when navigating from ListScreen to DetailScreen, fetching data
                * from database here -> data initialization*/
                    initializeData(it)
                }
                ACTION_START -> {
                    /*This is called when Start-Button is pressed, starting timer here and setting*/
                    val intentTime =
                        it.extras?.getLong(EXTRA_TOTAL_TIME)
                    if (intentTime != null) {
                        startServiceTimer(intentTime)
                    }
                }
                ACTION_PAUSE -> {
                    /*Called when pause button is pressed, pause timer, set isTimerRunning = false*/
                    pauseTimer()
                }
                ACTION_RESUME -> {
                    /*Called when resume button is pressed, resume timer here, set isTimerRunning
                * = true*/
                    resumeTimer()
                }
                ACTION_CANCEL -> {
                    /*This is called when cancel button is pressed - resets the current timer to
                * start state*/
                    cancelServiceTimer()
                }
                ACTION_CANCEL_AND_RESET -> {
                    /*Is called when navigating back to ListsScreen, resetting acquired data
                * to null*/
                    cancelServiceTimer()
                    resetData()
                }

                // Audio related actions
                ACTION_MUTE -> {
                    /*Sets current audio state to mute*/
//                    audioState = AudioState.MUTE
                }
                ACTION_VIBRATE -> {
                    /*Sets current audio state to vibrate*/
//                    audioState = AudioState.VIBRATE
                }
                ACTION_SOUND -> {
                    /*Sets current audio state to sound enabled*/
//                    audioState = AudioState.SOUND
                }
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        // UI is visible, use service without being foreground
        isBound = true
        if (!isKilled) pushToBackground()
        return super.onBind(intent)
    }

    override fun onRebind(intent: Intent?) {
        // UI is visible again, push service to background -> notification are not visible
        isBound = true
        if (!isKilled) pushToBackground()
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        // UI is not visible anymore, push service to foreground -> notifications visible
        isBound = false
        if (!isKilled && currentTimerState.value != TimerState.EXPIRED) pushToForeground()
        // return true so onRebind is used if service is alive and client connects
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
        tts?.stop()
        tts?.shutdown()
    }

    private fun startTimer(wasPaused: Boolean = false, newTotalTime: Long? = null) {
        val time = (if (wasPaused) millisToCompletion else newTotalTime) ?: return

        elapsedTimeInMillisEverySecond.postValue(time)
        elapsedTimeInMillis.postValue(time)

        lastSecondTimestamp = time

        timer = object : CountDownTimer(time, TIMER_UPDATE_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                onTimerTick(millisUntilFinished)
            }

            override fun onFinish() {
                onTimerFinish()
            }
        }.start()
    }

    private fun onTimerTick(millisUntilFinished: Long) {
        millisToCompletion = millisUntilFinished
        elapsedTimeInMillis.postValue(millisUntilFinished)

        if (millisUntilFinished <= lastSecondTimestamp - 1000L) {
            lastSecondTimestamp -= 1000L
            elapsedTimeInMillisEverySecond.postValue(lastSecondTimestamp)

//            // if lastSecondTimestamp within 3 seconds of end, start counting/vibrating
//            if (lastSecondTimestamp <= 3000L)
//                speakOrVibrate(
//                    tts = tts,
//                    vibrator = vibrator,
//                    audioState = audioState,
//                    sayText = millisToSeconds(lastSecondTimestamp).toString(),
//                    vibrationLength = 200L
//                )
        }
    }

    private fun onTimerFinish() {
        timerIndex += 1
        cancelTimer()
    }

    private fun pauseTimer() {
        currentTimerState.postValue(TimerState.PAUSED)
        timer?.cancel()
    }

    private fun resumeTimer() {
        currentTimerState.postValue(TimerState.RUNNING)
        startTimer(wasPaused = true)
    }

    private fun cancelTimer() {
        timer?.cancel()
        resetTimer()
    }

    private fun resetTimer() {
        timerIndex = 0
        postInitData()
    }

    private fun initializeData(intent: Intent) {
        if (!isInitialized) {
            intent.extras?.let {

                serviceScope.launch {
//                        audioState = AudioState.valueOf(preferenceRepository.getCurrentSoundState())
                    isInitialized = true
                    postInitData()
                }
//                }
            }
        }
    }

    private fun initializeTTS(status: Int) {
        tts?.let {
            if (status == TextToSpeech.SUCCESS) {
                val result = it.setLanguage(Locale.US)
                it.voice = it.defaultVoice
                it.language = Locale.US
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                }
            } else {
            }
        }
    }

    private fun postInitData() {
        currentTimerState.postValue(TimerState.EXPIRED)
        elapsedTimeInMillis.postValue(null)
        totalTimeInMillis.postValue(null)
        elapsedTimeInMillisEverySecond.postValue(null)
    }

    private fun resetData() {
        isInitialized = false
    }

    private fun startServiceTimer(newTotalTime: Long) {
        acquireWakelock()
        isKilled = false
        resetTimer()
        startTimer(newTotalTime = newTotalTime)
        totalTimeInMillis.postValue(newTotalTime)
        currentTimerState.postValue(TimerState.RUNNING)
    }

    private fun cancelServiceTimer() {
        releaseWakelock()
        cancelTimer()
        currentTimerState.postValue(TimerState.EXPIRED)
        isKilled = true
        stopForeground(true)
    }

    private fun acquireWakelock() {
        wakeLock = (getSystemService(POWER_SERVICE) as PowerManager).run {
            newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK,
                "com.lifting.app.core.service:RestTimerService::lock"
            ).apply {
                acquire()
            }
        }
    }

    private fun releaseWakelock() {
        try {
            wakeLock?.let {
                if (it.isHeld) {
                    it.release()
                }
            }
        } catch (e: Exception) {
        }
    }

    private fun pushToForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createNotificationChannel(notificationManager)
        startForeground(Constants.NOTIFICATION_ID, baseNotificationBuilder.build())

        currentTimerState.value?.let { updateNotificationActions(it) }
    }

    private fun pushToBackground() {
        stopForeground(true)
    }

    private fun setupObservers() {
        currentTimerState.observe(this) {
            if (!isKilled && !isBound)
                updateNotificationActions(it)
        }

        elapsedTimeInMillisEverySecond.observe(this) {
            if (!isKilled && !isBound) {
                val notification = currentNotificationBuilder
                    .setContentText(getFormattedStopWatchTime(it))
                notificationManager.notify(Constants.NOTIFICATION_ID, notification.build())
            }
        }
    }

    private fun updateNotificationActions(state: TimerState) {
        val notificationActionText = if (state == TimerState.RUNNING) "Pause" else "Resume"

        val pendingIntent = when (state) {
            TimerState.RUNNING -> {
                pauseActionPendingIntent
            }
            else -> {
                resumeActionPendingIntent
            }
        }

        currentNotificationBuilder.javaClass.getDeclaredField("mActions").apply {
            isAccessible = true
            set(currentNotificationBuilder, ArrayList<NotificationCompat.Action>())
        }

        currentNotificationBuilder = baseNotificationBuilder
            .setContentTitle("Timer")
            .addAction(R.drawable.ic_alarm, notificationActionText, pendingIntent)
            .addAction(R.drawable.ic_alarm, "Cancel", cancelActionPendingIntent)
        notificationManager.notify(Constants.NOTIFICATION_ID, currentNotificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

}