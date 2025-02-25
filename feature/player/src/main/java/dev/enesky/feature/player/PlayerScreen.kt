package dev.enesky.feature.player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.ui.theme.MovieCatalogTheme
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
    onMovieClick: (Int) -> Unit = {},
) {
    ObserveAsEvents(eventFlow) { playerEvent ->
        when (playerEvent) {
            is PlayerEvent.OnError -> { /* Handle error */ }
            is PlayerEvent.OnMovieClick -> onMovieClick(playerEvent.movieId)
        }
    }

    PlayerContent(
        modifier = modifier,
        movieDetail = uiState.movieDetail,
        onMovieClick = onMovieClick,
    )
}

@Composable
fun PlayerContent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail? = null,
    onMovieClick: (Int) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(text = "Player Screen")
    }
}

@Preview
@Composable
private fun DetailScreenPreview() {
    MovieCatalogTheme {
        PlayerContent()
    }
}
