package com.lifting.app.feature_home.presentation.tracker.components.custom_fab

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
interface FabIcon {
    @Stable
    val iconRes: Int
    @Stable
    val iconRotate: Float?
}

private class FabIconImpl(
    override val iconRes: Int,
    override val iconRotate: Float?
) : FabIcon

fun FabIcon(@DrawableRes iconRes: Int, iconRotate: Float? = null): FabIcon =
    FabIconImpl(iconRes, iconRotate)