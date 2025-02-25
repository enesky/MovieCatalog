package dev.enesky.core.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import com.google.accompanist.placeholder.material.placeholder
import dev.enesky.core.ui.theme.MovieCatalogTheme

@Composable
fun ImagePlaceholder(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .placeholder(color = color),
    )
}

fun Modifier.placeholder() = composed {
    placeholder(color = MaterialTheme.colorScheme.surfaceVariant)
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
@Composable
fun Modifier.placeholder(
    color: Color = MaterialTheme.colorScheme.surfaceVariant
) = composed {
    placeholder(
        visible = true,
        color = color,
        shape = MovieCatalogTheme.shapes.small,
    )
}
