package dev.enesky.core.domain.utils

import dev.enesky.core.domain.model.Genre
import io.mockk.clearAllMocks
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Enes Kamil YILMAZ on 27/02/2025
 */
class MovieUtilsTest {

    @Test
    fun formatGenres_whenGenresProvided_expectFormattedString() {
        // Given
        val genres = listOf(
            Genre(1, "Action"),
            Genre(2, "Adventure"),
            Genre(3, "Comedy")
        )
        val expectedResult = "Action | Adventure | Comedy"

        // When
        val result = formatGenres(genres)

        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun formatGenres_whenMoreThanThreeGenresProvided_expectOnlyFirstThree() {
        // Given
        val genres = listOf(
            Genre(1, "Action"),
            Genre(2, "Adventure"),
            Genre(3, "Comedy"),
            Genre(4, "Drama"),
            Genre(5, "Horror")
        )
        val expectedResult = "Action | Adventure | Comedy"

        // When
        val result = formatGenres(genres)

        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun formatGenres_whenEmptyList_expectEmptyString() {
        // Given
        val genres = emptyList<Genre>()
        val expectedResult = ""

        // When
        val result = formatGenres(genres)

        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun formatRating_whenRatingProvided_expectFormattedRating() {
        // Given
        val rating = 7.5213
        val expectedResult = "7.5"

        // When
        val result = formatRating(rating)

        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun formatRating_whenIntegerRating_expectFormattedRating() {
        // Given
        val rating = 8.0
        val expectedResult = "8"

        // When
        val result = formatRating(rating)

        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun formatDateToMonthYear_whenValidDateProvided_expectFormattedDate() {
        // Given
        val dateString = "2023-05-15"
        val expectedResult = "May 2023" // This will depend on locale, but for test we can assume US locale

        // When
        val result = formatDateToMonthYear(dateString)

        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun formatDateToMonthYear_whenNullDate_expectEmptyString() {
        // Given
        val dateString: String? = null
        val expectedResult = ""

        // When
        val result = formatDateToMonthYear(dateString)

        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun formatDateToMonthYear_whenEmptyDate_expectEmptyString() {
        // Given
        val dateString = ""
        val expectedResult = ""

        // When
        val result = formatDateToMonthYear(dateString)

        // Then
        assertEquals(expectedResult, result)
    }

    @Test
    fun formatMinutes_whenMinutesProvided_expectFormattedMinutes() {
        // Given
        val minutes = 120
        val expectedResult = "120 min" // Assuming non-Turkish locale

        // When
        val result = formatMinutes(minutes)

        // Then
        assertEquals(expectedResult, result)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}