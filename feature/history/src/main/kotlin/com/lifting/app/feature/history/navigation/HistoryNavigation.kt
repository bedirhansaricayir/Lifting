package com.lifting.app.feature.history.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.feature.history.HistoryScreen
import com.lifting.app.feature.history.HistoryViewModel

/**
 * Created by bedirhansaricayir on 08.03.2025
 */

fun NavGraphBuilder.historyScreen(

) {
    composable<LiftingScreen.History> {
        val viewModel: HistoryViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val effect = viewModel.effect

        LaunchedEffect(effect) {
            effect.collect { effect ->
                when (effect) {
                    else -> Unit
                }
            }
        }

        HistoryScreen(
            state = state,
            onEvent = viewModel::setEvent
        )
    }
}