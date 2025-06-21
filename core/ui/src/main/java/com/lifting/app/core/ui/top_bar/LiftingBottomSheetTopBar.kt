package com.lifting.app.core.ui.top_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.R
import com.lifting.app.core.ui.components.LiftingButton
import com.lifting.app.core.ui.components.LiftingButtonType

/**
 * Created by bedirhansaricayir on 18.08.2024
 */

@Composable
fun LiftingBottomSheetTopBar(
    modifier: Modifier = Modifier,
    title: Int = R.string.bottom_sheet_title_new_exercise,
    isEnabledActionButton: Boolean,
    onNavigationClick: () -> Unit,
    onActionClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = LiftingTheme.colors.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        LiftingButton(
            buttonType = LiftingButtonType.IconButton(
                icon = LiftingTheme.icons.close,
                tint = LiftingTheme.colors.onBackground
            ),
            onClick = onNavigationClick
        )

        Text(
            text = stringResource(id = title),
            style = LiftingTheme.typography.header2,
            color = LiftingTheme.colors.onBackground
        )

        LiftingButton(
            buttonType = LiftingButtonType.IconButton(
                icon = LiftingTheme.icons.done,
                tint = LiftingTheme.colors.primary,
                enabled = isEnabledActionButton
            ),
            onClick = onActionClick,
        )
    }
}