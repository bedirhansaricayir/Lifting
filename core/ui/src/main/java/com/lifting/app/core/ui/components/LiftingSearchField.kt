@file:OptIn(ExperimentalLayoutApi::class)

package com.lifting.app.core.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.lifting.app.core.common.extensions.doWhenHasNextOrPrevious
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
    extraTrailingIcon: @Composable (() -> Unit)? = null,
    useAnimatedPlaceholder: Boolean = false,
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val keyboardVisible = WindowInsets.isImeVisible

    LaunchedEffect(keyboardVisible) {
        if (keyboardVisible.not()) {
            focusManager.clearFocus()
        }
    }

    BasicTextField(
        modifier = modifier
            .background(
                LiftingTheme.colors.surface,
                LiftingTheme.shapes.large
            )
            .padding(LiftingTheme.dimensions.extraSmall)
            .height(32.dp),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        maxLines = 1,
        textStyle = LiftingTheme.typography.caption.copy(color = LiftingTheme.colors.onBackground),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboard?.hide()
            }
        ),
        cursorBrush = SolidColor(LiftingTheme.colors.onBackground),
        decorationBox = { innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(horizontal = LiftingTheme.dimensions.small),
                    imageVector = LiftingTheme.icons.search,
                    contentDescription = stringResource(id = R.string.search_bar_leading_icon_search_content_description),
                    tint = LiftingTheme.colors.onBackground.copy(0.75f)
                )

                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = placeholder,
                                color = LiftingTheme.colors.onBackground.copy(0.75f),
                            )

                            if (useAnimatedPlaceholder) {
                                AnimatedSearchPlaceholder(
                                    hints = listOf("Back Squat", "Deadlift", "Bench Press")
                                )
                            }

                        }
                    }
                    innerTextField()
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    LiftingAnimationContainer(
                        visible = value.isNotEmpty(),
                        content = {
                            LiftingButton(
                                buttonType = LiftingButtonType.IconButton(
                                    icon = LiftingTheme.icons.clear,
                                    tint = LiftingTheme.colors.onBackground
                                ),
                                onClick = { onValueChange("") }
                            )
                        }
                    )

                    extraTrailingIcon?.invoke()
                }
            }
        }
    )
}

@Composable
private fun AnimatedSearchPlaceholder(
    hints: List<String>,
    modifier: Modifier = Modifier
) {
    val iterator = hints.listIterator()

    val target by produceState(initialValue = hints.first()) {
        iterator.doWhenHasNextOrPrevious {
            value = it
        }
    }

    AnimatedContent(
        targetState = target,
        transitionSpec = { ScrollAnimation() },
        label = "Animated Placeholder"
    ) { exercise ->
        Text(
            modifier = modifier,
            text = " ${stringResource(R.string.e_g_string, exercise)}",
            color = LiftingTheme.colors.onBackground.copy(0.5f),
            style = LiftingTheme.typography.caption
        )
    }
}

private object ScrollAnimation {
    operator fun invoke(): ContentTransform {
        return slideInVertically(
            initialOffsetY = { 50 },
            animationSpec = tween()
        ) + fadeIn() with slideOutVertically(
            targetOffsetY = { -50 },
            animationSpec = tween()
        ) + fadeOut()
    }
}