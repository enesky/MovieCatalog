package dev.enesky.moviecatalog

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enesky.core.common.connectivity.ConnectivityListener
import dev.enesky.core.common.data.base.BaseViewModel
import dev.enesky.core.common.data.delegate.IErrorEvent
import dev.enesky.core.common.data.delegate.IEvent
import dev.enesky.core.common.data.delegate.IUiState
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Enes Kamil YILMAZ on 27/02/2025
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val connectivityListener: ConnectivityListener
) : BaseViewModel<MainUiState, MainEvent>(
    initialState = { MainUiState() }
) {
    init {
        listenConnectivity()
    }

    private fun listenConnectivity() {
        viewModelScope.launch {
            connectivityListener.isNetworkAvailable.collect {
                if (!it) {
                    triggerEvent { MainEvent.OnNoNetworkDialog }
                }
            }
        }
    }

}

data class MainUiState(
    override val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isConfigLoaded: Boolean = false,
) : IUiState

sealed interface MainEvent : IEvent {
    data class OnError(override val errorMessage: String) : MainEvent, IErrorEvent
    data object OnNoNetworkDialog : MainEvent
}
