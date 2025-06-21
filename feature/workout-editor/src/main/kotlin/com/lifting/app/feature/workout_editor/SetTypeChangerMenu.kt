package com.lifting.app.feature.workout_editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.LogSetType

/**
 * Created by bedirhansaricayir on 09.02.2025
 */

@Composable
internal fun SetTypeChangerMenu(
    expanded: Boolean,
    selectedType: LogSetType,
    onDismissRequest: () -> Unit,
    onChangeSetType: (LogSetType) -> Unit,
) {
    val allTypes = LogSetType.entries

    DropdownMenu(
        expanded = expanded,
        containerColor = LiftingTheme.colors.surface,
        shape = LiftingTheme.shapes.medium,
        onDismissRequest = onDismissRequest,
    ) {
        allTypes.forEach { type ->
            with(type) {
                val bgColor = if (selectedType == this) {
                    LiftingTheme.colors.background.copy(0.75f)
                } else {
                    LiftingTheme.colors.surface
                }
                DropdownMenuItem(
                    modifier = Modifier.background(color = bgColor),
                    onClick = {
                        onChangeSetType(this)
                    },
                    leadingIcon = {
                        SetTypeIcon()
                    },
                    text = {
                        SetTypeName()
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = LiftingTheme.colors.onBackground
                    )
                )
            }
        }
    }
}

@Composable
private fun LogSetType.SetTypeIcon() {
    val props = getIconProps()
    Box(modifier = Modifier.width(28.dp), contentAlignment = Alignment.CenterStart) {
        Text(text = props.first, color = props.second)
    }
}

@Composable
private fun LogSetType.SetTypeName() {
    Text(text = readableName)
}

private fun LogSetType.getIconProps() = when (this) {
    LogSetType.NORMAL -> Pair("N", Color.Gray)
    LogSetType.WARM_UP -> Pair("W", Color.Yellow)
    LogSetType.DROP_SET -> Pair("D", Color.Magenta)
    LogSetType.FAILURE -> Pair("F", Color.Red)
}