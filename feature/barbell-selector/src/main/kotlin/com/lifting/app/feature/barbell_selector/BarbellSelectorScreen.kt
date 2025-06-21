package com.lifting.app.feature.barbell_selector

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifting.app.core.common.extensions.toReadableStringWithTwoDecimals
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.Barbell
import com.lifting.app.core.model.WeightUnit
import com.lifting.app.core.ui.extensions.toLocalizedBarbellName
import com.lifting.app.core.ui.extensions.toLocalizedString

/**
 * Created by bedirhansaricayir on 29.05.2025
 */
@Composable
internal fun BarbellSelectorScreen(
    state: BarbellSelectorUIState,
    onEvent: (BarbellSelectorUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    BarbellSelectorScreenContent(
        state = state,
        onEvent = onEvent,
        modifier = modifier
    )
}

@Composable
private fun BarbellSelectorScreenContent(
    state: BarbellSelectorUIState,
    onEvent: (BarbellSelectorUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(LiftingTheme.colors.background)
    ) {
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                text = stringResource(com.lifting.app.core.ui.R.string.select_barbell),
                textAlign = TextAlign.Center,
                style = LiftingTheme.typography.header2,
                color = LiftingTheme.colors.onBackground
            )
        }
        itemsIndexed(
            state.barbells,
            key = { _: Int, barbell: Barbell -> barbell.id }
        ) { index, barbell ->
            BarbellSelectorListItem(
                barbell = barbell,
                isSelected = barbell.id == state.barbellId,
                onClick = { onEvent(BarbellSelectorUIEvent.OnBarbellClick(barbell)) }
            )
        }
    }
}

@Composable
private fun BarbellSelectorListItem(
    barbell: Barbell,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = onClick)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.extraSmall)
            ) {
                Text(
                    text = stringResource(barbell.name.toLocalizedBarbellName()),
                    color = LiftingTheme.colors.onBackground,
                    style = LiftingTheme.typography.subtitle1
                )

                Text(
                    text = "${
                        barbell.weightKg?.toReadableStringWithTwoDecimals()
                    } ${stringResource(WeightUnit.Kg.toLocalizedString())} / ${
                        barbell.weightLbs?.toReadableStringWithTwoDecimals()
                    } ${stringResource(WeightUnit.Lbs.toLocalizedString())}",
                    style = LiftingTheme.typography.caption,
                    color = LiftingTheme.colors.onBackground.copy(0.75f)
                )
            }

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .background(
                            color = LiftingTheme.colors.primary,
                            shape = CircleShape
                        )
                        .padding(LiftingTheme.dimensions.small),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = com.lifting.app.core.ui.R.string.selected_barbell),
                        style = LiftingTheme.typography.button.copy(fontSize = 12.sp),
                        color = LiftingTheme.colors.onPrimary
                    )
                }
            }
        }

    }
}