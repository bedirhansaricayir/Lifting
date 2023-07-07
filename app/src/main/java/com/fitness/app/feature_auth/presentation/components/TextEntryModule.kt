package com.fitness.app.feature_auth.presentation.components

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitness.app.R
import com.fitness.app.ui.theme.White40
import com.fitness.app.ui.theme.grey10
import com.fitness.app.ui.theme.grey30

@OptIn(ExperimentalMaterial3Api::class)
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
    modifier: Modifier = Modifier,
    isPasswordField: Boolean = false,
    imeAction: ImeAction
) {
    var isPasswordHidden by remember {
        mutableStateOf(true)
    }
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
            onValueChange = onValueChanged,
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
                if (isPasswordField) {
                    Icon(
                        painter = if(isPasswordHidden) painterResource(id = R.drawable.icon_visibility ) else painterResource(id = R.drawable.icon_visibility_off),
                        contentDescription = null,
                        tint = cursorColor,
                        modifier = Modifier.clickable {
                            isPasswordHidden = !isPasswordHidden
                        }
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
            visualTransformation = if (isPasswordField && isPasswordHidden) PasswordVisualTransformation() else if(!isPasswordHidden && isPasswordField) VisualTransformation.None else visualTransformation
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextEntryModulePreview(){
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
        imeAction = ImeAction.Default
    )
}