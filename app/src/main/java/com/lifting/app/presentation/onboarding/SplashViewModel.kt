package com.lifting.app.presentation.onboarding

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifting.app.data.local.datastore.DataStoreRepository
import com.lifting.app.navigation.graphs.AuthScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String?> = mutableStateOf(null)
    val startDestination: State<String?> = _startDestination

    init {
        viewModelScope.launch {
            val onBoardingCompleted = repository.readOnBoardingState().first()

            if (onBoardingCompleted) {
                _startDestination.value = AuthScreen.SignInScreen.route
            } else {
                _startDestination.value = AuthScreen.OnBoardingScreen.route
            }

            _isLoading.value = false
        }
    }
}


/*
viewModelScope.launch {
    repository.readOnBoardingState().collect { completed ->
        if (completed) {
            _startDestination.value = AuthScreen.SignInScreen.route
        } else {
            _startDestination.value = AuthScreen.OnBoardingScreen.route
        }
    }
    _isLoading.value = false
}*/
