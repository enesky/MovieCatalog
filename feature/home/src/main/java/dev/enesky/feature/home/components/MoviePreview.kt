package dev.enesky.feature.home.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import dev.enesky.core.common.utils.Empty
import dev.enesky.core.domain.constant.MovieConstants
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.ui.annotation.PreviewUiMode
import dev.enesky.core.ui.components.ImagePlaceholder
import dev.enesky.core.ui.components.MyNetworkImage
import dev.enesky.core.ui.theme.MovieCatalogTheme

/**
 * Created by Enes Kamil YILMAZ on 25/02/2025
 */
@Suppress("LongMethod")
@Composable
fun MoviePreview(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail?,
    isLoading: Boolean = false,
    onMovieClick: ((id: Int) -> Unit)? = null,
) {
    val config = LocalConfiguration.current
    val isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE
    val screenWidth = config.screenWidthDp.dp
    val screenHeight = config.screenHeightDp.dp

    // In landscape, limit the height based on available screen height
    val itemHeight = if (isLandscape) {
        minOf(screenHeight * 0.7f, screenWidth * 0.4f)
    } else {
        screenWidth * 0.75f
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = MovieCatalogTheme.spacing.medium)
            .clip(MovieCatalogTheme.shapes.small)
            .clickable(
                enabled = movieDetail != null && onMovieClick != null,
                onClick = { movieDetail?.id?.let { onMovieClick?.invoke(it) } },
            ),
    ) {
        if (isLoading || movieDetail == null) {
            ImagePlaceholder(
                modifier = Modifier
                    .height(itemHeight)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MovieCatalogTheme.colors.transparent,
                                MovieCatalogTheme.colors.softDark,
                            ),
                        ),
                    ),
            )
        } else {
            MyNetworkImage(
                modifier = Modifier.height(itemHeight),
                model = movieDetail.posterUrl,
                contentDescription = movieDetail.title,
            )
        }
        // Background gradient
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MovieCatalogTheme.colors.transparent,
                            MovieCatalogTheme.colors.transparent,
                            MovieCatalogTheme.colors.softDark,
                        ),
                    ),
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(MovieCatalogTheme.spacing.medium),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom,
            ) {
                // Movie Title
                Text(
                    modifier = Modifier,
                    text = movieDetail?.title ?: String.Empty,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MovieCatalogTheme.typography.regular.h3,
                )
                // Movie Genre

                Text(
                    modifier = Modifier,
                    text = movieDetail?.genreText ?: String.Empty,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MovieCatalogTheme.typography.regular.h5,
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(vertical = 2.dp, horizontal = 4.dp),
                text = movieDetail?.rating ?: String.Empty,
                maxLines = 1,
                style = MovieCatalogTheme.typography.regular.h6,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@PreviewUiMode
@Composable
private fun TopAnimePreviewPreview() {
    MovieCatalogTheme {
        MoviePreview(
            movieDetail = MovieConstants.PLACEHOLDER_DETAILED_MOVIE,
        ) {}
    }
}
