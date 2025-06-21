package com.lifting.app.feature.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.Selectable
import com.lifting.app.core.ui.common.UiText
import com.lifting.app.core.ui.components.LiftingCard
import com.lifting.app.core.ui.components.LiftingSelectableDropdown

/**
 * Created by bedirhansaricayir on 16.05.2025
 */

@Composable
internal fun <T> ExpandableSettingsItem(
    title: String,
    icon: ImageVector,
    popupItems: List<Selectable<T>>,
    popupItemDisplayText: (T) -> UiText,
    popupItemKey: (T) -> Any,
    popupItemClick: (Selectable<T>) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = LiftingTheme.shapes.medium,
    popupItemLeadingIcon: @Composable ((T) -> Unit)? = null,
    description: String = String.EMPTY,
) {
    val configuration = LocalConfiguration.current
    val dropDownMaxHeight = (configuration.screenHeightDp * 0.3f).dp
    var dropDownOffset by remember { mutableStateOf(IntOffset.Zero) }
    var expanded by remember { mutableStateOf(false) }

    LiftingCard(
        modifier = modifier
            .onGloballyPositioned {
                dropDownOffset = IntOffset(
                    x = it.size.width,
                    y = (it.size.height / 2)
                )
            },
        shape = shape,
        onClick = { expanded = !expanded }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(end = 24.dp),
                imageVector = icon,
                contentDescription = String.EMPTY,
                tint = LiftingTheme.colors.onBackground,
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = LiftingTheme.typography.subtitle1,
                    color = LiftingTheme.colors.onBackground
                )
                if (description.isNotEmpty()) {
                    Text(
                        text = description,
                        style = LiftingTheme.typography.caption,
                        color = LiftingTheme.colors.onBackground.copy(0.75f),
                    )
                }
            }
        }

        if (expanded) {
            LiftingSelectableDropdown(
                items = popupItems,
                itemDisplayText = popupItemDisplayText,
                onDismiss = { expanded = false },
                key = popupItemKey,
                onItemClick = {
                    popupItemClick(it)
                    expanded = false
                },
                dropDownOffset = dropDownOffset,
                maxDropDownHeight = dropDownMaxHeight,
                leadingIcon = popupItemLeadingIcon
            )
        }
    }
}

@Composable
internal fun SettingsItem(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    shape: Shape = LiftingTheme.shapes.medium,
    description: String = String.EMPTY,
    onClick: () -> Unit
) {
    LiftingCard(
        modifier = modifier,
        shape = shape,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(end = 24.dp),
                imageVector = icon,
                contentDescription = text,
                tint = LiftingTheme.colors.onBackground,
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = text,
                    style = LiftingTheme.typography.subtitle1,
                    color = LiftingTheme.colors.onBackground
                )
                if (description.isNotEmpty()) {
                    Text(
                        text = description,
                        style = LiftingTheme.typography.caption,
                        color = LiftingTheme.colors.onBackground.copy(0.75f),
                    )
                }
            }
        }
    }
}

@Composable
internal fun SettingsSectionHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = LiftingTheme.typography.caption,
        color = LiftingTheme.colors.onBackground.copy(0.75f)
    )
}

@Composable
internal fun SettingsItemDivider(
    modifier: Modifier = Modifier,
    color: Color = LiftingTheme.colors.surface.copy(0.75f),
    thickness: Dp = 0.5.dp
) {
    HorizontalDivider(
        modifier = modifier,
        color = color,
        thickness = thickness
    )
}