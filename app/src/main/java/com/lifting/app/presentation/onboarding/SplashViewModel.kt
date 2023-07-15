package com.lifting.app.presentation.onboarding

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifting.app.data.local.datastore.DataStoreRepository
import com.lifting.app.navigation.graphs.AuthScreen
import com.lifting.app.navigation.graphs.Graph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableStateFlow<String?> = MutableStateFlow(null)
    val startDestination: StateFlow<String?> = _startDestination.asStateFlow()

    init {
        navigateState()
    }

    private fun navigateState() {
        viewModelScope.launch {
            val onBoardingCompleted = repository.onBoardingState()
            val onSignInSuccessfully = repository.signInState()
            if (onSignInSuccessfully == true) {
                _startDestination.value = Graph.HOME

            } else if (onBoardingCompleted == true) {
                _startDestination.value = AuthScreen.SignInScreen.route

            } else {
                _startDestination.value = AuthScreen.OnBoardingScreen.route
            }
            _isLoading.value = false
        }
    }
}