package com.lifting.app.core.ui.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
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

fun Color.Companion.randomColorById(id: Int): Color = randomSuperSetColor("superset_$id")

private fun randomSuperSetColor(id: String): Color {
    val rand = Random(id.hashCode())
    val red: Int = rand.nextInt(256)
    val green: Int = rand.nextInt(256)
    val blue: Int = rand.nextInt(256)
    return Color(red, green, blue)
}

fun Color.isDark() = luminance() < 0.5f

fun Color.darkerOrLighter(factor: Float = 1f) = if (isDark()) lighter(factor) else darker(factor)

fun Color.darker(factor: Float = 1f) =
    Color(ColorUtils.blendARGB(this.toArgb(), Color.Black.toArgb(), factor))

fun Color.lighter(factor: Float = 1f) =
    Color(ColorUtils.blendARGB(this.toArgb(), Color.White.toArgb(), factor))

fun String.toColor(): Color? =
    try {
        Color(
            android.graphics.Color.parseColor(
                if (this.startsWith("#"))
                    this
                else
                    "#$this"
            )
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
