package com.lifting.app.core.common.extensions

import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController

/**
 * Created by bedirhansaricayir on 04.05.2025
 */

fun<T> T.clearFocus(
    keyboard: SoftwareKeyboardController?,
    focusManager: FocusManager,
) {
    keyboard?.hide()
    focusManager.clearFocus()
}