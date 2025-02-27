package dev.enesky.core.common.data.delegate

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Enes Kamil YILMAZ on 21/11/2023
 */

/**
 * Base interface for defining new UiState data classes
 **/
interface IUiState {
    val isLoading: Boolean
}

/**
 * Base interface for ui state delegation
 **/
interface UiState<T> {

    /**
     * Main ui state flow
     **/
    val uiState: StateFlow<T>

    /**
     * Ui state value for ease of use
     **/
    val currentState: T

    /**
     * Setter function for updating the ui state
     * Usage:
     *     setState {
     *         copy(loading = false)
     *     }
     **/
    suspend fun updateUiState(reduce: T.() -> T)
}

/**
 * Delegation class for ui state usage
 **/
class UiStateDelegate<State : IUiState>(
    initialState: () -> State,
) : UiState<State> {

    private val lazyInitialState by lazy(initialState)

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(lazyInitialState)
    override val uiState: StateFlow<State>
        get() = _uiState

    override val currentState: State = uiState.value

    override suspend fun updateUiState(reduce: State.() -> State) {
        val updatedState = _uiState.value.reduce()
        _uiState.emit(updatedState)
    }
}
