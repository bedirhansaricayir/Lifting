package com.lifting.app.core.common.extensions

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
