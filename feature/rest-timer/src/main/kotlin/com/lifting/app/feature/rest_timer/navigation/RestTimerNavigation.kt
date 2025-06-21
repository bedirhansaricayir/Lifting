package com.lifting.app.feature.rest_timer.navigation

import androidx.compose.material.navigation.bottomSheet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.ui.BaseComposableLayout
import com.lifting.app.feature.rest_timer.RestTimerScreen
import com.lifting.app.feature.rest_timer.RestTimerViewModel

/**
 * Created by bedirhansaricayir on 11.06.2025
 */

fun NavController.navigateToRestTimer() = navigate(LiftingScreen.RestTimerBottomSheet().route)

fun NavGraphBuilder.restTimerBottomSheetScreen() {
    bottomSheet(LiftingScreen.RestTimerBottomSheet().route) {
        val viewModel: RestTimerViewModel = hiltViewModel()

        BaseComposableLayout(
            viewModel = viewModel,
            effectHandler = { context, effect ->

            }
        ) { state ->
            RestTimerScreen(
                state = state,
                onEvent = viewModel::setEvent
            )
        }
    }
} 