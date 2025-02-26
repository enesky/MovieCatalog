package dev.enesky.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.core.domain.constant.MovieConstants
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.ui.annotation.PreviewUiMode
import dev.enesky.core.ui.components.SwipeRefresh
import dev.enesky.core.ui.theme.MovieCatalogTheme
import dev.enesky.feature.detail.components.DetailedMoviePreview
import dev.enesky.feature.detail.components.GenreItem
import dev.enesky.feature.detail.components.SummaryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    uiState: DetailUiState = DetailUiState(),
    eventFlow: Flow<DetailEvent> = emptyFlow(),
    onRefresh: () -> Unit = {},
    onNavigateToPlayerScreen: (Int) -> Unit = {},
) {
    ObserveAsEvents(eventFlow) { detailEvent ->
        when (detailEvent) {
            is DetailEvent.OnError -> { /* Handle error */ }
        }
    }

    DetailContent(
        modifier = modifier,
        isLoading = uiState.isLoading,
        movieDetail = uiState.movieDetail,
        onRefresh = onRefresh,
        onNavigateToPlayer = onNavigateToPlayerScreen,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    movieDetail: MovieDetail? = null,
    onRefresh: () -> Unit = {},
    onNavigateToPlayer: (Int) -> Unit = {},
) {
    val listState = rememberLazyListState()

    SwipeRefresh(
        modifier = modifier,
        isRefreshing = isLoading,
        onRefresh = { onRefresh() },
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            contentPadding = PaddingValues(
                bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
            ),
            state = listState,
        ) {
            item {
                DetailedMoviePreview(
                    movieDetail = movieDetail,
                    onTrailerClick = onNavigateToPlayer,
                )
            }
            item {
                GenreItem(genreList = movieDetail?.genres ?: listOf())
            }
            item {
                SummaryItem(summary = movieDetail?.overview ?: "")
            }
        }
    }

}

@PreviewUiMode
@Composable
private fun DetailScreenPreview() {
    MovieCatalogTheme {
        DetailContent(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            movieDetail = MovieConstants.PLACEHOLDER_DETAILED_MOVIE
        )
    }
}
