package dev.enesky.feature.detail.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.enesky.core.domain.constant.MovieConstants
import dev.enesky.core.domain.model.Genre
import dev.enesky.core.ui.annotation.PreviewUiMode
import dev.enesky.core.ui.theme.MovieCatalogTheme
import okhttp3.internal.immutableListOf

/**
 * Created by Enes Kamil YILMAZ on 26/02/2025
 */
@Composable
fun GenreItem(
    modifier: Modifier = Modifier,
    genreList: List<Genre>? = immutableListOf(),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MovieCatalogTheme.spacing.medium),
    ) {
        HorizontalDivider(
            modifier = Modifier.padding(bottom = MovieCatalogTheme.spacing.medium),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f),
        )
        Text(
            text = "Genres",
            style = MovieCatalogTheme.typography.regular.h4,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(MovieCatalogTheme.spacing.xSmall))
        Row {
            genreList?.forEach { genre ->
                Text(
                    modifier = Modifier
                        .padding(end = MovieCatalogTheme.spacing.xSmall)
                        .border(
                            width = MovieCatalogTheme.spacing.border,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = MovieCatalogTheme.shapes.small
                        )
                        .padding(MovieCatalogTheme.spacing.xxxSmall),
                    text = genre.name,
                    style = MovieCatalogTheme.typography.regular.h6,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}

@PreviewUiMode
@Composable
private fun PreviewGenreItems() {
    MovieCatalogTheme {
        GenreItem(
            modifier = Modifier,
            genreList = MovieConstants.PLACEHOLDER_DETAILED_MOVIE.genres
        )
    }
}
