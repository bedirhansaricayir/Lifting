package com.lifting.app.core.common.extensions

/**
 * Created by bedirhansaricayir on 22.02.2025
 */

val String.Companion.EMPTY: String
    get() = ""

fun String.orEmpty() = this.ifBlank { "" }
