package dev.enesky.feature.detail

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
class DetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailsUseCase
) : BaseViewModel<DetailUiState, DetailEvent>(
    initialState = { DetailUiState() }
) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getMovieDetails(1231)
        }
    }

    private suspend fun getMovieDetails(movieId: Int) {
        updateUiState { copy(isLoading = true) }
        getMovieDetailUseCase.invoke(id = movieId).fold(
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

data class DetailUiState(
    override val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val movieDetail: MovieDetail? = null,
) : IUiState

sealed interface DetailEvent : IEvent {
    data class OnError(override val errorMessage: String) : DetailEvent, IErrorEvent
    data class OnMovieClick(val movieId: Int) : DetailEvent
}
