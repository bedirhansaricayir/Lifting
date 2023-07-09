package com.lifting.app.feature_auth.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifting.app.R
import com.lifting.app.theme.White40
import com.lifting.app.theme.grey10
import com.lifting.app.theme.grey30

@Composable
fun TextEntryModule(
    text: String,
    hint: String,
    leadingIcon: ImageVector,
    textValue: String,
    keyboardType: KeyboardType = KeyboardType.Ascii,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    borderColor: Color = Color.Black,
    textColor: Color = White40,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    cursorColor: Color,
    onValueChanged: (String) -> Unit,
    trailingIcon: Int? = null,
    trailingIconColor: Color = MaterialTheme.colorScheme.primary,
    onTrailingIconClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    imeAction: ImeAction,
    isUsernameField: Boolean = false
) {
    val maxChar = 20
    Column(
        modifier = modifier
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp)
                .border(0.5.dp, borderColor, RoundedCornerShape(20.dp))
                .height(60.dp)
                .shadow(3.dp, RoundedCornerShape(20.dp)),
            value = textValue,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = cursorColor,
                focusedContainerColor = grey30,
                unfocusedContainerColor = grey30,
                disabledContainerColor = grey30,
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
            ),
            onValueChange = {
                if (isUsernameField) {
                    if (it.length <= maxChar) onValueChanged(it)
                } else onValueChanged(it)
            },
            shape = RoundedCornerShape(20.dp),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = cursorColor
                )
            },
            trailingIcon = {
                if (trailingIcon != null) {
                    Icon(
                        painter = painterResource(trailingIcon),
                        contentDescription = null,
                        tint = trailingIconColor,
                        modifier = Modifier.clickable {
                            if (onTrailingIconClick != null) onTrailingIconClick()
                        }
                    )
                }
                if (isUsernameField) {
                    Text(
                        modifier = Modifier.padding(end = 8.dp),
                        text = "${textValue.length} / $maxChar",
                        style = textStyle,
                        fontSize = 12.sp,
                        color = if (textValue.length == maxChar) MaterialTheme.colorScheme.primary else textColor
                    )
                }
            },
            placeholder = {
                Text(
                    text = hint,
                    style = textStyle,
                    color = grey10
                )
            },
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            visualTransformation = visualTransformation,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextEntryModulePreview() {
    TextEntryModule(
        text = "Email",
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 0.dp, 10.dp, 5.dp),
        hint = "bedirhansaricayir@gmail.com",
        leadingIcon = Icons.Default.Email,
        textValue = "",
        borderColor = Color.Black,
        cursorColor = MaterialTheme.colorScheme.primary,
        onValueChanged = {},
        imeAction = ImeAction.Default,
        trailingIcon = R.drawable.icon_visibility,
        onTrailingIconClick = {}
    )
}