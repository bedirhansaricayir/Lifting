package com.lifting.app.core.common.extensions

/**
 * Created by bedirhansaricayir on 22.02.2025
 */

val String.Companion.EMPTY: String
    get() = ""

fun String.orEmpty() = this.ifBlank { "" }

fun String.getIntValues(key: String = ",") = this.trim().split(key).map { it.trim().toInt() }