package dev.enesky.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

enum class WindowSize {
    COMPACT,
    MEDIUM,
    EXPANDED
}

/**
 * Created by Enes Kamil YILMAZ on 27/02/2025
 */
@Composable
fun rememberWindowSizeClass(): WindowSize {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    return remember(screenWidth) {
        when {
            screenWidth < 600.dp -> WindowSize.COMPACT // Phone
            screenWidth < 840.dp -> WindowSize.MEDIUM // Small tablet
            else -> WindowSize.EXPANDED // Large tablet
        }
    }
}
