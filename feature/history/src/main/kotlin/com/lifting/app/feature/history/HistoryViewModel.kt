package com.lifting.app.feature.history

import androidx.lifecycle.viewModelScope
import com.lifting.app.core.base.viewmodel.BaseViewModel
import com.lifting.app.core.data.repository.workouts.WorkoutsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by bedirhansaricayir on 08.03.2025
 */

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val workoutsRepository: WorkoutsRepository,
) : BaseViewModel<HistoryUIState, HistoryUIEvent, HistoryUIEffect>() {
    override fun setInitialState(): HistoryUIState = HistoryUIState.Loading

    override fun handleEvents(event: HistoryUIEvent) {
        when (event) {
            else -> Unit
        }
    }

    init {
        setUiState()
    }

    private fun setUiState() {
        viewModelScope.launch {
            workoutsRepository.getWorkoutsWithExtraInfo()
                .onStart { setState(HistoryUIState.Loading) }
                .catch { setState(HistoryUIState.Error) }
                .collect { setState(HistoryUIState.Success(it)) }

        }
    }

}