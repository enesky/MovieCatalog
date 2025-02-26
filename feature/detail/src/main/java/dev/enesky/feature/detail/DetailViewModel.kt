package dev.enesky.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enesky.core.common.data.base.BaseViewModel
import dev.enesky.core.common.data.delegate.IErrorEvent
import dev.enesky.core.common.data.delegate.IEvent
import dev.enesky.core.common.data.delegate.IUiState
import dev.enesky.core.common.data.fold
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.domain.usecase.GetMovieDetailsUseCase
import dev.enesky.feature.detail.navigation.Detail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailUiState, DetailEvent>(
    initialState = { DetailUiState() }
) {

    init {
        getMovieDetails()
    }

    fun getMovieDetails() {
        val args: Detail = savedStateHandle.toRoute()
        viewModelScope.launch(Dispatchers.IO) {

            updateUiState { copy(isLoading = true) }
            getMovieDetailUseCase.invoke(id = args.movieId).fold(
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

data class DetailUiState(
    override val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val movieDetail: MovieDetail? = null,
) : IUiState

sealed interface DetailEvent : IEvent {
    data class OnError(override val errorMessage: String) : DetailEvent, IErrorEvent
}
