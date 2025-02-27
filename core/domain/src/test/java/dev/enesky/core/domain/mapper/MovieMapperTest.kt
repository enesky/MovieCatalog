package dev.enesky.core.domain.mapper

import dev.enesky.core.data.model.MovieDto
import dev.enesky.core.domain.constant.ImageConstants
import dev.enesky.core.domain.utils.formatDateToMonthYear
import dev.enesky.core.domain.utils.formatRating
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.mockkStatic
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by Enes Kamil YILMAZ on 27/02/2025
 */
class MovieMapperTest {

    private lateinit var mapper: MovieMapper
    private val mockMovieDto = MovieDto(
        id = 1,
        title = "Test Movie",
        overview = "Test Overview",
        popularity = 100.0,
        backdropPath = "/backdrop.jpg",
        releaseDate = "2023-05-15",
        genreIds = listOf(1, 2, 3),
        originalLanguage = "en",
        originalTitle = "Original Test Movie",
        posterPath = "/poster.jpg",
        voteAverage = 7.5,
        voteCount = 1000,
        adult = false,
        video = false
    )

    @Before
    fun setup() {
        mapper = MovieMapper()

        mockkObject(ImageConstants)
        mockkStatic(::formatDateToMonthYear)
        mockkStatic(::formatRating)

        every { ImageConstants.getBackdropUrl(any()) } returns "https://image.tmdb.org/t/p/w500/backdrop.jpg"
        every { ImageConstants.getPosterUrl(any()) } returns "https://image.tmdb.org/t/p/w500/poster.jpg"
        every { formatDateToMonthYear(any()) } returns "May 2023"
        every { formatRating(any()) } returns "7.5"
    }

    @Test
    fun mapToDomain_whenValidMovieDto_expectCorrectMovieDomainModel() {
        // Given
        val expectedBackdropUrl = "https://image.tmdb.org/t/p/w500/backdrop.jpg"
        val expectedPosterUrl = "https://image.tmdb.org/t/p/w500/poster.jpg"
        val expectedReleaseDate = "May 2023"
        val expectedRating = "7.5"

        // When
        val result = mapper.mapToDomain(mockMovieDto)

        // Then
        assertEquals(mockMovieDto.id, result.id)
        assertEquals(mockMovieDto.title, result.title)
        assertEquals(mockMovieDto.overview, result.overview)
        assertEquals(mockMovieDto.popularity, result.popularity, 0.0)
        assertEquals(expectedBackdropUrl, result.backdropUrl)
        assertEquals(expectedReleaseDate, result.releaseDate)
        assertEquals(mockMovieDto.genreIds, result.genreIds)
        assertEquals(mockMovieDto.originalLanguage, result.language)
        assertEquals(mockMovieDto.originalTitle, result.originalTitle)
        assertEquals(expectedPosterUrl, result.posterUrl)
        assertEquals(expectedRating, result.rating)
        assertEquals(mockMovieDto.voteCount, result.voteCount)
        assertEquals(mockMovieDto.adult, result.adult)
        assertEquals(mockMovieDto.video, result.video)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}
