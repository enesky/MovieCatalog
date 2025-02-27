package dev.enesky.core.domain.usecase

import dev.enesky.core.common.data.Resource
import dev.enesky.core.common.data.base.BaseException
import dev.enesky.core.data.model.MovieDetailDto
import dev.enesky.core.domain.mapper.MovieDetailMapper
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.network.repository.MovieRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Created by Enes Kamil YILMAZ on 27/02/2025
 */
class GetMovieDetailsUseCaseTest {

    private lateinit var useCase: GetMovieDetailsUseCase
    private lateinit var repository: MovieRepository
    private lateinit var mapper: MovieDetailMapper

    private val mockMovieDetailDto = mockk<MovieDetailDto>()
    private val mockMovieDetail = mockk<MovieDetail>()
    private val movieId = 1

    @Before
    fun setup() {
        repository = mockk()
        mapper = mockk()
        useCase = GetMovieDetailsUseCase(repository, mapper)

        every { mapper.mapToDomain(any()) } returns mockMovieDetail
    }

    @Test
    fun invoke_whenSuccess_expectResourceSuccess() = runBlocking {
        // Given
        coEvery { repository.getMovieDetails(movieId) } returns Resource.Success(mockMovieDetailDto)

        // When
        val result = useCase(movieId)

        // Then
        assertTrue(result is Resource.Success)
        assertEquals(mockMovieDetail, (result as Resource.Success).data)
        coVerify(exactly = 1) { repository.getMovieDetails(movieId) }
        coVerify(exactly = 1) { mapper.mapToDomain(mockMovieDetailDto) }
    }

    @Test
    fun invoke_whenError_expectResourceError() = runBlocking {
        // Given
        val errorMessage = "Error fetching movie details"
        coEvery { repository.getMovieDetails(movieId) } returns Resource.Error(BaseException(errorMessage))

        // When
        val result = useCase(movieId)

        // Then
        assertTrue(result is Resource.Error)
        coVerify(exactly = 1) { repository.getMovieDetails(movieId) }
        coVerify(exactly = 0) { mapper.mapToDomain(any()) }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}