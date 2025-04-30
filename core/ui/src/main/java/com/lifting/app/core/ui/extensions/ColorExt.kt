package com.lifting.app.core.ui.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.core.graphics.ColorUtils
import kotlin.random.Random

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

fun Color.lighterColor(ratio: Float = 0.3f): Color {
    return Color(ColorUtils.blendARGB(this.toLegacyInt(), android.graphics.Color.BLACK, ratio))
}

fun Color.darkerColor(ratio: Float = 0.5f): Color {
    return Color(ColorUtils.blendARGB(this.toLegacyInt(), android.graphics.Color.BLACK, ratio))
}

fun Color.lighterOrDarkerColor(ratio: Float = 0.5f) =
    if (isDark()) lighterColor(ratio) else darkerColor(ratio)

fun Color.Companion.randomColorById(id: Int): Color = randomSuperSetColor("superset_$id")

private fun randomSuperSetColor(id: String): Color {
    val rand = Random(id.hashCode())
    val red: Int = rand.nextInt(256)
    val green: Int = rand.nextInt(256)
    val blue: Int = rand.nextInt(256)
    return Color(red, green, blue)
}

fun Color.isDark() = luminance() < 0.5f
