package dev.enesky.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.core.ui.theme.MovieCatalogTheme
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

    HomeContent(
        modifier = modifier,
        homeUiState = uiState,
        onMovieClick = onNavigateToDetail,
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState = HomeUiState(),
    onMovieClick: (Int) -> Unit = {},
) {
    val popularMovies = homeUiState.popularMovies?.collectAsLazyPagingItems()
    val topRatedMovies = homeUiState.topRatedMovies?.collectAsLazyPagingItems()
    val upcomingMovies = homeUiState.upcomingMovies?.collectAsLazyPagingItems()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Home Screen")
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = {
            onMovieClick(1)
        }) {
            Text(text = "Navigate to Detail")
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MovieCatalogTheme {
        HomeContent {}
    }
}
