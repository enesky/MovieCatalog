package dev.enesky.core.network.paging

import androidx.paging.PagingSource
import dev.enesky.core.data.model.MovieDto
import dev.enesky.core.data.model.MoviePagingDto
import dev.enesky.core.network.api.MovieDbApi
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Enes Kamil YILMAZ on 27/02/2025
 */
@ExperimentalCoroutinesApi
class UpcomingMoviesPagingSourceTest {

    private lateinit var pagingSource: UpcomingMoviesPagingSource
    private lateinit var moviesService: MovieDbApi

    @Before
    fun setup() {
        moviesService = mockk()
        pagingSource = UpcomingMoviesPagingSource(moviesService)
    }

    @Test
    fun load_whenFirstPageSuccess_expectCorrectPageData() = runTest {
        // Given
        val movieList = listOf(
            MovieDto(
                id = 1,
                title = "Upcoming Movie",
                overview = "Future release overview",
                popularity = 120.0,
                backdropPath = "/backdrop.jpg",
                releaseDate = "2025-05-15",
                genreIds = listOf(1, 2, 3),
                originalLanguage = "en",
                originalTitle = "Original Upcoming Movie",
                posterPath = "/poster.jpg",
                voteAverage = 0.0,
                voteCount = 0,
                adult = false,
                video = false
            )
        )

        val page = 1
        val response = MoviePagingDto(
            page = page,
            results = movieList,
            totalPages = 10,
            totalResults = 100
        )

        coEvery { moviesService.getUpcomingMovies(page) } returns response

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = page,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        // Then
        assertEquals(
            PagingSource.LoadResult.Page(
                data = movieList,
                prevKey = null,
                nextKey = 2
            ),
            result
        )
    }

    @Test
    fun load_whenNextPageSuccess_expectCorrectPageData() = runTest {
        // Given
        val movieList = listOf(
            MovieDto(
                id = 2,
                title = "Upcoming Movie 2",
                overview = "Another future release",
                popularity = 110.0,
                backdropPath = "/backdrop2.jpg",
                releaseDate = "2025-06-15",
                genreIds = listOf(2, 3, 4),
                originalLanguage = "en",
                originalTitle = "Original Upcoming Movie 2",
                posterPath = "/poster2.jpg",
                voteAverage = 0.0,
                voteCount = 0,
                adult = false,
                video = false
            )
        )

        val page = 2
        val response = MoviePagingDto(
            page = page,
            results = movieList,
            totalPages = 10,
            totalResults = 100
        )

        coEvery { moviesService.getUpcomingMovies(page) } returns response

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = page,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        // Then
        assertEquals(
            PagingSource.LoadResult.Page(
                data = movieList,
                prevKey = 1,
                nextKey = 3
            ),
            result
        )
    }

    @Test
    fun load_whenEmptyResponse_expectNullNextKey() = runTest {
        // Given
        val emptyList = emptyList<MovieDto>()
        val page = 1
        val response = MoviePagingDto(
            page = page,
            results = emptyList,
            totalPages = 0,
            totalResults = 0
        )

        coEvery { moviesService.getUpcomingMovies(page) } returns response

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = page,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        // Then
        assertEquals(
            PagingSource.LoadResult.Page(
                data = emptyList,
                prevKey = null,
                nextKey = null
            ),
            result
        )
    }

    @Test
    fun load_whenIOException_expectErrorResult() = runTest {
        // Given
        val exception = IOException("Network error")
        val page = 1

        coEvery { moviesService.getUpcomingMovies(page) } throws exception

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = page,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        // Then
        assertEquals(
            PagingSource.LoadResult.Error<Int, MovieDto>(exception),
            result
        )
    }

    @Test
    fun load_whenHttpException_expectErrorResult() = runTest {
        // Given
        val exception = mockk<HttpException>()
        val page = 1

        coEvery { moviesService.getUpcomingMovies(page) } throws exception

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = page,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        // Then
        assertEquals(
            PagingSource.LoadResult.Error<Int, MovieDto>(exception),
            result
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}
