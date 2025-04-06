package com.lifting.app.core.common.extensions

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by bedirhansaricayir on 22.02.2025
 */

fun NavController.observeRouteArgument(
    key: String,
    initialValue: String? = null
): StateFlow<String?>? {
    return this.currentBackStackEntry?.savedStateHandle?.getStateFlow(
        key = key,
        initialValue = initialValue
    )
}

fun NavController.clearRouteArgument(
    key: String,
) {
    this.currentBackStackEntry?.savedStateHandle?.set(
        key = key,
        value = null
    )
}


fun <T> NavBackStackEntry.observeRouteArgument(
    key: String,
    initialValue: T
): StateFlow<T?> {
    return this.savedStateHandle.getStateFlow(key, initialValue)
}

fun <T> NavBackStackEntry.clearRouteArgument(
    key: String
) {
    this.savedStateHandle.remove<T>(key)
}