package dev.enesky.core.domain.utils

import dev.enesky.core.domain.model.Genre
import java.text.DecimalFormat

/**
 * Created by Enes Kamil YILMAZ on 25/02/2025
 */

/**
 * Formats genres to a single string
 *
 * Input: [Genre("Action"), Genre("Adventure"), Genre("Comedy")]
 * Output: "Action | Adventure | Comedy"
 */
fun formatGenres(genres: List<Genre>): String {
    val maxGenreCount = 3
    return genres.take(maxGenreCount).joinToString(separator = " | ") { it.name }
}

/**
 * Formats rating to a single string
 *
 * Input: 7.5213
 * Output: "7.5"
 */
fun formatRating(rating: Double): String {
    val ratingPattern = "#.#"
    val ratingFormat = DecimalFormat(ratingPattern)
    return ratingFormat.format(rating).toString()
}