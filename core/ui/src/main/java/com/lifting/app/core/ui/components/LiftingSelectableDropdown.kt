package com.lifting.app.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.Selectable
import com.lifting.app.core.ui.common.UiText

/**
 * Created by bedirhansaricayir on 04.06.2025
 */

@Composable
fun <T> LiftingSelectableDropdown(
    items: List<Selectable<T>>,
    itemDisplayText: (T) -> UiText,
    onDismiss: () -> Unit,
    key: (T) -> Any,
    onItemClick: (Selectable<T>) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable (T) -> Unit)? = null,
    dropDownOffset: IntOffset = IntOffset.Zero,
    maxDropDownHeight: Dp = Dp.Unspecified,
    properties: PopupProperties = PopupProperties(focusable = true)
) {
    Popup(
        onDismissRequest = onDismiss,
        offset = dropDownOffset,
        properties = properties
    ) {
        Surface(
            color = LiftingTheme.colors.surface,
            contentColor = LiftingTheme.colors.onBackground,
            shape = RoundedCornerShape(10.dp),
            shadowElevation = 4.dp,
            modifier = modifier
                .sizeIn(
                    maxWidth = 200.dp,
                    maxHeight = maxDropDownHeight,
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
        ) {
            LazyColumn(
                modifier = Modifier.padding(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(
                    items = items,
                    key = { key(it.item) }
                ) { selectable ->
                    Row(
                        modifier = Modifier
                            .height(IntrinsicSize.Max)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                color = if (selectable.selected) {
                                    LiftingTheme.colors.surface.copy(alpha = 0.05f)
                                } else LiftingTheme.colors.surface
                            )
                            .clickable {
                                onItemClick(selectable)
                            }
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        leadingIcon?.invoke(selectable.item)
                        Text(
                            modifier = Modifier.weight(1f),
                            text = itemDisplayText(selectable.item).asString(),
                        )
                        if (selectable.selected) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = LiftingTheme.icons.check,
                                contentDescription = String.EMPTY,
                                tint = LiftingTheme.colors.primary
                            )
                        }
                    }
                }
            }
        }
    }
}
