package com.lifting.app.core.ui.top_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.ui.R

/**
 * Created by bedirhansaricayir on 04.08.2024
 */

@Composable
fun LiftingTopSearchBar(
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(id = R.string.search_bar_place_holder),
    value: String,
    onValueChange: (String) -> Unit,
    onBackClick: () -> Unit = {},
    leadingIconModifier: Modifier = Modifier
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val density = LocalDensity.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LiftingTheme.colors.background)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = LiftingTheme.icons.back,
                contentDescription = stringResource(id = R.string.back_button_content_description)
            )
        }

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
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
                )
            },
            leadingIcon = {
                Icon(
                    modifier = leadingIconModifier,
                    imageVector = LiftingTheme.icons.search,
                    contentDescription = stringResource(id = R.string.search_bar_leading_icon_search_content_description),
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = value.isNotEmpty(),
                    enter = slideInHorizontally(
                        animationSpec = tween(durationMillis = DURATION_ENTER)
                    ) {
                        with(density) { 40.dp.roundToPx() }
                    } + expandHorizontally(
                        animationSpec = tween(durationMillis = DURATION_ENTER),
                        expandFrom = Alignment.End
                    ) + fadeIn(
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutHorizontally {
                        with(density) { 40.dp.roundToPx() }
                    } + shrinkHorizontally() + fadeOut()
                ) {
                    IconButton(onClick = { onValueChange("") }
                    ) {
                        Icon(
                            imageVector = LiftingTheme.icons.clear,
                            contentDescription = stringResource(id = R.string.search_bar_trailing_icon_clear_content_description)
                        )
                    }
                }
            },
            shape = CircleShape,
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = LiftingTheme.colors.background,
                unfocusedContainerColor = LiftingTheme.colors.background,
                cursorColor = LiftingTheme.colors.onBackground,
                focusedIndicatorColor = LiftingTheme.colors.onBackground
            )
        )
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboard?.show()
    }
}

const val DURATION_ENTER = 300