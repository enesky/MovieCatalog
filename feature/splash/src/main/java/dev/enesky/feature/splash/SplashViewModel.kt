package dev.enesky.feature.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enesky.core.common.data.base.BaseViewModel
import dev.enesky.core.common.data.delegate.IErrorEvent
import dev.enesky.core.common.data.delegate.IEvent
import dev.enesky.core.common.data.delegate.IUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SplashUiState, SplashEvent>(initialState = { SplashUiState() }) {

    init {
        imitateSessionCheck()
    }

    private fun imitateSessionCheck() {
        viewModelScope.launch(Dispatchers.IO) {
            updateUiState { copy(isLoading = true) }
            // Simulate session check
            delay(3000)
            triggerEvent {
                SplashEvent.OnNavigateToHomeScreen
            }
            updateUiState { copy(isLoading = false) }
        }
    }
}

data class SplashUiState(
    override val isLoading: Boolean = false,
    val errorMessage: String? = null,
) : IUiState

sealed interface SplashEvent : IEvent {
    data class OnError(override val errorMessage: String) : SplashEvent, IErrorEvent
    data object OnNavigateToHomeScreen : SplashEvent
}
