package dev.enesky.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Created by Enes Kamil YILMAZ on 25/02/2025
 */

private val DefaultSpace = 0.dp
private val BorderStroke = 1.dp
private val IconSize = 24.dp
private val LargeIconSize = 48.dp
private val XXXS = 2.dp
private val XXS = 4.dp
private val XS = 8.dp
private val S = 12.dp
private val M = 16.dp
private val L = 24.dp
private val XL = 32.dp
private val XXL = 40.dp
private val XXXL = 64.dp

@Immutable
data class MovieCatalogSpacing(
    val default: Dp = DefaultSpace,
    val border: Dp = BorderStroke,
    val statusBarPadding: Dp = XXL,
    val iconSize: Dp = IconSize,
    val largeIconSize: Dp = LargeIconSize,

    val xxxSmall: Dp = XXXS,
    val xxSmall: Dp = XXS,
    val xSmall: Dp = XS,
    val small: Dp = S,
    val medium: Dp = M,
    val large: Dp = L,
    val xLarge: Dp = XL,
    val xxLarge: Dp = XXL,
    val xxxLarge: Dp = XXXL,
)

internal val LocalMovieCatalogSpacing = staticCompositionLocalOf { MovieCatalogSpacing() }
