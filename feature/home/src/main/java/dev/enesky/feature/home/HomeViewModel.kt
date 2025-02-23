package dev.enesky.feature.home

import dev.enesky.core.common.data.base.BaseViewModel
import dev.enesky.core.common.data.delegate.IErrorEvent
import dev.enesky.core.common.data.delegate.IEvent
import dev.enesky.core.common.data.delegate.IUiState
import dev.enesky.core.domain.model.Movie
import dev.enesky.core.domain.usecase.GetCategorizedMoviesUseCase

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
class HomeViewModel(
    private val getCategorizedMoviesUseCase: GetCategorizedMoviesUseCase
) : BaseViewModel<HomeUiState, HomeEvent>(
    initialState = { HomeUiState() }
) {
    // ViewModel implementation
}

data class HomeUiState(
    override val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val popularMovies: List<Movie>? = null,
    val topRatedMovies: List<Movie>? = null,
    val upcomingMovies: List<Movie>? = null,
): IUiState

sealed interface HomeEvent : IEvent {
    data class OnError(override val errorMessage: String) : HomeEvent, IErrorEvent
    data class OnMovieClick(val movieId: Int) : HomeEvent
    data class OnPopularMoviesLoaded(val popularMovies: List<Movie>) : HomeEvent
    data class OnTopRatedMoviesLoaded(val topRatedMovies: List<Movie>) : HomeEvent
    data class OnUpcomingMoviesLoaded(val upcomingMovies: List<Movie>) : HomeEvent
}
