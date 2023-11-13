package com.lifting.app.feature_notification.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.lifting.app.feature_notification.domain.AlarmScheduler
import com.lifting.app.feature_notification.domain.model.AlarmTime
import com.lifting.app.feature_notification.presentation.AlarmReceiver
import java.util.Calendar
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val context: Context
) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(alarmTime: AlarmTime) {
        val intent = Intent(context, AlarmReceiver::class.java)

        val triggerTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, alarmTime.hour)
            set(Calendar.MINUTE, alarmTime.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        if (triggerTime.timeInMillis < System.currentTimeMillis()){
            triggerTime.add(Calendar.DAY_OF_YEAR, 1)
        }
        Toast.makeText(context,"Alarm planlandı: ${triggerTime.time}",Toast.LENGTH_LONG).show()
        Log.d("NextTriggerTime","Alarm scheduled at ${triggerTime.time}")

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC,
            triggerTime.timeInMillis,
            PendingIntent.getBroadcast(
                context,
                3,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel() {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                3,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}