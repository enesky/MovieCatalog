package dev.enesky.core.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.enesky.core.ui.theme.MovieCatalogTheme

@Composable
fun Message(
    @StringRes messageResourceId: Int,
    modifier: Modifier = Modifier,
    @DrawableRes imageResourceId: Int? = null,
) {
    CenteredBox(modifier = modifier.padding(horizontal = MovieCatalogTheme.spacing.xLarge)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MovieCatalogTheme.spacing.medium),
        ) {
            Image(
                modifier = Modifier.size(72.dp),
                painter = if (imageResourceId != null) {
                    painterResource(id = imageResourceId)
                } else {
                    rememberVectorPainter(Icons.Default.Warning)
                },
                contentDescription = stringResource(id = messageResourceId),
            )
            Text(
                text = stringResource(id = messageResourceId),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
private fun MessagePreview() {
    MovieCatalogTheme {
        Message(
            modifier = Modifier.fillMaxSize(),
            messageResourceId = dev.enesky.core.common.R.string.label_no_movie_result,
        )
    }
}
