package com.lifting.app.core.ui.components

/**
 * Created by bedirhansaricayir on 10.05.2025
 */

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.Selectable
import com.lifting.app.core.ui.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LiftingFilterDialog(
    items: List<Selectable<String>>,
    onDismissRequest: () -> Unit,
    onClearFilterClick: () -> Unit,
    onItemSelected: (Selectable<String>) -> Unit,
    @StringRes title: Int = R.string.filter_dialog_title,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LiftingTheme.dimensions.large),
            color = LiftingTheme.colors.surface,
            shape = LiftingTheme.shapes.large,
            tonalElevation = 6.dp,
        ) {
            Column {
                Text(
                    modifier = Modifier
                        .padding(LiftingTheme.dimensions.large),
                    text = stringResource(title),
                    style = LiftingTheme.typography.subtitle1,
                    color = LiftingTheme.colors.onBackground,
                )

                FlowRow(
                    modifier = Modifier
                        .animateContentSize()
                        .padding(horizontal = LiftingTheme.dimensions.large),
                    horizontalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.small),
                ) {
                    items.forEach { item ->
                        FilterChip(
                            selected = item.selected,
                            onClick = { onItemSelected(item) },
                            label = {
                                Text(
                                    text = item.item,
                                    style = LiftingTheme.typography.caption,
                                )
                            },
                            shape = RoundedCornerShape(50),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = LiftingTheme.colors.primary,
                                selectedLabelColor = LiftingTheme.colors.onPrimary,
                                selectedLeadingIconColor = LiftingTheme.colors.onPrimary,
                                labelColor = LiftingTheme.colors.onBackground
                            ),
                            border = if (item.selected.not()) BorderStroke(
                                width = 1.dp,
                                color = LiftingTheme.colors.onBackground
                            ) else null,
                            leadingIcon = {
                                if (item.selected) {
                                    Icon(
                                        imageVector = LiftingTheme.icons.check,
                                        contentDescription = String.EMPTY,
                                    )
                                }
                            }
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = LiftingTheme.dimensions.large),
                    color = LiftingTheme.colors.onBackground.copy(0.5f)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(LiftingTheme.dimensions.large),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (items.any { it.selected }) {
                        LiftingButton(
                            modifier = Modifier
                                .weight(1f)
                                .clip(CircleShape),
                            buttonType = LiftingButtonType.TextButton(text = stringResource(R.string.clear_filter_text)),
                            onClick = onClearFilterClick,
                        )
                    }

                    LiftingButton(
                        modifier = Modifier.weight(1f),
                        buttonType = LiftingButtonType.PrimaryButton(
                            text = stringResource(if (items.any { it.selected }) R.string.apply_filter_text else R.string.close_filter_text)
                        ),
                        onClick = onDismissRequest
                    )
                }
            }
        }
    }
}
