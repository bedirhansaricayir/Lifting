package com.lifting.app.feature.history.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.ui.BaseComposableLayout
import com.lifting.app.feature.history.HistoryScreen
import com.lifting.app.feature.history.HistoryUIEffect
import com.lifting.app.feature.history.HistoryViewModel

/**
 * Created by bedirhansaricayir on 08.03.2025
 */

fun NavGraphBuilder.historyScreen(
    navigateToCalendar: () -> Unit,
    navigateToWorkoutDetail: (workoutId: String) -> Unit
) {
    composable<LiftingScreen.History> { entry ->
        val viewModel: HistoryViewModel = hiltViewModel()

        BaseComposableLayout(
            viewModel = viewModel,
            effectHandler = { context, effect ->
                when (effect) {
                    HistoryUIEffect.NavigateToCalendar -> navigateToCalendar()
                    is HistoryUIEffect.NavigateToWorkoutDetail -> navigateToWorkoutDetail(effect.workoutId)
                }
            }
        ) { state ->
            HistoryScreen(
                state = state,
                onEvent = viewModel::setEvent
            )
        }
    }
}