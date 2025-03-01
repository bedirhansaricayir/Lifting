package com.lifting.app.feature.workout_edit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lifting.app.core.model.LogSetType

/**
 * Created by bedirhansaricayir on 09.02.2025
 */

@Composable
internal fun SetTypeChangerMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    selectedType: LogSetType,
    onDismissRequest: () -> Unit,
    onChangeSetType: (LogSetType) -> Unit,
) {
    val allTypes = LogSetType.values()

    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = onDismissRequest,
    ) {
        allTypes.forEach { type ->
            with(type) {
                val bgColor = if (selectedType == this) {
                    Color.White
                } else {
                    Color.Transparent
                }


                DropdownMenuItem(
                    modifier = Modifier.background(bgColor),
                    onClick = {
                        onChangeSetType(this)
                    },
                    leadingIcon = {
                        SetTypeIcon()
                    },
                    text = {
                        SetTypeName()
                    }
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