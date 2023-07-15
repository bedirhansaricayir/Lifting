package com.lifting.app.feature_auth.presentation.splash

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifting.app.data.local.datastore.DataStoreRepository
import com.lifting.app.navigation.graphs.AuthScreen
import com.lifting.app.navigation.graphs.Graph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    private val _startDestination: MutableState<String?> = mutableStateOf(null)
    val startDestination: State<String?> = _startDestination

    init {
        navigateState()
    }

    private fun navigateState() {
        viewModelScope.launch {
            val onBoardingCompleted = dataStoreRepository.onBoardingState()
            val onSignInSuccessfully = dataStoreRepository.signInState()
            if (onSignInSuccessfully == true) {
                _startDestination.value = Graph.HOME
            } else if (onBoardingCompleted == true) {
                _startDestination.value = AuthScreen.SignInScreen.route
            } else AuthScreen.OnBoardingScreen.route
        }
    }
}