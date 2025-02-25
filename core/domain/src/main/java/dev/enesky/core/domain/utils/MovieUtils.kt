package dev.enesky.core.domain.utils

import dev.enesky.core.domain.model.Genre
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

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


/**
 * Formats date to month year format
 */
fun formatDateToMonthYear(dateString: String?): String {
    if (dateString.isNullOrEmpty()) return ""
    val inputFormat = "yyyy-MM-dd"
    val outputFormat = "MMM yyyy"
    val inputSimpleDateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
    val date = inputSimpleDateFormat.parse(dateString)
    val outputSimpleDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
    return date?.let { outputSimpleDateFormat.format(it) } ?: ""
}

/**
 * Formats minutes to a single string
 */
fun formatMinutes(minutes: Int): String {
    val currentLocale = Locale.getDefault().language
    val turkishLocale = "tr"
    val suffix = if (currentLocale == turkishLocale) "dk" else "min"
    return "$minutes $suffix"
}