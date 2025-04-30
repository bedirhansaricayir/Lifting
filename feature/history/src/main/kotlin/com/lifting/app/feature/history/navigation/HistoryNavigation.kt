package com.lifting.app.feature.history.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lifting.app.core.common.extensions.clearRouteArgument
import com.lifting.app.core.common.extensions.observeRouteArgument
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.SELECTED_CALENDAR_DAY
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.SELECTED_CALENDAR_MONTH
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.SELECTED_CALENDAR_YEAR
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
        val state by viewModel.state.collectAsStateWithLifecycle()
        val effect = viewModel.effect

        LaunchedEffect(effect) {
            effect.collect { effect ->
                when (effect) {
                    HistoryUIEffect.NavigateToCalendar -> navigateToCalendar()
                    is HistoryUIEffect.NavigateToWorkoutDetail -> navigateToWorkoutDetail(effect.workoutId)
                    else -> Unit
                }
            }
        }

        val selectedYear = 
            entry.observeRouteArgument<Int?>(SELECTED_CALENDAR_YEAR, null).collectAsStateWithLifecycle()

        val selectedMonth = 
            entry.observeRouteArgument<String?>(SELECTED_CALENDAR_MONTH,null).collectAsStateWithLifecycle()

        val selectedDay =
            entry.observeRouteArgument<String?>(SELECTED_CALENDAR_DAY, null).collectAsStateWithLifecycle()

        LaunchedEffect(key1 = selectedYear) {
            selectedYear.value?.let {
                viewModel.setSelectedYear(it)
            }.also {
                entry.clearRouteArgument<Int>(SELECTED_CALENDAR_YEAR)
            }
        }

        LaunchedEffect(key1 = selectedMonth) {
            selectedMonth.value?.let {
                viewModel.setSelectedMonth(it)
            }.also { 
                entry.clearRouteArgument<String>(SELECTED_CALENDAR_MONTH)
            }
        }

        LaunchedEffect(key1 = selectedDay) {
            selectedDay.value?.let {
                viewModel.setSelectedDay(it)
            }.also {
                entry.clearRouteArgument<String>(SELECTED_CALENDAR_DAY)
            }
        }

        HistoryScreen(
            state = state,
            onEvent = viewModel::setEvent
        )
    }
}