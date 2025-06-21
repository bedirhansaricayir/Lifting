package com.lifting.app.feature.barbell_selector

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.common.extensions.getBackStackArgs
import com.lifting.app.core.data.repository.barbells.BarbellsRepository
import com.lifting.app.core.model.Barbell
import com.lifting.app.core.navigation.screens.LiftingScreen.Companion.SELECTED_BARBELL_JUNCTION_RESULT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 29.05.2025
 */
@HiltViewModel
internal class BarbellSelectorViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val barbellsRepository: BarbellsRepository
) : BaseViewModel<BarbellSelectorUIState, BarbellSelectorUIEvent, BarbellSelectorUIEffect>() {

    private val selectedBarbellWithJunctionId =
        savedStateHandle.getStateFlow<String>(SELECTED_BARBELL_JUNCTION_RESULT, "")

    override fun setInitialState(): BarbellSelectorUIState = BarbellSelectorUIState()

    override fun handleEvents(event: BarbellSelectorUIEvent) {
        when (event) {
            is BarbellSelectorUIEvent.OnReceivedBarbellChangeRequest -> onReceivedBarbellChangeRequest(
                event.selectedBarbellWithJunctionId
            )

            is BarbellSelectorUIEvent.OnBarbellClick -> setBarbellToBackStack(event.barbell)
        }
    }

    init {
        updateUIState()
    }

    private fun updateUIState() {
        combine(
            barbellsRepository.getActiveBarbells(),
            selectedBarbellWithJunctionId
        ) { barbells, selectedBarbellWithJunctionId ->
            val barbellWithJunctionId = selectedBarbellWithJunctionId.getBackStackArgs()
            updateState { currentState ->
                currentState.copy(
                    barbells = barbells.sortedByDescending { it.weightKg ?: it.weightLbs },
                    junctionId = barbellWithJunctionId.first(),
                    barbellId = barbellWithJunctionId.lastOrNull()
                )
            }
        }.launchIn(viewModelScope)


    }

    private fun onReceivedBarbellChangeRequest(selectedBarbellWithJunctionId: String) {
        savedStateHandle[SELECTED_BARBELL_JUNCTION_RESULT] = selectedBarbellWithJunctionId
    }

    private fun setBarbellToBackStack(barbell: Barbell) {
        getCurrentState().apply {
            setEffect(BarbellSelectorUIEffect.SetBarbellToBackStack("${this.junctionId},${barbell.id}"))
        }
    }

}