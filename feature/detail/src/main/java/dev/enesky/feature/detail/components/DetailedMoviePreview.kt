package dev.enesky.feature.detail.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
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
fun DetailedMoviePreview(
    movieDetail: MovieDetail?,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    backdropHeight: Dp = calculateMoviePreviewHeight(),
    posterSize: Pair<Dp, Dp> = calculatePosterItemDimensions(),
    onTrailerClick: ((id: Int) -> Unit)? = null,
) {
    val previewHeight = backdropHeight + (posterSize.first / 2)

    Box(
        modifier = modifier
            .height(previewHeight)
            .fillMaxWidth()
    ) {
        // Background image
        val backgroundModifier = Modifier
            .fillMaxWidth()
            .height(backdropHeight)
            .align(Alignment.TopStart)
        if (isLoading || movieDetail == null) {
            ImagePlaceholder(
                modifier = backgroundModifier
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MovieCatalogTheme.colors.transparent,
                                MaterialTheme.colorScheme.background,
                            ),
                        ),
                    ),
            )
        } else {
            MyNetworkImage(
                modifier = backgroundModifier,
                model = movieDetail.backdropUrl,
                contentDescription = movieDetail.title,
            )
        }

        // Foreground gradient
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.background,
                            MovieCatalogTheme.colors.transparent,
                            MovieCatalogTheme.colors.transparent,
                            MovieCatalogTheme.colors.transparent,
                            MovieCatalogTheme.colors.transparent,
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.background,
                        ),
                    ),
                ),
        ) {
            // Movie Poster
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(MovieCatalogTheme.spacing.medium),
            ) {
                if (isLoading || movieDetail == null) {
                    ImagePlaceholder(
                        modifier = Modifier
                            .size(posterSize.first, posterSize.second)
                            .clip(MovieCatalogTheme.shapes.small),
                    )
                } else {
                    MyNetworkImage(
                        modifier = Modifier
                            .size(posterSize.first, posterSize.second)
                            .clip(MovieCatalogTheme.shapes.small),
                        model = movieDetail.posterUrl,
                        contentDescription = movieDetail.title,
                    )
                }
            }
            // Movie Title and Genre
            val titlePaddingStart = posterSize.first + MovieCatalogTheme.spacing.medium
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(bottom = MovieCatalogTheme.spacing.small)
                    .padding(start = titlePaddingStart)
                    .padding(MovieCatalogTheme.spacing.medium),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom,
            ) {
                // Movie Title
                Text(
                    modifier = Modifier,
                    text = movieDetail?.title ?: String.Empty,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MovieCatalogTheme.typography.bold.h4,
                )
                // Movie Release Date and Runtime
                Text(
                    modifier = Modifier,
                    text = "${movieDetail?.releaseDate} | ${movieDetail?.runtime}",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MovieCatalogTheme.typography.regular.h6,
                )

                // Movie Rating
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating Icon",
                        tint = MovieCatalogTheme.colors.lightOrange,
                    )
                    Text(
                        modifier = Modifier
                            .padding(
                                vertical = MovieCatalogTheme.spacing.xxSmall,
                                horizontal = MovieCatalogTheme.spacing.xxSmall
                            ),
                        text = movieDetail?.rating ?: String.Empty,
                        style = MovieCatalogTheme.typography.regular.h6,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }

            // Watch Trailer
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(MovieCatalogTheme.spacing.medium)
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.1f),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(vertical = MovieCatalogTheme.spacing.xxxSmall)
                    .padding(
                        start = MovieCatalogTheme.spacing.xxxSmall,
                        end = MovieCatalogTheme.spacing.xSmall
                    )
                    .clickable {
                        onTrailerClick?.invoke(movieDetail?.id ?: 0)
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Rating Icon",
                    tint = MovieCatalogTheme.colors.lightOrange,
                )
                Text(
                    text = "Watch Trailer",
                    style = MovieCatalogTheme.typography.regular.h6,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}

@Composable
private fun calculateMoviePreviewHeight(): Dp {
    val config = LocalConfiguration.current
    val isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE
    val screenWidth = config.screenWidthDp.dp
    val screenHeight = config.screenHeightDp.dp
    val landscapeWidthMultiplier = 0.4f
    val landscapeHeightMultiplier = 0.7f
    val portraitMultiplier = 0.75f

    // In landscape, limit the height based on available screen height
    return if (isLandscape) {
        minOf(screenHeight * landscapeHeightMultiplier, screenWidth * landscapeWidthMultiplier)
    } else {
        screenWidth * portraitMultiplier
    }
}

/**
 * Calculate movie item dimensions based on screen width and orientation
 * Make sure last item slightly visible
 */
@Composable
private fun calculatePosterItemDimensions(): Pair<Dp, Dp> {
    val config = LocalConfiguration.current
    val isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE
    val screenWidth = config.screenWidthDp.dp
    val landscapeDivider = 8f
    val portraitDivider = 4f
    val heightMultiplier = 1.75f

    val itemWidth = if (isLandscape) {
        screenWidth / landscapeDivider
    } else {
        screenWidth / portraitDivider
    }
    val itemHeight = itemWidth * heightMultiplier

    return Pair(itemWidth, itemHeight)
}

@PreviewUiMode
@Composable
private fun PreviewDetailedMoviePreview() {
    MovieCatalogTheme {
        DetailedMoviePreview(
            modifier = Modifier,
            movieDetail = MovieConstants.PLACEHOLDER_DETAILED_MOVIE,
        ) {}
    }
}
