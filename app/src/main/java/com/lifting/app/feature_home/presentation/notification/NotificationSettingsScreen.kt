package com.lifting.app.feature_home.presentation.notification

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.feature_home.presentation.components.CommonTopBar

@Composable
fun NotificationSettingsScreen(onBackNavigationIconClicked: () -> Unit) {
    NotificationSettingsScreenContent(onBackNavigationIconClicked = onBackNavigationIconClicked)
}

@Composable
fun NotificationSettingsScreenContent(
    modifier: Modifier = Modifier,
    onBackNavigationIconClicked: () -> Unit,
) {
    var checked by remember { mutableStateOf(false) }

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
            state = checked,
            onStateChange = { checked = it })
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