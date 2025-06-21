package com.lifting.app.feature.exercise_detail.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.Selectable
import com.lifting.app.core.ui.components.LiftingChip
import com.lifting.app.core.ui.components.LiftingSelectableDropdown
import com.lifting.app.feature.exercise_detail.ChartPeriod

/**
 * Created by bedirhansaricayir on 04.06.2025
 */

@Composable
internal fun ChartFilterDropdown(
    selectedText: String,
    isDropdownVisible: Boolean,
    periods: List<Selectable<ChartPeriod>>,
    onItemSelected: (ChartPeriod) -> Unit,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val dropDownMaxHeight = (configuration.screenHeightDp * 0.3f).dp

    var dropDownOffset by remember {
        mutableStateOf(IntOffset.Zero)
    }

    val iconRotate by animateFloatAsState(
        targetValue = if (isDropdownVisible) 180f else 0f,
        label = "Icon Rotation"
    )

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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedText,
                style = LiftingTheme.typography.caption,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.width(LiftingTheme.dimensions.extraSmall))
            Icon(
                modifier = Modifier.rotate(iconRotate),
                imageVector = LiftingTheme.icons.chevronDown,
                contentDescription = String.EMPTY
            )
        }


        if (isDropdownVisible) {
            LiftingSelectableDropdown(
                items = periods,
                itemDisplayText = { period ->
                    period.title
                },
                onDismiss = onDismiss,
                key = { it.title.asString(context) },
                onItemClick = { selectedPeriod ->
                    onItemSelected(selectedPeriod.item)
                },
                dropDownOffset = dropDownOffset,
                maxDropDownHeight = dropDownMaxHeight
            )
        }
    }
}