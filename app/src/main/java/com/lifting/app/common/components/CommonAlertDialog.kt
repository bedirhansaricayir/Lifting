package com.lifting.app.common.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.lifting.app.R

@Composable
fun CommonAlertDialog(
    dialogState: Boolean,
    title: Int,
    body: Int,
    confirmButtonTitle: Int,
    confirmButtonColor: Color = MaterialTheme.colorScheme.primary,
    onDissmiss: () -> Unit,
    onCancelClicked: () -> Unit,
    onConfirmClicked: () -> Unit
) {
    if (dialogState) {
        AlertDialog(
            onDismissRequest = { onDissmiss.invoke() },
            title = {
                Text(
                    modifier = Modifier,
                    text = stringResource(id = title),
                    color = Color.LightGray,
                    style = MaterialTheme.typography.titleSmall
                )
            },
            text = {
                Text(
                    modifier = Modifier,
                    text = stringResource(id = body),
                    color = Color.LightGray,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            dismissButton = {
                TextButton(onClick = {
                    onCancelClicked.invoke()
                    onDissmiss.invoke()
                }) {
                    Text(text = stringResource(id = R.string.date_already_exist_error_cancel_label))
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmClicked.invoke()
                        onDissmiss.invoke()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = confirmButtonColor
                    )
                ) {
                    Text(text = stringResource(id = confirmButtonTitle))
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
        )
    }

}