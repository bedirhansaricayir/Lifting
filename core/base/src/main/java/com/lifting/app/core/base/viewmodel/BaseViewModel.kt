package com.lifting.app.core.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE : State, EVENT : Event, EFFECT : Effect> : ViewModel() {

    private val initialState: STATE by lazy { setInitialState() }

    abstract fun setInitialState(): STATE

    private val _state: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    private val _event: MutableSharedFlow<EVENT> = MutableSharedFlow()
    val event: SharedFlow<EVENT> = _event.asSharedFlow()

    private val _effect = Channel<EFFECT>()
    val effect = _effect.receiveAsFlow()

    fun getCurrentState() = state.value

    protected fun updateState(updateFunction: (STATE) -> STATE) {
        while (true) {
            val prevState = _state.value
            val newState = updateFunction(prevState)
            if (_state.compareAndSet(prevState, newState)) {
                return
            }
        }
    }


    init {
        subscribeToEvents()
    }

    abstract fun handleEvents(event: EVENT)

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    fun setState(state: STATE) {
        viewModelScope.launch { _state.emit(state) }
    }

    fun setEvent(event: EVENT) {
        viewModelScope.launch { _event.emit(event) }
    }

    fun setEffect(effect: EFFECT) {
        viewModelScope.launch { _effect.send(effect) }
    }

}

interface State
interface Event
interface Effect
