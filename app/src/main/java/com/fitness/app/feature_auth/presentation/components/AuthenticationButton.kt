package com.fitness.app.feature_auth.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fitness.app.R
import com.fitness.app.feature_auth.data.model.AuthenticationMode
import com.fitness.app.ui.theme.Purple40
import com.fitness.app.ui.theme.White40

@Composable
fun AuthenticationButton(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    backgroundColor: Color,
    contentColor: Color,
    shape: Dp = 25.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    enabled: Boolean = false,
    isLoading: Boolean = false,
    onButtonClick: () -> Unit
) {

    Button(
        modifier = modifier,
        onClick = { onButtonClick() },
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
            CircularProgressIndicator(
                color = contentColor,
                modifier = Modifier.size(20.dp)
            )
        } else {
            Text(
                text = stringResource(
                    if (authenticationMode ==
                        AuthenticationMode.SIGN_IN
                    ) {
                        R.string.action_sign_in
                    } else {
                        R.string.action_sign_up
                    }
                ),
                style = textStyle
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthenticationButtonPreview() {
    AuthenticationButton(authenticationMode = AuthenticationMode.SIGN_IN, backgroundColor = Purple40, contentColor = White40, isLoading = false, enabled = true, modifier = Modifier.fillMaxWidth()) {

    }
}