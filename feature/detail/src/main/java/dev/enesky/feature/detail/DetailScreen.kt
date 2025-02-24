package dev.enesky.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.ui.theme.MovieCatalogTheme
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
    onMovieClick: (Int) -> Unit = {},
    onNavigateToPlayerScreen: (Int) -> Unit = {},
) {
    ObserveAsEvents(eventFlow) { detailEvent ->
        when (detailEvent) {
            is DetailEvent.OnError -> { /* Handle error */ }
            is DetailEvent.OnMovieClick -> onMovieClick(detailEvent.movieId)
        }
    }

    DetailContent(
        modifier = modifier,
        movieDetail = uiState.movieDetail,
        onMovieClick = onMovieClick,
    )
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail? = null,
    onMovieClick: (Int) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Detail Screen")

        Spacer(modifier = Modifier.padding(16.dp))

        Button(onClick = { onMovieClick(1) }) {
            Text(text = "Navigate to Player")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = movieDetail?.posterUrl)
                        .crossfade(true)
                        .error(android.R.drawable.stat_notify_error)
                        .listener(
                            onError = { _, result ->
                                // TODO: Add error handling -> "Error: ${result.throwable}"
                            }
                        )
                        .build()
                ),
                contentScale = ContentScale.FillHeight,
                contentDescription = "",
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = movieDetail.toString())
        }
    }
}

@Preview
@Composable
private fun DetailScreenPreview() {
    MovieCatalogTheme {
        DetailContent()
    }
}
