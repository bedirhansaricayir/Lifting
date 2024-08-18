package com.lifting.app.core.ui.extensions

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils

/**
 * Created by bedirhansaricayir on 18.08.2024
 */

fun Color.toLegacyInt(): Int {
    return android.graphics.Color.argb(
        (alpha * 255.0f + 0.5f).toInt(),
        (red * 255.0f + 0.5f).toInt(),
        (green * 255.0f + 0.5f).toInt(),
        (blue * 255.0f + 0.5f).toInt()
    )
}

fun Color.lighterColor(ratio: Float = 0.5f): Color {
    return Color(ColorUtils.blendARGB(this.toLegacyInt(), android.graphics.Color.BLACK, ratio))
}
