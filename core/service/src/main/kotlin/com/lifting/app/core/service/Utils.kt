package com.lifting.app.core.service

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import java.util.concurrent.TimeUnit

/**
 * Created by bedirhansaricayir on 11.06.2025
 */

fun getFormattedStopWatchTime(ms: Long?, spaces: Boolean = true): String {
    ms?.let {
        var milliseconds = ms

        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)

        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)

        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

        val separator = if (spaces) " : " else ":"
        return if (hours > 0) "${if (hours < 10) "0" else ""}$hours$separator" else "" +
                "${if (minutes < 10) "0" else ""}$minutes$separator" +
                "${if (seconds < 10) "0" else ""}$seconds"
    }
    return ""
}

/*
fun speakOrVibrate(
    tts: TextToSpeech?,
    vibrator: Vibrator,
    audioState: AudioState,
    sayText: String,
    vibrationLength: Long
) {
    when(audioState){
        AudioState.MUTE -> return
        AudioState.VIBRATE -> vibrate(vibrator, vibrationLength)
        AudioState.SOUND -> ttsSpeak(tts, sayText)
    }
}

fun vibrate(vibrator: Vibrator, ms: Long){
    if (vibrator.hasVibrator()) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(ms)
        }
    }
}

fun ttsSpeak(tts: TextToSpeech?, message: String){
    tts?.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
}*/
