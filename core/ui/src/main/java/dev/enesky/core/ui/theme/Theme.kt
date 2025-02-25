package dev.enesky.core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun MovieCatalogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
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
