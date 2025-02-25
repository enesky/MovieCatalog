package dev.enesky.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.core.domain.constant.MovieConstants
import dev.enesky.core.domain.model.Movie
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.ui.theme.MovieCatalogTheme
import dev.enesky.feature.home.components.MoviePagingRow
import dev.enesky.feature.home.components.MoviePreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState = HomeUiState(),
    eventFlow: Flow<HomeEvent> = emptyFlow(),
    onMovieClick: (Int) -> Unit = {},
    onNavigateToDetail: (Int) -> Unit = {},
) {
    ObserveAsEvents(eventFlow) { homeEvent ->
        when (homeEvent) {
            is HomeEvent.OnError -> { /* Handle error */ }
            is HomeEvent.OnMovieClick -> onMovieClick(homeEvent.movieId)
        }
    }

    val nowPlayingMovies = uiState.nowPlayingMovies?.collectAsLazyPagingItems()
    val popularMovies = uiState.popularMovies?.collectAsLazyPagingItems()
    val topRatedMovies = uiState.topRatedMovies?.collectAsLazyPagingItems()
    val upcomingMovies = uiState.upcomingMovies?.collectAsLazyPagingItems()

    HomeContent(
        modifier = modifier,
        movieDetail = uiState.previewMovieDetail,
        nowPlayingMovies = nowPlayingMovies,
        popularMovies = popularMovies,
        topRatedMovies = topRatedMovies,
        upcomingMovies = upcomingMovies,
        onMovieClick = onNavigateToDetail,
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail? = null,
    nowPlayingMovies: LazyPagingItems<Movie>? = null,
    popularMovies: LazyPagingItems<Movie>? = null,
    topRatedMovies: LazyPagingItems<Movie>? = null,
    upcomingMovies: LazyPagingItems<Movie>? = null,
    onMovieClick: (Int) -> Unit = {},
) {

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding(),
        ),
    ) {
        item {
            MoviePreview(
                movieDetail = movieDetail,
                onMovieClick = onMovieClick,
            )
        }
        item {
            MoviePagingRow(
                title = stringResource(id = R.string.label_now_playing),
                pagingItems = nowPlayingMovies,
                onMovieClick = onMovieClick,
            )
        }
        item {
            MoviePagingRow(
                title = stringResource(id = R.string.label_popular),
                pagingItems = popularMovies,
                onMovieClick = onMovieClick,
            )
        }
        item {
            MoviePagingRow(
                title = stringResource(id = R.string.label_top_rated),
                pagingItems = topRatedMovies,
                onMovieClick = onMovieClick,
            )
        }
        item {
            MoviePagingRow(
                title = stringResource(id = R.string.label_upcoming),
                pagingItems = upcomingMovies,
                onMovieClick = onMovieClick,
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MovieCatalogTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center,
        ) {
            HomeContent(
                movieDetail = MovieConstants.PLACEHOLDER_DETAILED_MOVIE
            ) {}
        }

    }
}
