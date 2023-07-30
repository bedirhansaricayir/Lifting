package com.lifting.app.feature_home.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifting.app.common.constants.Constants
import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.domain.model.programMap
import com.lifting.app.feature_home.domain.use_case.GetProgramDataUseCase
import com.lifting.app.feature_home.domain.use_case.GetUserInfoUseCase
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
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomePageUiState(isLoading = false))
    val state: StateFlow<HomePageUiState> = _state.asStateFlow()

    private val _userDataState = MutableStateFlow(UserDataState())
    val userDataState: StateFlow<UserDataState> = _userDataState.asStateFlow()
    init {
        getUserInfo()
        getProgramData()
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
    private fun getUserInfo() {
        viewModelScope.launch {
            getUserInfoUseCase.invoke().collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        _userDataState.value = _userDataState.value.copy(
                            userDataLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _userDataState.value = _userDataState.value.copy(
                            email = response.data?.email,
                            username  = response.data?.displayName,
                            profilePictureUrl  = response.data?.photoUrl,
                            isPremium = response.data?.isPremium,
                            userDataLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _userDataState.value = _userDataState.value.copy(
                            userDataLoading = false,
                            userDataError = response.e
                        )
                    }
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
}