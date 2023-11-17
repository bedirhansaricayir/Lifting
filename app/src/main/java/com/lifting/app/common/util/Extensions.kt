package com.lifting.app.common.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.lifting.app.feature_detail.domain.model.SelectedProgram
import com.lifting.app.feature_home.data.remote.model.DusukZorluk
import com.lifting.app.feature_home.data.remote.model.OrtaZorluk
import com.lifting.app.feature_home.data.remote.model.YuksekZorluk
import java.time.Instant
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Formatter
import java.util.Locale
import androidx.compose.foundation.background
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.lifting.app.common.constants.Constants
import com.lifting.app.common.constants.Constants.Companion.APP_NAME

fun Modifier.noRippleClickable(enabled: Boolean = true, onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick.invoke()
    }
}

fun Long.toTimeFormat(): String {
    val mFormatBuilder = StringBuilder()
    val mFormatter = Formatter(mFormatBuilder, Locale.getDefault())
    val totalSeconds = this / 1000
    val seconds = totalSeconds % 60
    val minutes = totalSeconds / 60 % 60
    val hours = totalSeconds / 3600
    mFormatBuilder.setLength(0)
    return if (hours > 0) {
        mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
    } else {
        mFormatter.format("%02d:%02d", minutes, seconds).toString()
    }
}

fun LocalDate.toLong(): Long {
    return this.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
}

fun Long.toLocalDate(): LocalDate {
    val instant = Instant.ofEpochMilli(this)
    return instant.atZone(ZoneOffset.UTC).toLocalDate()
}

fun LocalDate.toLocaleFormat(pattern: String = "dd MMMM yyyy"): String {
    return this.format(DateTimeFormatter.ofPattern(pattern, Locale.getDefault()))
}

fun String.toLocalDate(pattern: String = "dd MMMM yyyy"): LocalDate? {
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    return try {
        LocalDate.parse(this, formatter)
    } catch (e: Exception) {
        null
    }
}

fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, Locale.getDefault())
}

fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.month.displayText(short = short)} ${this.year}"
}

fun DusukZorluk.toSelectedProgram(): SelectedProgram {
    return SelectedProgram(
        programName = this.programAdi ?: "",
        programDay = this.gunSayisi ?: 0,
        program = this.uygulanis
    )
}

fun OrtaZorluk.toSelectedProgram(): SelectedProgram {
    return SelectedProgram(
        programName = this.programAdi ?: "",
        programDay = this.gunSayisi ?: 0,
        program = this.uygulanis
    )
}

fun YuksekZorluk.toSelectedProgram(): SelectedProgram {
    return SelectedProgram(
        programName = this.programAdi ?: "",
        programDay = this.gunSayisi ?: 0,
        program = this.uygulanis
    )
}

fun Context.sendMail() {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:${Constants.EMAIL_APPLICATION}")
        putExtra(Intent.EXTRA_SUBJECT, APP_NAME)
    }
    startActivity(Intent.createChooser(intent, "Lifting Feedback"))
}

fun Modifier.shimmerLoadingAnimation(
    isLoadingCompleted: Boolean = false,
    isLightModeActive: Boolean = true,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
): Modifier {
    if (isLoadingCompleted) {
        return this
    } else {
        return composed {

            val shimmerColors = ShimmerAnimationData(isLightMode = isLightModeActive).getColours()

            val transition = rememberInfiniteTransition(label = "")

            val translateAnimation = transition.animateFloat(
                initialValue = 0f,
                targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = durationMillis,
                        easing = LinearEasing,
                    ),
                    repeatMode = RepeatMode.Restart,
                ),
                label = "Shimmer loading animation",
            )

            this.background(
                brush = Brush.linearGradient(
                    colors = shimmerColors,
                    start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                    end = Offset(x = translateAnimation.value, y = angleOfAxisY),
                ),
            )
        }
    }
}

data class ShimmerAnimationData(
    private val isLightMode: Boolean
) {
    fun getColours(): List<Color> {
        return if (isLightMode) {
            val color = Color.White

            listOf(
                color.copy(alpha = 0.3f),
                color.copy(alpha = 0.5f),
                color.copy(alpha = 1.0f),
                color.copy(alpha = 0.5f),
                color.copy(alpha = 0.3f),
            )
        } else {
            val color = Color.Black

            listOf(
                color.copy(alpha = 0.0f),
                color.copy(alpha = 0.3f),
                color.copy(alpha = 0.5f),
                color.copy(alpha = 0.3f),
                color.copy(alpha = 0.0f),
            )
        }
    }
}