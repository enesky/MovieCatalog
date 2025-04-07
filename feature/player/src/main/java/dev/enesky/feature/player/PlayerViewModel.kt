package dev.enesky.feature.player

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enesky.core.common.data.base.BaseViewModel
import dev.enesky.core.common.data.delegate.IErrorEvent
import dev.enesky.core.common.data.delegate.IEvent
import dev.enesky.core.common.data.delegate.IUiState
import dev.enesky.core.common.data.fold
import dev.enesky.core.common.remoteconfig.RemoteConfigManager
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.domain.usecase.GetMovieDetailsUseCase
import dev.enesky.feature.player.navigation.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */
@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<PlayerUiState, PlayerEvent>(
    initialState = { PlayerUiState() }
) {

    init {
        getMovieDetails()
    }

    private fun getMovieDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            updateUiState { copy(isLoading = true) }
            val args: Player? = savedStateHandle.toRoute()
            val id = args?.movieId ?: RemoteConfigManager.Values.previewMovieId.toInt()
            getMovieDetailsUseCase.invoke(id = id).fold(
                onSuccess = {
                    updateUiState {
                        copy(
                            isLoading = false,
                            movieDetail = it,
                            errorMessage = null
                        )
                    }
                },
                onError = {
                    updateUiState {
                        copy(
                            isLoading = false,
                            movieDetail = null,
                            errorMessage = it.message
                        )
                    }
                }
            )
        }
    }
}

@Stable
data class PlayerUiState(
    override val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val movieDetail: MovieDetail? = null,
) : IUiState

sealed interface PlayerEvent : IEvent {
    data class OnError(override val errorMessage: String) : PlayerEvent, IErrorEvent
}
