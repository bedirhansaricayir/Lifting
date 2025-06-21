package com.lifting.app.core.common.extensions

/**
 * Created by bedirhansaricayir on 22.02.2025
 */

val String.Companion.EMPTY: String
    get() = ""

fun String.orEmpty() = this.ifBlank { "" }

fun String.getBackStackArgs(separator: String = ",") = this.trim().split(separator).map { it.trim() }

fun String.replaceWithDot() = this.replace(',','.')

fun String.toMillisFromMMSS(): Long {
    val parts = this.split(":")
    val minutes = parts[0].toLongOrNull() ?: 0L
    val seconds = parts[1].toLongOrNull() ?: 0L
    return (minutes * 60 + seconds) * 1000
}
