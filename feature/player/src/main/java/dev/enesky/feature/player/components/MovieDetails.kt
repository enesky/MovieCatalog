package dev.enesky.feature.player.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.enesky.core.domain.constant.MovieConstants
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.ui.annotation.PreviewUiMode
import dev.enesky.core.ui.theme.MovieCatalogTheme

/**
 * Created by Enes Kamil YILMAZ on 26/02/2025
 */
@Composable
fun MovieDetails(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail? = null,
) {
    if (movieDetail == null) {
        return
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MovieCatalogTheme.spacing.medium),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MovieCatalogTheme.spacing.medium),
            text = movieDetail.title,
            style = MovieCatalogTheme.typography.regular.h4,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = movieDetail.runtime,
            style = MovieCatalogTheme.typography.regular.h6,
            color = MaterialTheme.colorScheme.onBackground,
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = MovieCatalogTheme.spacing.medium),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f),
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Summary",
            style = MovieCatalogTheme.typography.regular.h4,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MovieCatalogTheme.spacing.xSmall),
            text = movieDetail.overview,
            style = MovieCatalogTheme.typography.regular.h6,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@PreviewUiMode
@Composable
private fun SummaryItemPreview() {
    MovieCatalogTheme {
        MovieDetails(
            movieDetail = MovieConstants.PLACEHOLDER_DETAILED_MOVIE
        )
    }
}
