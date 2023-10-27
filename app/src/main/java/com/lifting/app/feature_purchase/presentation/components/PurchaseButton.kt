package com.lifting.app.feature_purchase.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.common.components.CommonProgressIndicatior

@Composable
fun PurchaseButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.background,
    shape: Dp = 25.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    enabled: Boolean = false,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(shape),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = backgroundColor,
            disabledContentColor = contentColor

        ),
        enabled = enabled
    ) {
        if (isLoading) {
            CommonProgressIndicatior(
                modifier = Modifier.size(20.dp),
            )
        } else {
            Text(
                text = stringResource(
                    R.string.label_purchase_button
                ),
                style = textStyle
            )
        }
    }
}