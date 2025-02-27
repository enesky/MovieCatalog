package dev.enesky.feature.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.enesky.core.domain.constant.MovieConstants
import dev.enesky.core.ui.annotation.PreviewUiMode
import dev.enesky.core.ui.theme.MovieCatalogTheme
import dev.enesky.feature.detail.R

/**
 * Created by Enes Kamil YILMAZ on 26/02/2025
 */
@Composable
fun SummaryItem(
    modifier: Modifier = Modifier,
    summary: String = "",
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MovieCatalogTheme.spacing.medium),
    ) {
        HorizontalDivider(
            modifier = Modifier.padding(vertical = MovieCatalogTheme.spacing.medium),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f),
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.label_summary),
            style = MovieCatalogTheme.typography.regular.h4,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MovieCatalogTheme.spacing.xSmall),
            text = summary,
            style = MovieCatalogTheme.typography.regular.h6,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@PreviewUiMode
@Composable
private fun SummaryItemPreview() {
    MovieCatalogTheme {
        SummaryItem(
            summary = MovieConstants.PLACEHOLDER_DETAILED_MOVIE.overview
        )
    }
}
