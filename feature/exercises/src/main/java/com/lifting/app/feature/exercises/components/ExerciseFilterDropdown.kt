package com.lifting.app.feature.exercises.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.Selectable
import com.lifting.app.core.ui.common.UiText
import com.lifting.app.core.ui.components.LiftingChip
import com.lifting.app.core.ui.components.LiftingSelectableDropdown
import com.lifting.app.core.ui.extensions.alphaClickable

/**
 * Created by bedirhansaricayir on 16.06.2025
 */

@Composable
internal fun <T> ExerciseFilterDropdown(
    selectedText: String,
    isDropdownVisible: Boolean,
    selectableItems: List<Selectable<T>>,
    itemDisplayText: (T) -> UiText,
    onItemSelected: (Selectable<T>) -> Unit,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val dropDownMaxHeight = (configuration.screenHeightDp * 0.3f).dp

    var dropDownOffset by remember {
        mutableStateOf(IntOffset.Zero)
    }

    val iconRotate by animateFloatAsState(
        targetValue = if (isDropdownVisible) 180f else 0f,
        label = "Icon Rotation"
    )

    val isAllType =
        selectedText == stringResource(id = com.lifting.app.core.ui.R.string.all_equipment_title) ||
                selectedText == stringResource(id = com.lifting.app.core.ui.R.string.all_category_title)

    LiftingChip(
        modifier = modifier
            .onGloballyPositioned {
                dropDownOffset = IntOffset(
                    x = 0,
                    y = it.size.height
                )
            },
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .height(32.dp)
                .padding(LiftingTheme.dimensions.small),
            verticalAlignment = Alignment.CenterVertically
        ) {

            BasicText(
                modifier = Modifier.weight(1f),
                text = selectedText,
                maxLines = 1,
                overflow = TextOverflow.MiddleEllipsis,
                style = LiftingTheme.typography.caption.copy(color = LiftingTheme.colors.onPrimary),
                autoSize = TextAutoSize.StepBased(minFontSize = 10.sp, maxFontSize = 12.sp)
            )

            if (!isAllType) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .alphaClickable { onClear() },
                    imageVector = LiftingTheme.icons.close,
                    contentDescription = String.EMPTY
                )
            } else {
                Icon(
                    modifier = Modifier.rotate(iconRotate),
                    imageVector = LiftingTheme.icons.chevronDown,
                    contentDescription = String.EMPTY
                )
            }


        }


        if (isDropdownVisible) {
            LiftingSelectableDropdown(
                items = selectableItems,
                itemDisplayText = { item ->
                    itemDisplayText(item)
                },
                onDismiss = onDismiss,
                key = { it.toString() },
                onItemClick = { selectedItem ->
                    onItemSelected(selectedItem)
                },
                dropDownOffset = dropDownOffset,
                maxDropDownHeight = dropDownMaxHeight
            )
        }
    }
}