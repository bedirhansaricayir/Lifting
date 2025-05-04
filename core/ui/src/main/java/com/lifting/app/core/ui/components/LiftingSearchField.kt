@file:OptIn(ExperimentalLayoutApi::class)

package com.lifting.app.core.ui.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.R

/**
 * Created by bedirhansaricayir on 04.05.2025
 */

@Composable
fun LiftingSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(id = R.string.search_bar_place_holder),
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val keyboardVisible = WindowInsets.isImeVisible

    LaunchedEffect(keyboardVisible) {
        if (keyboardVisible.not()) {
            focusManager.clearFocus()
        }
    }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboard?.hide()
            }
        ),
        placeholder = {
            Text(
                text = placeholder,
                color = LiftingTheme.colors.onBackground.copy(0.75f)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = LiftingTheme.icons.search,
                contentDescription = stringResource(id = R.string.search_bar_leading_icon_search_content_description),
                tint = LiftingTheme.colors.onBackground
            )
        },
        trailingIcon = {
            HorizontalSlidingAnimationContainer(
                visible = value.isNotEmpty(),
                content = {
                    IconButton(onClick = { onValueChange("") }
                    ) {
                        Icon(
                            imageVector = LiftingTheme.icons.clear,
                            contentDescription = stringResource(id = R.string.search_bar_trailing_icon_clear_content_description),
                            tint = LiftingTheme.colors.onBackground
                        )
                    }
                }
            )
        },
        shape = CircleShape,
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = LiftingTheme.colors.background,
            unfocusedContainerColor = LiftingTheme.colors.background,
            cursorColor = LiftingTheme.colors.onBackground,
            focusedIndicatorColor = LiftingTheme.colors.onBackground,
            unfocusedIndicatorColor = LiftingTheme.colors.onBackground.copy(0.75f),
            focusedPlaceholderColor = LiftingTheme.colors.onBackground,
            unfocusedPlaceholderColor = LiftingTheme.colors.onBackground,
            focusedTextColor = LiftingTheme.colors.onBackground,
            unfocusedTextColor = LiftingTheme.colors.onBackground,
        )
    )
}