package dev.enesky.core.domain.mapper

import dev.enesky.core.data.model.GenreDto
import dev.enesky.core.data.model.MovieDetailDto
import dev.enesky.core.data.model.ProductionCompanyDto
import dev.enesky.core.domain.constant.ImageConstants
import dev.enesky.core.domain.model.Genre
import dev.enesky.core.domain.model.ProductionCompany
import dev.enesky.core.domain.utils.formatDateToMonthYear
import dev.enesky.core.domain.utils.formatGenres
import dev.enesky.core.domain.utils.formatMinutes
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
class MovieDetailMapperTest {

    private lateinit var mapper: MovieDetailMapper

    private val mockGenres = listOf(
        GenreDto(id = 1, name = "Action"),
        GenreDto(id = 2, name = "Adventure")
    )

    private val mockProductionCompanies = listOf(
        ProductionCompanyDto(
            id = 1,
            name = "Test Studio",
            logoPath = "/logo.jpg",
            originCountry = "US"
        )
    )

    private val mockMovieDetailDto = MovieDetailDto(
        id = 1,
        title = "Test Movie",
        overview = "Test Overview",
        popularity = 100.0,
        adult = false,
        budget = 1000000,
        genres = mockGenres,
        homepage = "https://test-movie.com",
        revenue = 5000000,
        runtime = 120,
        status = "Released",
        tagline = "Test Tagline",
        video = false,
        backdropPath = "/backdrop.jpg",
        imdbId = "tt1234567",
        originalLanguage = "en",
        originalTitle = "Original Test Movie",
        posterPath = "/poster.jpg",
        releaseDate = "2023-05-15",
        voteAverage = 7.5,
        voteCount = 1000,
        productionCompanies = mockProductionCompanies
    )

    @Before
    fun setup() {
        // Global Given
        mapper = MovieDetailMapper()

        mockkObject(ImageConstants)
        mockkStatic(::formatDateToMonthYear)
        mockkStatic(::formatGenres)
        mockkStatic(::formatMinutes)
        mockkStatic(::formatRating)
    }

    @Test
    fun mapToDomain_whenValidMovieDetailDto_expectCorrectMovieDetailDomainModel() {
        // Given
        every { ImageConstants.getBackdropUrl(any()) } returns "https://image.tmdb.org/t/p/w500/backdrop.jpg"
        every { ImageConstants.getPosterUrl(any()) } returns "https://image.tmdb.org/t/p/w500/poster.jpg"
        every { ImageConstants.getLogoUrl(any()) } returns "https://image.tmdb.org/t/p/w500/logo.jpg"
        every { formatDateToMonthYear(any()) } returns "May 2023"
        every { formatGenres(any()) } returns "Action | Adventure"
        every { formatMinutes(any()) } returns "120 min"
        every { formatRating(any()) } returns "7.5"
        val expectedBackdropUrl = "https://image.tmdb.org/t/p/w500/backdrop.jpg"
        val expectedPosterUrl = "https://image.tmdb.org/t/p/w500/poster.jpg"
        val expectedLogoUrl = "https://image.tmdb.org/t/p/w500/logo.jpg"
        val expectedReleaseDate = "May 2023"
        val expectedGenreText = "Action | Adventure"
        val expectedRuntime = "120 min"
        val expectedRating = "7.5"

        // When
        val result = mapper.mapToDomain(mockMovieDetailDto)

        // Then
        assertEquals(mockMovieDetailDto.id, result.id)
        assertEquals(mockMovieDetailDto.title, result.title)
        assertEquals(mockMovieDetailDto.overview, result.overview)
        assertEquals(mockMovieDetailDto.adult, result.adult)
        assertEquals(mockMovieDetailDto.budget, result.budget)
        assertEquals(2, result.genres.size)
        assertEquals(1, result.genres[0].id)
        assertEquals("Action", result.genres[0].name)
        assertEquals(expectedGenreText, result.genreText)
        assertEquals(mockMovieDetailDto.homepage, result.homepage)
        assertEquals(mockMovieDetailDto.revenue, result.revenue)
        assertEquals(expectedRuntime, result.runtime)
        assertEquals(mockMovieDetailDto.status, result.status)
        assertEquals(mockMovieDetailDto.tagline, result.tagline)
        assertEquals(mockMovieDetailDto.video, result.video)
        assertEquals(expectedBackdropUrl, result.backdropUrl)
        assertEquals(mockMovieDetailDto.imdbId, result.imdbId)
        assertEquals(mockMovieDetailDto.originalLanguage, result.language)
        assertEquals(mockMovieDetailDto.originalTitle, result.originalTitle)
        assertEquals(expectedPosterUrl, result.posterUrl)
        assertEquals(expectedReleaseDate, result.releaseDate)
        assertEquals(expectedRating, result.rating)
        assertEquals(mockMovieDetailDto.voteCount, result.voteCount)
        assertEquals(1, result.productionCompanies.size)
        assertEquals(1, result.productionCompanies[0].id)
        assertEquals("Test Studio", result.productionCompanies[0].name)
        assertEquals(expectedLogoUrl, result.productionCompanies[0].logoUrl)
        assertEquals("US", result.productionCompanies[0].originCountry)
    }

    @Test
    fun mapToDomain_whenNullValues_expectDefaultValues() {
        // Given
        val mockMovieDetailDtoWithNulls = MovieDetailDto(
            id = null,
            title = null,
            overview = null,
            popularity = null,
            adult = null,
            budget = null,
            genres = null,
            homepage = null,
            revenue = null,
            runtime = null,
            status = null,
            tagline = null,
            video = null,
            backdropPath = null,
            imdbId = null,
            originalLanguage = null,
            originalTitle = null,
            posterPath = null,
            releaseDate = null,
            voteAverage = null,
            voteCount = null,
            productionCompanies = null
        )

        // When
        val result = mapper.mapToDomain(mockMovieDetailDtoWithNulls)

        // Then
        assertEquals(0, result.id)
        assertEquals("", result.title)
        assertEquals("", result.overview)
        assertEquals(0.0, result.popularity, 0.0)
        assertEquals(false, result.adult)
        assertEquals(0, result.budget)
        assertEquals(emptyList<Genre>(), result.genres)
        assertEquals("", result.genreText)
        assertEquals("", result.homepage)
        assertEquals(0, result.revenue)
        assertEquals("0 min", result.runtime)
        assertEquals("", result.status)
        assertEquals("", result.tagline)
        assertEquals(false, result.video)
        assertEquals(null, result.backdropUrl)
        assertEquals("", result.imdbId)
        assertEquals("", result.language)
        assertEquals("", result.originalTitle)
        assertEquals(null, result.posterUrl)
        assertEquals("", result.releaseDate)
        assertEquals("0", result.rating)
        assertEquals(0, result.voteCount)
        assertEquals(emptyList<ProductionCompany>(), result.productionCompanies)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}