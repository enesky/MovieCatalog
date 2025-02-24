package dev.enesky.feature.player

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enesky.core.common.data.base.BaseViewModel
import dev.enesky.core.common.data.delegate.IErrorEvent
import dev.enesky.core.common.data.delegate.IEvent
import dev.enesky.core.common.data.delegate.IUiState
import dev.enesky.core.common.data.fold
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.domain.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */
@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : BaseViewModel<PlayerUiState, PlayerEvent>(
    initialState = { PlayerUiState() }
) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getMoviePlayers(1231)
        }
    }

    private suspend fun getMoviePlayers(movieId: Int) {
        updateUiState { copy(isLoading = true) }
        getMovieDetailsUseCase.invoke(id = movieId).fold(
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

data class PlayerUiState(
    override val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val movieDetail: MovieDetail? = null,
) : IUiState

sealed interface PlayerEvent : IEvent {
    data class OnError(override val errorMessage: String) : PlayerEvent, IErrorEvent
    data class OnMovieClick(val movieId: Int) : PlayerEvent
}
