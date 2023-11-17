package com.lifting.app.feature_notification.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.lifting.app.R
import com.lifting.app.feature_home.presentation.components.CommonTopBar
import com.lifting.app.feature_notification.domain.model.AlarmTime
import java.time.LocalTime

@Composable
fun NotificationSettingsScreen(
    state: NotificationSettingsState,
    onEvent: (NotificationSettingsEvent) -> Unit,
    onBackNavigationIconClicked: () -> Unit
) {
    NotificationSettingsScreenContent(
        state = state,
        onEvent = onEvent,
        onBackNavigationIconClicked = onBackNavigationIconClicked
    )
}

@Composable
fun NotificationSettingsScreenContent(
    state: NotificationSettingsState,
    modifier: Modifier = Modifier,
    onEvent: (NotificationSettingsEvent) -> Unit,
    onBackNavigationIconClicked: () -> Unit,
) {
    var showTimePicker by remember { mutableStateOf(false) }
    var showNotificationPermission by remember { mutableStateOf(false) }
    var pendingEventDataHolder by remember { mutableStateOf<AlarmTime?>(null) }

    LaunchedEffect(key1 = state.notificationPermissionIsGranted) {
        if (state.notificationPermissionIsGranted && pendingEventDataHolder != null) {
            onEvent(NotificationSettingsEvent.OnAlarmScheduled(pendingEventDataHolder!!))
            showTimePicker = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonTopBar(
            title = R.string.label_notification_screen_title,
            onBackNavigationIconClicked = onBackNavigationIconClicked
        )
        SwitchWithLabel(
            title = R.string.label_daily_reminder,
            state = state.notificationIsExist,
            onStateChange = {
                if (!state.notificationIsExist) {
                    showTimePicker = true
                }
                onEvent(NotificationSettingsEvent.OnNotificationSwitchStateChanged)
            }
        )
    }
    TimePickerDialog(
        visible = showTimePicker,
        onDismiss = {
            showTimePicker = false
            onEvent(NotificationSettingsEvent.OnNotificationSwitchStateChanged)
        },
        onConfirm = { alarmTime ->
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                onEvent(NotificationSettingsEvent.OnAlarmScheduled(alarmTime))
                showTimePicker = false
            }else if(state.notificationPermissionIsGranted) {
                onEvent(NotificationSettingsEvent.OnAlarmScheduled(alarmTime))
                showTimePicker = false
            } else {
                showNotificationPermission = true
                pendingEventDataHolder = alarmTime
            }
        }
    )
    if (showNotificationPermission) NotificationPermission {
        onEvent(NotificationSettingsEvent.OnPermissionAllowed)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    visible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (AlarmTime) -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = LocalTime.now().hour,
        initialMinute = LocalTime.now().minute
    )
    if (visible) {
        AlertDialog(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(size = 12.dp)
                ),
            onDismissRequest = onDismiss
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = Color.LightGray.copy(alpha = 0.3f)
                    )
                    .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(state = timePickerState)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.End),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(id = R.string.dismiss_label))
                    }
                    TextButton(
                        onClick = {
                            onConfirm(
                                AlarmTime(
                                    hour = timePickerState.hour,
                                    minute = timePickerState.minute
                                )
                            )
                        }
                    ) {
                        Text(text = stringResource(id = R.string.confirm_label))
                    }
                }
            }
        }
    }
}

@Composable
private fun SwitchWithLabel(
    @StringRes title: Int,
    state: Boolean,
    onStateChange: (Boolean) -> Unit
) {

    val icon: (@Composable () -> Unit) = {
        Icon(
            imageVector = if (state) Icons.Filled.Check else Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier.size(SwitchDefaults.IconSize),
        )
    }

    Row(
        modifier = Modifier
            .clickable(
                role = Role.Switch,
                onClick = {
                    onStateChange(!state)
                }
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = title), modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            style = MaterialTheme.typography.labelMedium
        )

        Switch(
            modifier = Modifier.semantics { contentDescription = "Notification Switcher" },
            checked = state,
            thumbContent = icon,
            onCheckedChange = {
                onStateChange(it)
            },
        )
    }
}

@Composable
fun NotificationPermission(
    onAllowed: () -> Unit
) {
    val context = LocalContext.current
    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else mutableStateOf(true)
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted
            if (isGranted) {
                onAllowed()
            }
        }
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        SideEffect {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

}
