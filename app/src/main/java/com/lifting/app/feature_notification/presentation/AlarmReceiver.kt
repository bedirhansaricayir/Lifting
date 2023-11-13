package com.lifting.app.feature_notification.presentation

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.lifting.app.MainActivity
import com.lifting.app.R
import com.lifting.app.common.constants.NtfConstants.CHANNEL_ID
import com.lifting.app.common.constants.NtfConstants.NOTIFICATION_DESC
import com.lifting.app.common.constants.NtfConstants.NOTIFICATION_ID
import com.lifting.app.common.constants.NtfConstants.NOTIFICATION_TITLE
import com.lifting.app.common.constants.NtfConstants.REQUEST_CODE

 class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            Log.d("AlarmReceiver","Tringg...")
            sendNotification(context)
        }
    }

    private fun sendNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val pendingIntent: PendingIntent = createPendingIntent(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.app_launcher_icon)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(NOTIFICATION_DESC)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVibrate(LongArray(0))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID,builder)
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun createPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        var flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

        return PendingIntent.getActivity(
            context,
            REQUEST_CODE,
            intent,
            flags
        )
    }

}