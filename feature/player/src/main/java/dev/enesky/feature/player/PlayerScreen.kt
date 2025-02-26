package dev.enesky.feature.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.core.domain.constant.MovieConstants
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.ui.annotation.PreviewUiMode
import dev.enesky.core.ui.theme.MovieCatalogTheme
import dev.enesky.feature.player.components.MediaPlayer
import dev.enesky.feature.player.components.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */
@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    uiState: PlayerUiState = PlayerUiState(),
    eventFlow: Flow<PlayerEvent> = emptyFlow(),
) {
    ObserveAsEvents(eventFlow) { playerEvent ->
        when (playerEvent) {
            is PlayerEvent.OnError -> { /* Handle error */ }
        }
    }

    PlayerContent(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        movieDetail = uiState.movieDetail,
    )
}

@Composable
fun PlayerContent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail? = null
) {
    var isFullscreen by rememberSaveable { mutableStateOf(false) }

    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            MediaPlayer(
                modifier = Modifier.fillMaxWidth(),
                onFullscreenChange = { fullscreen ->
                    isFullscreen = fullscreen
                },
                isInFullscreenMode = isFullscreen
            )
        }
        if (isFullscreen.not()) {
            item {
                MovieDetails(movieDetail = movieDetail)
            }
        }
    }
}

@PreviewUiMode
@Composable
private fun DetailScreenPreview() {
    MovieCatalogTheme {
        PlayerContent(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            movieDetail = MovieConstants.PLACEHOLDER_DETAILED_MOVIE
        )
    }
}
