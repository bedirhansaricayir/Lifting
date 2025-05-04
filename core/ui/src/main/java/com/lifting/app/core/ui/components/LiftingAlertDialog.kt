package com.lifting.app.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lifting.app.core.designsystem.LiftingTheme

/**
 * Created by bedirhansaricayir on 01.05.2025
 */

@Composable
fun LiftingAlertDialog(
    @StringRes title: Int,
    @StringRes text: Int,
    @StringRes dismissText: Int,
    @StringRes confirmText: Int,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(title)
            )
        },
        text = {
            Text(
                text = stringResource(text)
            )
        },
        dismissButton = {
            LiftingTextButton(
                text = stringResource(dismissText),
                onClick = onDismiss
            )
        },
        confirmButton = {
            LiftingTextButton(
                text = stringResource(confirmText),
                onClick = onConfirm
            )
        },
        containerColor = LiftingTheme.colors.background,
        tonalElevation = LiftingTheme.dimensions.extraSmall,
        titleContentColor = LiftingTheme.colors.onBackground,
        textContentColor = LiftingTheme.colors.onBackground.copy(alpha = 0.75f),
    )
}