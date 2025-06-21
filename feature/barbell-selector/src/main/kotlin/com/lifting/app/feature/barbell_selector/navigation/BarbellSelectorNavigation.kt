package com.lifting.app.feature.barbell_selector.navigation

import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.SELECTED_BARBELL_JUNCTION_RESULT
import com.lifting.app.core.ui.BaseComposableLayout
import com.lifting.app.feature.barbell_selector.BarbellSelectorScreen
import com.lifting.app.feature.barbell_selector.BarbellSelectorUIEffect
import com.lifting.app.feature.barbell_selector.BarbellSelectorUIEvent
import com.lifting.app.feature.barbell_selector.BarbellSelectorViewModel

/**
 * Created by bedirhansaricayir on 29.05.2025
 */

fun NavController.navigateToBarbellSelector() =
    navigate(LiftingScreen.BarbellSelectorBottomSheet().route)

fun NavGraphBuilder.barbellSelectorBottomSheetScreen(
    navController: NavController
) {
    bottomSheet(LiftingScreen.BarbellSelectorBottomSheet().route) {
        val viewModel: BarbellSelectorViewModel = hiltViewModel()
        val selectedBarbellWithJunctionId = navController.previousBackStackEntry?.savedStateHandle?.get<String>(
            SELECTED_BARBELL_JUNCTION_RESULT
        )

        LaunchedEffect(selectedBarbellWithJunctionId) {
            selectedBarbellWithJunctionId?.let {
                viewModel.setEvent(
                    BarbellSelectorUIEvent.OnReceivedBarbellChangeRequest(
                        selectedBarbellWithJunctionId
                    )
                )
            }
        }

        BaseComposableLayout(
            viewModel = viewModel,
            effectHandler = { context, effect ->
                when (effect) {
                    is BarbellSelectorUIEffect.SetBarbellToBackStack -> {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            SELECTED_BARBELL_JUNCTION_RESULT,
                            effect.barbellWithJunctionId
                        )
                        navController.popBackStack()
                    }
                }
            }
        ) { state ->
            BarbellSelectorScreen(
                state = state,
                onEvent = viewModel::setEvent
            )
        }
    }
} 