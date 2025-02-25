package dev.enesky.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val BlazeOrange = Color(0xFFF96D00) // Main color
private val EbonyClay = Color(0xFF2B323F) // Background color on dark mode
private val White = Color(0xFFFFFFFF) // Text color on dark background
private val Black = Color(0xFF171725) // Text color on light background
private val Geyser = Color(0xFFDFE6E9) // Background color on light mode
private val SoothingBreeze = Color(0xFFB2BEC3) // Second text or second background color
private val Dark = Color(0xFF1F1D2B)
private val Soft = Color(0xFF252836)
private val Green = Color(0xFF22B07D)
private val Orange = Color(0xFFFF8700)
private val Red = Color(0xFFFB4141)
private val Grey = Color(0xFF92929D)
private val DarkGrey = Color(0xFF696974)
private val WhiteGrey = Color(0xFFEBEBEF)
private val LineDark = Color(0xFFEAEAEA)

internal val DarkColorScheme = darkColorScheme(
    primary = BlazeOrange,
    onPrimary = White,
    secondary = Orange,
    onSecondary = White,
    background = EbonyClay,
    onBackground = White,
    surface = SoothingBreeze,
    onSurface = White,
    surfaceVariant = Soft,
    onSurfaceVariant = Grey,
    error = Red,
    onError = White,
    outline = Grey
)

internal val LightColorScheme = lightColorScheme(
    primary = BlazeOrange,
    onPrimary = White,
    secondary = Orange,
    onSecondary = White,
    background = Geyser,
    onBackground = Black,
    surface = SoothingBreeze,
    onSurface = Black,
    surfaceVariant = WhiteGrey,
    onSurfaceVariant = Grey,
    error = Red,
    onError = White,
    outline = Grey
)

@Immutable
data class MovieCatalogColors(
    val isSystemInDarkTheme: Boolean = false,
    val default: Color = Color.Unspecified,
    val transparent: Color = Color.Transparent,
    val main: Color = BlazeOrange,
    val background: Color = if (isSystemInDarkTheme) EbonyClay else SoothingBreeze,
    val backgroundDark: Color = EbonyClay,
    val backgroundLight: Color = Geyser,
    val text: Color = if (isSystemInDarkTheme) White else Black,
    val textDark: Color = White,
    val textLight: Color = Black,
    val secondary: Color = SoothingBreeze,
    val dark: Color = Dark,
    val softDark: Color = Soft,
    val green: Color = Green,
    val lightOrange: Color = Orange,
    val red: Color = Red,
    val white: Color = White,
    val whiteGrey: Color = WhiteGrey,
    val black: Color = Black,
    val grey: Color = Grey,
    val darkGrey: Color = DarkGrey,
    val lineDark: Color = LineDark,
)

internal val LocalMovieCatalogColors = staticCompositionLocalOf { MovieCatalogColors() }
