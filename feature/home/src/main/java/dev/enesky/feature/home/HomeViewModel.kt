package dev.enesky.feature.home

import dev.enesky.core.common.data.base.BaseViewModel
import dev.enesky.core.common.data.delegate.IErrorEvent
import dev.enesky.core.common.data.delegate.IEvent
import dev.enesky.core.common.data.delegate.IUiState
import dev.enesky.core.domain.usecase.GetPopularMoviesUseCase
import dev.enesky.core.domain.usecase.GetTopRatedMoviesUseCase
import dev.enesky.core.domain.usecase.GetUpcomingMoviesUseCase

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
class HomeViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
) : BaseViewModel<HomeUiState, HomeEvent>(
    initialState = { HomeUiState() }
) {
    // ViewModel implementation
}

data class HomeUiState(
    override val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val popularMovies: List<MovieResponse>? = null,
    val topRatedMovies: List<MovieResponse>? = null,
    val upcomingMovies: List<MovieResponse>? = null,
): IUiState

sealed interface HomeEvent : IEvent {
    data class OnError(override val errorMessage: String) : HomeEvent, IErrorEvent
    data class OnMovieClick(val movieId: Int) : HomeEvent
    data class OnPopularMoviesLoaded(val movies: List<MovieResponse>) : HomeEvent
    data class OnTopRatedMoviesLoaded(val movies: List<MovieResponse>) : HomeEvent
    data class OnUpcomingMoviesLoaded(val movies: List<MovieResponse>) : HomeEvent
}
