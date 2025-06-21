package com.lifting.app.core.model

/**
 * Created by bedirhansaricayir on 04.06.2025
 */

data class Selectable<T>(
    val item: T,
    val selected: Boolean
)