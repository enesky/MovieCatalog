package dev.enesky.core.domain.usecase

import androidx.paging.PagingData
import app.cash.turbine.test
import dev.enesky.core.data.model.MovieDto
import dev.enesky.core.domain.constant.MovieCategory
import dev.enesky.core.domain.mapper.MovieMapper
import dev.enesky.core.network.repository.MovieRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by Enes Kamil YILMAZ on 27/02/2025
 */
class GetCategorizedMoviesUseCaseTest {

    private lateinit var useCase: GetCategorizedMoviesUseCase
    private lateinit var repository: MovieRepository
    private lateinit var mapper: MovieMapper

    // Mock a flow with empty PagingData
    private val mockFlow = flowOf(PagingData.empty<MovieDto>())

    @Before
    fun setup() {
        repository = mockk()
        mapper = mockk()
        useCase = GetCategorizedMoviesUseCase(repository, mapper)

        // Setup repository mock responses
        every { repository.getNowPlayingMoviesPagingData() } returns mockFlow
        every { repository.getPopularMoviesPagingData() } returns mockFlow
        every { repository.getTopRatedMoviesPagingData() } returns mockFlow
        every { repository.getUpcomingMoviesPagingData() } returns mockFlow

        // Setup mapper mock
        every { mapper.mapToDomain(any()) } returns mockk()
    }

    @Test
    fun invoke_whenNowPlayingCategory_expectCorrectRepositoryMethodCalled() = runTest {
        // Given
        val category = MovieCategory.NOW_PLAYING

        // When & Then
        useCase(category).test {
            awaitItem() // Consume the PagingData
            cancelAndConsumeRemainingEvents()

            // Verify repository method calls
            verify(exactly = 1) { repository.getNowPlayingMoviesPagingData() }
            verify(exactly = 0) { repository.getPopularMoviesPagingData() }
            verify(exactly = 0) { repository.getTopRatedMoviesPagingData() }
            verify(exactly = 0) { repository.getUpcomingMoviesPagingData() }
        }
    }

    @Test
    fun invoke_whenPopularCategory_expectCorrectRepositoryMethodCalled() = runTest {
        // Given
        val category = MovieCategory.POPULAR

        // When & Then
        useCase(category).test {
            awaitItem() // Consume the PagingData
            cancelAndConsumeRemainingEvents()

            // Verify repository method calls
            verify(exactly = 0) { repository.getNowPlayingMoviesPagingData() }
            verify(exactly = 1) { repository.getPopularMoviesPagingData() }
            verify(exactly = 0) { repository.getTopRatedMoviesPagingData() }
            verify(exactly = 0) { repository.getUpcomingMoviesPagingData() }
        }
    }

    @Test
    fun invoke_whenTopRatedCategory_expectCorrectRepositoryMethodCalled() = runTest {
        // Given
        val category = MovieCategory.TOP_RATED

        // When & Then
        useCase(category).test {
            awaitItem() // Consume the PagingData
            cancelAndConsumeRemainingEvents()

            // Verify repository method calls
            verify(exactly = 0) { repository.getNowPlayingMoviesPagingData() }
            verify(exactly = 0) { repository.getPopularMoviesPagingData() }
            verify(exactly = 1) { repository.getTopRatedMoviesPagingData() }
            verify(exactly = 0) { repository.getUpcomingMoviesPagingData() }
        }
    }

    @Test
    fun invoke_whenUpcomingCategory_expectCorrectRepositoryMethodCalled() = runTest {
        // Given
        val category = MovieCategory.UPCOMING

        // When & Then
        useCase(category).test {
            awaitItem() // Consume the PagingData
            cancelAndConsumeRemainingEvents()

            // Verify repository method calls
            verify(exactly = 0) { repository.getNowPlayingMoviesPagingData() }
            verify(exactly = 0) { repository.getPopularMoviesPagingData() }
            verify(exactly = 0) { repository.getTopRatedMoviesPagingData() }
            verify(exactly = 1) { repository.getUpcomingMoviesPagingData() }
        }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}
