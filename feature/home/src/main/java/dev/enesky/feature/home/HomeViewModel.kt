package dev.enesky.feature.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enesky.core.common.data.base.BaseViewModel
import dev.enesky.core.common.data.delegate.IErrorEvent
import dev.enesky.core.common.data.delegate.IEvent
import dev.enesky.core.common.data.delegate.IUiState
import dev.enesky.core.common.data.fold
import dev.enesky.core.common.remoteconfig.RemoteConfigManager
import dev.enesky.core.domain.constant.MovieCategory
import dev.enesky.core.domain.model.Movie
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.domain.usecase.GetCategorizedMoviesUseCase
import dev.enesky.core.domain.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategorizedMoviesUseCase: GetCategorizedMoviesUseCase,
    private val getMovieDetailUseCase: GetMovieDetailsUseCase
) : BaseViewModel<HomeUiState, HomeEvent>(
    initialState = { HomeUiState() }
) {

    init {
        getMovies()
        getMovieDetails()
    }

    private fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            updateUiState { copy(isLoading = true) }

            val nowPlayingMovies = getCategorizedMoviesUseCase.invoke(MovieCategory.NOW_PLAYING)
                .distinctUntilChanged()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)

            val popularMovies = getCategorizedMoviesUseCase.invoke(MovieCategory.POPULAR)
                .distinctUntilChanged()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)

            val topRatedMovies = getCategorizedMoviesUseCase.invoke(MovieCategory.TOP_RATED)
                .distinctUntilChanged()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)

            val upcomingMovies = getCategorizedMoviesUseCase.invoke(MovieCategory.UPCOMING)
                .distinctUntilChanged()
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)

            updateUiState {
                copy(
                    isLoading = false,
                    nowPlayingMovies = nowPlayingMovies,
                    popularMovies = popularMovies,
                    topRatedMovies = topRatedMovies,
                    upcomingMovies = upcomingMovies
                )
            }
        }
    }

    private fun getMovieDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            getMovieDetailUseCase.invoke(id = RemoteConfigManager.Values.previewMovieId.toInt()).fold(
                onSuccess = {
                    updateUiState {
                        copy(
                            isLoading = false,
                            previewMovieDetail = it,
                            errorMessage = null
                        )
                    }
                },
                onError = {
                    updateUiState {
                        copy(
                            isLoading = false,
                            previewMovieDetail = null,
                            errorMessage = it.message
                        )
                    }
                }
            )
        }
    }
}

data class HomeUiState(
    override val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val previewMovieDetail: MovieDetail? = null,
    val nowPlayingMovies: Flow<PagingData<Movie>>? = null,
    val popularMovies: Flow<PagingData<Movie>>? = null,
    val topRatedMovies: Flow<PagingData<Movie>>? = null,
    val upcomingMovies: Flow<PagingData<Movie>>? = null,
) : IUiState

sealed interface HomeEvent : IEvent {
    data class OnError(override val errorMessage: String) : HomeEvent, IErrorEvent
    data class OnMovieClick(val movieId: Int) : HomeEvent
}
