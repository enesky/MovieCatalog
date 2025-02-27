package dev.enesky.core.ui.utils


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

enum class WindowSizeClass {
    COMPACT,
    MEDIUM,
    EXPANDED
}

/**
 * Created by Enes Kamil YILMAZ on 27/02/2025
 */
@Composable
fun rememberWindowSizeClass(): WindowSizeClass {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    return remember(screenWidth) {
        when {
            screenWidth < 600.dp -> WindowSizeClass.COMPACT // Phone
            screenWidth < 840.dp -> WindowSizeClass.MEDIUM  // Small tablet
            else -> WindowSizeClass.EXPANDED                // Large tablet
        }
    }
}
