package com.lifting.app.core.ui

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.base.viewmodel.Effect
import com.lifting.app.core.base.viewmodel.Event
import com.lifting.app.core.base.viewmodel.State
import com.lifting.app.core.designsystem.LiftingTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by bedirhansaricayir on 11.05.2025
 */

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <STATE : State, EVENT : Event, EFFECT : Effect> BaseComposableLayout(
    modifier: Modifier = Modifier,
    viewModel: BaseViewModel<STATE, EVENT, EFFECT>,
    onBackPressed: (() -> Unit)? = null,
    containerColor: Color = LiftingTheme.colors.background,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    effectHandler: (suspend CoroutineScope.(context: Context, effect: EFFECT) -> Unit)? = null,
    content: @Composable (BoxScope.(uiState: STATE) -> Unit)
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val context = LocalContext.current

    LaunchedEffect(key1 = lifecycle) {
        if (effectHandler != null) {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                withContext(Dispatchers.Main.immediate) {
                    viewModel.effect.collect { actionEvent ->
                        effectHandler(context, actionEvent)
                    }
                }

            }
        }
    }

    BackHandler(
        enabled = onBackPressed != null,
        onBack = { onBackPressed?.invoke() }
    )

    Scaffold(
        modifier = Modifier,
        containerColor = containerColor,
        contentWindowInsets = WindowInsets(0,0,0,0),
    ) { innerPadding ->
        Box(modifier = modifier
            .padding(innerPadding)
            .imePadding()
        ) {
            content(uiState)
        }
    }
}