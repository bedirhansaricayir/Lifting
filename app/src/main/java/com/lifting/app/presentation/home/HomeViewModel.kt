package com.lifting.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.lifting.app.core.constants.Constants
import com.lifting.app.core.util.Resource
import com.lifting.app.domain.model.programMap
import com.lifting.app.domain.use_case.GetProgramDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProgramDataUseCase: GetProgramDataUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _state = MutableStateFlow(HomePageUiState(isLoading = false))
    val state: StateFlow<HomePageUiState> = _state.asStateFlow()

    init {
        getProgramData()
        getSignedInUser()
    }

    fun onEvent(event: HomePageEvent) {
        when (event) {
            is HomePageEvent.OnWorkoutProgramPlayButtonClicked -> {
                _state.value = _state.value.copy(
                    selectedProgram = event.uygulanis,
                    selectedProgramName = event.isim
                )
            }
            is HomePageEvent.OnPersonalizedProgramButtonClicked -> {
                val personalizedProgram = getProgramForGoal(event.days,event.goal)
                _state.value = _state.value.copy(
                    onPersonalizedProgramCreated = personalizedProgram
                )
            }
        }
    }

    private fun getProgramForGoal(days: String, selectedGoal: String?): String? {
        return selectedGoal?.let { goal ->
            programMap[days]?.let { program ->
                when (goal) {
                    Constants.YAG_YAK -> program.burnFat
                    Constants.KAS_KAZAN -> program.gainMuscle
                    Constants.FORM_KORU -> program.maintenance
                    else -> null
                }
            }
        }
    }

    private fun getProgramData() {
        viewModelScope.launch {
            getProgramDataUseCase.invoke().onEach { response ->
                _state.update {
                    when (response) {
                        is Resource.Loading -> {
                            it.copy(isLoading = true)
                        }
                        is Resource.Success -> {
                            it.copy(isLoading = false, programData = response.data)
                        }
                        is Resource.Error -> {
                            it.copy(isLoading = false, error = response.e)
                        }
                    }
                }
            }.collect()
        }
    }

    private fun getSignedInUser() = firebaseAuth.currentUser?.run {
        _state.value = _state.value.copy(
            userData = UserData(
                userId = uid,
                username = displayName,
                profilePictureUrl = photoUrl?.toString()
            )
        )
    }
}