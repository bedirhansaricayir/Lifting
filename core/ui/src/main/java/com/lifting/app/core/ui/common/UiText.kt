package com.lifting.app.core.ui.common

/**
 * Created by bedirhansaricayir on 04.06.2025
 */

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource

@Stable
sealed interface UiText {
    data class Dynamic(val value: String): UiText

    @Stable
    data class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ): UiText {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as StringResource

            if (id != other.id) return false
            if (!args.contentEquals(other.args)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + args.contentHashCode()
            return result
        }
    }

    @Stable
    data class Combined(
        val format: String,
        val uiTexts: Array<UiText>
    ): UiText {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Combined

            if (format != other.format) return false
            if (!uiTexts.contentEquals(other.uiTexts)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = format.hashCode()
            result = 31 * result + uiTexts.contentHashCode()
            return result
        }
    }

    @Composable
    fun asString(): String {
        return when(this) {
            is Dynamic -> value
            is StringResource -> stringResource(id, *args)
            is Combined -> {
                val strings = uiTexts.map { uiText ->
                    when(uiText) {
                        is Combined -> throw IllegalArgumentException("Can't nest combined UiTexts.")
                        is Dynamic -> uiText.value
                        is StringResource -> stringResource(uiText.id, *uiText.args)
                    }
                }
                String.format(format, *strings.toTypedArray())
            }
        }
    }

    fun asString(context: Context): String {
        return when(this) {
            is Dynamic -> value
            is StringResource -> context.getString(id, *args)
            is Combined -> {
                val strings = uiTexts.map { uiText ->
                    when(uiText) {
                        is Combined -> throw IllegalArgumentException("Can't nest combined UiTexts.")
                        is Dynamic -> uiText.value
                        is StringResource -> context.getString(uiText.id, *uiText.args)
                    }
                }
                String.format(format, *strings.toTypedArray())
            }
        }
    }
}