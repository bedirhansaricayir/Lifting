package com.lifting.app.feature.calendar.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.SELECTED_CALENDAR_DAY
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.SELECTED_CALENDAR_MONTH
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.SELECTED_CALENDAR_YEAR
import com.lifting.app.feature.calendar.CalendarScreen
import com.lifting.app.feature.calendar.CalendarViewModel
import java.time.LocalDate

/**
 * Created by bedirhansaricayir on 09.03.2025
 */

fun NavController.navigateToCalendar(selectedDate: LocalDate = LocalDate.now()) =
    navigate(LiftingScreen.Calendar(selectedDate.toString()))

fun NavGraphBuilder.calendarScreen(
    navController: NavController,
    onNavigateBack: () -> Unit
) {
    composable<LiftingScreen.Calendar> {
        val viewModel: CalendarViewModel = hiltViewModel()
        val calendar = viewModel.calendarFlow.collectAsLazyPagingItems()
        val workoutsCounts by viewModel.workoutsCounts.collectAsStateWithLifecycle(initialValue = emptyList())

        CalendarScreen(
            calendar = calendar,
            workoutsCounts = workoutsCounts,
            onNavigateBack = onNavigateBack,
            setYearToBackStack = { year ->
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    SELECTED_CALENDAR_YEAR, year
                )
                onNavigateBack()
            },
            setMonthToBackStack = { month ->
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    SELECTED_CALENDAR_MONTH, "${month.month},${month.year}"
                )
                onNavigateBack()
            },
            setDayToBackStack = { day ->
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    SELECTED_CALENDAR_DAY, "${day.day},${day.date.monthValue},${day.date.year}"
                )
                onNavigateBack()
            }
        )
    }
}