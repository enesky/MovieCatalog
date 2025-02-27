package dev.enesky.feature.adaptive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */
@Composable
fun AdaptiveScreen(
    modifier: Modifier = Modifier,
    uiState: PlayerUiState = PlayerUiState(),
    eventFlow: Flow<PlayerEvent> = emptyFlow(),
) {
    ObserveAsEvents(eventFlow) { playerEvent ->
        when (playerEvent) {
            is PlayerEvent.OnError -> { /* Handle error */ }
        }
    }

    AdaptiveScreen(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        movieDetail = uiState.movieDetail,
    )
}

@Composable
fun AdaptiveContent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail? = null
) {
    var isFullscreen by rememberSaveable { mutableStateOf(false) }


}

@PreviewUiMode
@Composable
private fun DetailScreenPreview() {
    MovieCatalogTheme {
        AdaptiveContent(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            movieDetail = MovieConstants.PLACEHOLDER_DETAILED_MOVIE
        )
    }
}
