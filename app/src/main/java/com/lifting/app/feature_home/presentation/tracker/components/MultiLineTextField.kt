package com.lifting.app.feature_home.presentation.tracker.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.lifting.app.R

@Composable
fun MultiLineTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    hintText: Int = R.string.add_comment_label,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall,
    maxLines: Int = Int.MAX_VALUE
) {
    Box(modifier = modifier.padding(8.dp)) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            value = value,
            onValueChange = onValueChanged,
            textStyle = textStyle,
            maxLines = maxLines,
            placeholder = {
                Box(modifier = modifier) {
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(id = hintText),
                            color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                        )
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                disabledTextColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                disabledContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.White

            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        )
    }
}


