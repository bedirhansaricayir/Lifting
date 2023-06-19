package com.fitness.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.app.core.util.Resource
import com.fitness.app.domain.GetProgramDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Kullanıcı arayüzü değişikliklerinde yok edilmeyen  kullanıcı arayüzüyle ilgili verileri saklar ve business logic'den sorumludur.
 * Bu sınıfta hiç bir UI bileşeni bulunmaz.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProgramDataUseCase: GetProgramDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomePageUiState(isLoading = false))
    val state: StateFlow<HomePageUiState> = _state.asStateFlow()

    init {
        getProgramData()
    }

    //Kullanıcı arayüzündeki etkileşimleri bu fonksiyon sayesinde yakalıyoruz ve yönetiyoruz.
    fun onEvent(event: HomePageEvent) {
        when (event) {
            is HomePageEvent.OnWorkoutProgramPlayButtonClicked -> {
                _state.value = _state.value.copy(
                    selectedProgram = event.uygulanis,
                    selectedProgramName = event.isim
                )
            }
        }
    }

    //Programların çekilip data haline getirildiği fonksiyon
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