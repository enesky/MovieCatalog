package dev.enesky.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.enesky.core.domain.constant.MovieConstants
import dev.enesky.core.domain.model.Movie
import dev.enesky.core.ui.annotation.PreviewUiMode
import dev.enesky.core.ui.components.ImagePlaceholder
import dev.enesky.core.ui.components.MyNetworkImage
import dev.enesky.core.ui.components.placeholder
import dev.enesky.core.ui.theme.MovieCatalogTheme

/**
 * Created by Enes Kamil YILMAZ on 25/02/2025
 */

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    isPlaceholder: Boolean = false,
    onNavigateDetailsClick: ((id: Int) -> Unit)? = null,
) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    val itemWidth = screenWidth / 3.8f
    val itemHeight = itemWidth * 1.75f

    Column(
        modifier = if (isPlaceholder) {
            modifier
        } else {
            modifier.clickable {
                onNavigateDetailsClick?.invoke(movie.id)
            }
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box {
            if (isPlaceholder) {
                ImagePlaceholder(
                    modifier = Modifier
                        .size(itemWidth, itemHeight)
                        .clip(MovieCatalogTheme.shapes.small),
                )
            } else {
                MyNetworkImage(
                    modifier = Modifier
                        .size(itemWidth, itemHeight)
                        .clip(MovieCatalogTheme.shapes.small),
                    model = movie.posterUrl,
                    contentDescription = movie.title,
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f),
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(vertical = 2.dp, horizontal = 4.dp),
                    text = movie.rating,
                    maxLines = 1,
                    style = MovieCatalogTheme.typography.regular.h6,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        // Movie Title
        Text(
            modifier = Modifier
                .width(itemWidth)
                .padding(vertical = 2.dp)
                .then(
                    if (isPlaceholder) Modifier.placeholder() else Modifier
                ),
            text = movie.title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MovieCatalogTheme.typography.regular.h6,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@PreviewUiMode
@Composable
private fun AnimeItemPreview() {
    MovieCatalogTheme {
        MovieItem(
            movie = MovieConstants.PLACEHOLDER_MOVIE,
            isPlaceholder = false,
        )
    }
}

