package dev.enesky.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.enesky.core.ui.theme.MovieCatalogTheme

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */

@Composable
fun BoxScope.BuildTypeIndicator(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .align(Alignment.BottomStart)
            .padding(vertical = MovieCatalogTheme.spacing.xSmall)
            .padding(bottom = WindowInsets.systemBars.asPaddingValues().calculateBottomPadding())
    ) {
        Text(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.error,
                    shape = RoundedCornerShape(
                        topEnd = MovieCatalogTheme.spacing.xSmall,
                        bottomEnd = MovieCatalogTheme.spacing.xSmall,
                    ),
                )
                .padding(horizontal = MovieCatalogTheme.spacing.xSmall),
            text = text,
            color = MaterialTheme.colorScheme.onError,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Preview
@Composable
private fun BuildTypeIndicatorPreview() {
    Box {
         BuildTypeIndicator(
            text = "Debug"
        )
    }
}
