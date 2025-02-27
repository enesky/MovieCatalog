package dev.enesky.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import dev.enesky.core.ui.R

private val NunitoSans = FontFamily(
    Font(R.font.nunito_sans_regular, FontWeight.Normal),
    Font(R.font.nunito_sans_bold, FontWeight.Bold),
)

internal val Typography = defaultTypography()

@Immutable
class MovieCatalogTypography {
    val regular = Regular()
    val bold = Bold()

    @Immutable
    data class Regular(
        val default: TextStyle = defaultTextStyle(),
        val h1: TextStyle = defaultTextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = H1FontSize,
            lineHeight = H1LineHeight,
        ),
        val h2: TextStyle = defaultTextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = H2FontSize,
            lineHeight = H2LineHeight,
        ),
        val h3: TextStyle = defaultTextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = H3FontSize,
            lineHeight = H3LineHeight,
        ),
        val h4: TextStyle = defaultTextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = H4FontSize,
            lineHeight = H4LineHeight,
        ),
        val h5: TextStyle = defaultTextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = H5FontSize,
            lineHeight = H5LineHeight,
        ),
        val h6: TextStyle = defaultTextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = H6FontSize,
            lineHeight = H6LineHeight,
        ),
        val h7: TextStyle = defaultTextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = H7FontSize,
            lineHeight = H7LineHeight,
        ),
    )

    @Immutable
    data class Bold(
        val default: TextStyle = defaultTextStyle(),
        val h1: TextStyle = defaultTextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = H1FontSize,
            lineHeight = H1LineHeight,
        ),
        val h2: TextStyle = defaultTextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = H2FontSize,
            lineHeight = H2LineHeight,
        ),
        val h3: TextStyle = defaultTextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = H3FontSize,
            lineHeight = H3LineHeight,
        ),
        val h4: TextStyle = defaultTextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = H4FontSize,
            lineHeight = H4LineHeight,
        ),
        val h5: TextStyle = defaultTextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = H5FontSize,
            lineHeight = H5LineHeight,
        ),
        val h6: TextStyle = defaultTextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = H6FontSize,
            lineHeight = H6LineHeight,
        ),
        val h7: TextStyle = defaultTextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = H7FontSize,
            lineHeight = H7LineHeight,
        ),
    )
}

private fun defaultTextStyle(
    fontWeight: FontWeight,
    fontSize: TextUnit,
    lineHeight: TextUnit,
    letterSpacing: TextUnit = LetterSpacing,
) = TextStyle(
    fontFamily = NunitoSans,
    fontWeight = fontWeight,
    fontSize = fontSize,
    lineHeight = lineHeight,
    letterSpacing = letterSpacing,
)

private fun defaultTextStyle(
    letterSpacing: TextUnit = LetterSpacing,
) = TextStyle(
    fontFamily = NunitoSans,
    letterSpacing = letterSpacing,
)

private fun defaultTypography() = with(MovieCatalogTypography()) {
    Typography(
        headlineLarge = regular.h1,
        headlineMedium = regular.h2,
        headlineSmall = regular.h3,
        titleLarge = regular.h4,
        titleMedium = regular.h5,
        titleSmall = regular.h6,
        labelLarge = regular.h5,
        labelMedium = regular.h6,
        labelSmall = regular.h7,
    )
}

internal val LocalMovieCatalogTypography = staticCompositionLocalOf { MovieCatalogTypography() }

// Font Sizing
private val LetterSpacing = (0.12f).sp

private val H1FontSize = 28.sp
private val H1LineHeight = (34.13f).sp

private val H2FontSize = 24.sp
private val H2LineHeight = (29.26f).sp

private val H3FontSize = 18.sp
private val H3LineHeight = (21.94f).sp

private val H4FontSize = 16.sp
private val H4LineHeight = (19.5f).sp

private val H5FontSize = 14.sp
private val H5LineHeight = (17.07f).sp

private val H6FontSize = 12.sp
private val H6LineHeight = (14.63f).sp

private val H7FontSize = 10.sp
private val H7LineHeight = (12.19f).sp
