package dev.enesky.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun MovieCatalogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

object MovieCatalogTheme {
    val shapes: MovieCatalogShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalMovieCatalogShapes.current

    val spacing: MovieCatalogSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalMovieCatalogSpacing.current

    val colors: MovieCatalogColors
        @Composable
        @ReadOnlyComposable
        get() = LocalMovieCatalogColors.current

    val typography: MovieCatalogTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalMovieCatalogTypography.current
}
