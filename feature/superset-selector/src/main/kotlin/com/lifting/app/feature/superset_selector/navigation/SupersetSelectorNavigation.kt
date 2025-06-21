package com.lifting.app.feature.superset_selector.navigation

import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.lifting.app.core.navigation.screens.LiftingScreen
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.RESULT_SUPERSET_SELECTOR_SUPERSET_ID_KEY
import com.lifting.app.core.ui.BaseComposableLayout
import com.lifting.app.feature.superset_selector.SupersetSelectorScreen
import com.lifting.app.feature.superset_selector.SupersetSelectorUIEffect
import com.lifting.app.feature.superset_selector.SupersetSelectorUIEvent
import com.lifting.app.feature.superset_selector.SupersetSelectorViewModel

/**
 * Created by bedirhansaricayir on 11.06.2025
 */

fun NavController.navigateToSupersetSelector() =
    navigate(LiftingScreen.SupersetSelectorBottomSheet().route)

fun NavGraphBuilder.supersetSelectorBottomSheetScreen(
    navController: NavController
) {
    bottomSheet(LiftingScreen.SupersetSelectorBottomSheet().route) {
        val viewModel: SupersetSelectorViewModel = hiltViewModel()
        val workoutIdWithJunctionId = navController.previousBackStackEntry?.savedStateHandle?.get<String>(
            RESULT_SUPERSET_SELECTOR_SUPERSET_ID_KEY
        )

        LaunchedEffect(workoutIdWithJunctionId) {
            workoutIdWithJunctionId?.let {
                viewModel.setEvent(
                    SupersetSelectorUIEvent.OnSupersetRequest(
                        workoutIdWithJunctionId
                    )
                )
            }
        }

        BaseComposableLayout(
            viewModel = viewModel,
            effectHandler = { context, effect ->
                when (effect) {
                    is SupersetSelectorUIEffect.SetSuperSetToBackStack -> {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            RESULT_SUPERSET_SELECTOR_SUPERSET_ID_KEY,
                            effect.result
                        )
                        navController.popBackStack()
                    }
                }
            }
        ) { state ->
            SupersetSelectorScreen(
                state = state,
                onEvent = viewModel::setEvent
            )
        }
    }
} 