package com.lifting.app.core.ui.common

import android.app.Activity
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

/**
 * Created by bedirhansaricayir on 11.06.2025
 */

@Composable
fun PreventKeyboardOnResume() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(Unit) {
        val window = (context as? Activity)?.window
        val originalMode = window?.attributes?.softInputMode

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
            } else if (event == Lifecycle.Event.ON_PAUSE) {
                window?.setSoftInputMode(
                    originalMode ?: WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                )
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            window?.setSoftInputMode(
                originalMode ?: WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
            )
        }
    }
}