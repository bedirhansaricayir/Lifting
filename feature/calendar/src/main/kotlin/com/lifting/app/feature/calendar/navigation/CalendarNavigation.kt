package com.lifting.app.feature.calendar.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.ui.BaseComposableLayout
import com.lifting.app.feature.calendar.CalendarScreen
import com.lifting.app.feature.calendar.CalendarUIEffect
import com.lifting.app.feature.calendar.CalendarViewModel

/**
 * Created by bedirhansaricayir on 09.03.2025
 */

fun NavController.navigateToCalendar() = navigate(LiftingScreen.Calendar)

fun NavGraphBuilder.calendarScreen(
    onNavigateBack: () -> Unit,
    onNavigateToWorkoutDetail: (String) -> Unit
) {
    composable<LiftingScreen.Calendar> {
        val viewModel: CalendarViewModel = hiltViewModel()

        BaseComposableLayout(
            viewModel = viewModel,
            effectHandler = { context, effect ->
                when (effect) {
                    CalendarUIEffect.NavigateBack -> onNavigateBack()
                    is CalendarUIEffect.NavigateToWorkoutDetail -> onNavigateToWorkoutDetail(effect.workoutId)
                }
            }
        ) { state ->
            CalendarScreen(
                state = state,
                onEvent = viewModel::setEvent,
            )
        }
    }
}