package com.lifting.app.core.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme

/**
 * Created by bedirhansaricayir on 05.02.2025
 */

sealed interface LiftingButtonType {
    data class TextButton(val text: String) : LiftingButtonType
    data class PrimaryButton(val text: String, val leadingIcon: ImageVector? = null) :
        LiftingButtonType

    data class IconButton(
        val icon: ImageVector,
        val tint: Color,
        val enabled: Boolean = true,
        val contentDescription: String = String.EMPTY
    ) : LiftingButtonType

    data class ExtendedButton(
        val content: @Composable RowScope.() -> Unit,
    ) : LiftingButtonType
}

@Composable
fun LiftingButton(
    buttonType: LiftingButtonType,
    modifier: Modifier = Modifier,
    colors: ButtonColors = LiftingButtonDefaults.buttonColors(buttonType),
    style: TextStyle = LiftingTheme.typography.button,
    onClick: () -> Unit = { },
) {
    when (buttonType) {
        is LiftingButtonType.TextButton ->
            LiftingTextButton(
                text = buttonType.text,
                modifier = modifier,
                colors = colors,
                style = style,
                onClick = onClick,
            )

        is LiftingButtonType.PrimaryButton ->
            LiftingPrimaryButton(
                text = buttonType.text,
                modifier = modifier,
                leadingIcon = buttonType.leadingIcon,
                colors = colors,
                style = style,
                onClick = onClick,
            )

        is LiftingButtonType.IconButton ->
            LiftingIconButton(
                icon = buttonType.icon,
                tint = buttonType.tint,
                modifier = modifier,
                colors = LiftingButtonDefaults.iconButtonColors(),
                contentDescription = buttonType.contentDescription,
                enabled = buttonType.enabled,
                onClick = onClick,
            )

        is LiftingButtonType.ExtendedButton ->
            LiftingExtendedButton(
                modifier = modifier,
                onClick = onClick,
                content = buttonType.content
            )

    }
}

@Composable
private fun LiftingTextButton(
    text: String,
    modifier: Modifier = Modifier,
    colors: ButtonColors = LiftingButtonDefaults.textButtonColors(),
    style: TextStyle = LiftingTheme.typography.button,
    onClick: () -> Unit = { },
) {
    TextButton(
        modifier = modifier,
        colors = colors,
        onClick = onClick,
    ) {
        Text(
            text = text,
            style = style
        )
    }
}

@Composable
private fun LiftingPrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    colors: ButtonColors = LiftingButtonDefaults.primaryButtonColors(),
    style: TextStyle = LiftingTheme.typography.button,
    onClick: () -> Unit = { },
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = colors
    ) {
        leadingIcon?.let { imageVector ->
            Icon(
                imageVector = imageVector,
                contentDescription = String.EMPTY,
                modifier = Modifier.padding(end = LiftingTheme.dimensions.small),
            )
        }
        Text(
            text = text,
            style = style
        )
    }
}

@Composable
private fun LiftingIconButton(
    icon: ImageVector,
    tint: Color,
    modifier: Modifier = Modifier,
    colors: IconButtonColors = LiftingButtonDefaults.iconButtonColors(),
    contentDescription: String = String.EMPTY,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val alpha by animateFloatAsState(targetValue = if (enabled) 1f else 0.5f, label = "")

    IconButton(
        onClick = onClick,
        enabled = enabled,
        colors = colors,
        modifier = modifier.alpha(alpha)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Composable
private fun LiftingExtendedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = FloatingActionButtonDefaults.extendedFabShape,
    containerColor: Color = LiftingTheme.colors.primary,
    contentColor: Color = LiftingTheme.colors.onPrimary,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(
        defaultElevation = 2.dp
    ),
    content: @Composable (RowScope.() -> Unit),
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = elevation,
        onClick = onClick
    ) { content.invoke(this) }
}

object LiftingButtonDefaults {

    @Composable
    fun buttonColors(buttonType: LiftingButtonType) =
        when (buttonType) {
            is LiftingButtonType.TextButton ->
                textButtonColors()

            is LiftingButtonType.PrimaryButton ->
                primaryButtonColors()

            else -> textButtonColors()
        }


    @Composable
    fun textButtonColors(
        contentColor: Color = LiftingTheme.colors.primary
    ) = ButtonDefaults.textButtonColors(
        contentColor = contentColor
    )

    @Composable
    fun primaryButtonColors(
        containerColor: Color = LiftingTheme.colors.primary,
        contentColor: Color = LiftingTheme.colors.onPrimary
    ) = ButtonDefaults.buttonColors(
        containerColor = containerColor,
        contentColor = contentColor
    )

    @Composable
    fun iconButtonColors() = IconButtonDefaults.iconButtonColors()
}