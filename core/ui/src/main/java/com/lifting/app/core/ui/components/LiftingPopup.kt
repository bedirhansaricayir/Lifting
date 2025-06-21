package com.lifting.app.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.common.UiText

/**
 * Created by bedirhansaricayir on 11.06.2025
 */

@Composable
fun <T> LiftingPopup(
    items: List<T>,
    onClick: (T) -> Unit,
    itemDisplayText: (T) -> UiText,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = LiftingPopupDefaults.containerColor,
    contentColor: Color = LiftingPopupDefaults.contentColor,
    elevation: Dp = LiftingPopupDefaults.elevation,
    shape: Shape = LiftingPopupDefaults.shape,
    maxDropDownHeight: Dp = Dp.Unspecified,
    dropDownOffset: IntOffset = IntOffset.Zero,
    properties: PopupProperties = PopupProperties(focusable = true),
) {
    Popup(
        onDismissRequest = onDismiss,
        offset = dropDownOffset,
        properties = properties
    ) {
        Surface(
            modifier = modifier,
            color = containerColor,
            contentColor = contentColor,
            shape = shape,
            shadowElevation = elevation
        ) {
            Column(
                modifier = Modifier
                    .padding(6.dp)
                    .width(IntrinsicSize.Max),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items.forEach { item ->
                    LiftingPopupItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(LiftingTheme.colors.surface)
                            .clickable { onClick(item) }
                            .padding(8.dp),
                        text = itemDisplayText(item).asString()
                    )
                }
            }
        }
    }
}

@Composable
fun LiftingPopupItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
    )
}

object LiftingPopupDefaults {
    val elevation = 8.dp
    val containerColor
        @Composable get() = LiftingTheme.colors.surface
    val contentColor
        @Composable get() = LiftingTheme.colors.onBackground
    val shape
        @Composable get() = RoundedCornerShape(10.dp)
}