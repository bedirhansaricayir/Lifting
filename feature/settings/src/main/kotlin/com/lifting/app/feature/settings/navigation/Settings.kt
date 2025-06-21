package com.lifting.app.feature.settings.navigation

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.content.pm.PackageInfoCompat
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.ui.BaseComposableLayout
import com.lifting.app.core.ui.components.in_app_notification.LiftingInAppNotificationData
import com.lifting.app.core.ui.components.in_app_notification.LiftingNotificationType
import com.lifting.app.core.ui.components.in_app_notification.LocalNotificationManager
import com.lifting.app.core.ui.components.in_app_notification.NotificationManager
import com.lifting.app.feature.settings.SettingsScreen
import com.lifting.app.feature.settings.SettingsUIEffect
import com.lifting.app.feature.settings.SettingsViewModel
import java.time.LocalDateTime

/**
 * Created by bedirhansaricayir on 16.05.2025
 */
fun NavGraphBuilder.settingsScreen(
) {
    composable<LiftingScreen.Settings> {
        val viewModel: SettingsViewModel = hiltViewModel()
        val notificationManager = LocalNotificationManager.current

        BaseComposableLayout(
            viewModel = viewModel,
            effectHandler = { context, effect ->
                when (effect) {
                    SettingsUIEffect.NavigateToAppStore -> navigateToPlayStore(context)
                    SettingsUIEffect.NavigateToMail -> navigateToMail(context)
                    is SettingsUIEffect.ShowNotification -> showNotification(notificationManager, effect.message.asString(context))
                }
            }
        ) { state ->
            SettingsScreen(
                state = state,
                onEvent = viewModel::setEvent
            )
        }
    }
}

private fun showNotification(
    notificationManager: NotificationManager,
    message: String
) {
    val data = LiftingInAppNotificationData(
        message = message,
        type = LiftingNotificationType.Success,
        duration = 3000L
    )
    notificationManager.send(data)
}

private fun navigateToPlayStore(
    context: Context
) = Intent(Intent.ACTION_VIEW).apply {
    data = PLAY_STORE_URL.toUri()
}.also { intent ->
    context.startActivity(intent)
}


private fun navigateToMail(
    context: Context
) = Intent().apply {
    action = Intent.ACTION_SENDTO
    data = createPayload(context)
}.also {
    context.startActivity(it)
}


private fun createPayload(
    context: Context
): Uri {
    val packageManager = context.packageManager
    val packageName = context.packageName
    val versionName = try {
        packageManager.getPackageInfo(packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        "Unknown"
    }

    val versionCode = try {
        PackageInfoCompat.getLongVersionCode(packageManager.getPackageInfo(packageName, 0))
            .toString()
    } catch (e: Exception) {
        "Unknown"
    }

    val feedbackHint = context.getString(com.lifting.app.core.ui.R.string.feedback_hint)
    val subjectHint = context.getString(com.lifting.app.core.ui.R.string.subject_hint)

    val deviceInfo = """
        Device Info:
        Brand: ${Build.BRAND}
        Manufacturer: ${Build.MANUFACTURER}
        Model: ${Build.MODEL}
        Android Version: ${Build.VERSION.RELEASE}
        SDK Version: ${Build.VERSION.SDK_INT}

        Application Info:
        Package: $packageName
        Version: $versionName ($versionCode)

        Sent Time: ${LocalDateTime.now()}

        ------------------------

        $feedbackHint
    """.trimIndent()

    val subject = Uri.encode(subjectHint)
    val body = Uri.encode(deviceInfo)

    return "mailto:$MAIL_ADDRESS?subject=$subject&body=$body".toUri()
}


private const val PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=com.lifting.app"
private const val MAIL_ADDRESS = "liftingapp@gmail.com"
