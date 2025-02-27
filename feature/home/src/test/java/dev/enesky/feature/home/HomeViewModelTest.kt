package dev.enesky.feature.home

import androidx.paging.PagingData
import app.cash.turbine.test
import dev.enesky.core.common.data.Resource
import dev.enesky.core.common.data.base.BaseException
import dev.enesky.core.common.remoteconfig.FetchStatus
import dev.enesky.core.common.remoteconfig.RemoteConfigManager
import dev.enesky.core.domain.constant.MovieCategory
import dev.enesky.core.domain.model.Movie
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.domain.usecase.GetCategorizedMoviesUseCase
import dev.enesky.core.domain.usecase.GetMovieDetailsUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

/**
 * Created by Enes Kamil YILMAZ on 27/02/2025
 */
@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var getCategorizedMoviesUseCase: GetCategorizedMoviesUseCase
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    private val testDispatcher = StandardTestDispatcher()
    private val mockMovieDetail = mockk<MovieDetail>()
    private val mockPagingDataFlow = flowOf(PagingData.empty<Movie>())
    private val configStatusFlow = MutableStateFlow(FetchStatus.LOADING)
    private val testPreviewMovieId = 123L

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        getCategorizedMoviesUseCase = mockk()
        getMovieDetailsUseCase = mockk()

        // Mock RemoteConfigManager
        mockkObject(RemoteConfigManager)
        every { RemoteConfigManager.configStatus } returns configStatusFlow
        every { RemoteConfigManager.Values.previewMovieId } returns testPreviewMovieId

        // Setup default use case mocks
        setupDefaultUseCaseMocks()
    }

    private fun setupDefaultUseCaseMocks() {
        // Mock GetCategorizedMoviesUseCase
        every { getCategorizedMoviesUseCase.invoke(MovieCategory.NOW_PLAYING) } returns mockPagingDataFlow
        every { getCategorizedMoviesUseCase.invoke(MovieCategory.POPULAR) } returns mockPagingDataFlow
        every { getCategorizedMoviesUseCase.invoke(MovieCategory.TOP_RATED) } returns mockPagingDataFlow
        every { getCategorizedMoviesUseCase.invoke(MovieCategory.UPCOMING) } returns mockPagingDataFlow

        // Mock GetMovieDetailsUseCase
        coEvery { getMovieDetailsUseCase.invoke(any()) } returns Resource.Success(mockMovieDetail)
    }

    @Test
    fun init_shouldCallGetConfigAndGetMovies() = runTest {
        // When
        viewModel = HomeViewModel(getCategorizedMoviesUseCase, getMovieDetailsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify(exactly = 1) { getCategorizedMoviesUseCase.invoke(MovieCategory.NOW_PLAYING) }
        verify(exactly = 1) { getCategorizedMoviesUseCase.invoke(MovieCategory.POPULAR) }
        verify(exactly = 1) { getCategorizedMoviesUseCase.invoke(MovieCategory.TOP_RATED) }
        verify(exactly = 1) { getCategorizedMoviesUseCase.invoke(MovieCategory.UPCOMING) }
    }

    @Test
    fun getConfig_whenSuccess_expectCorrectUiState() = runTest {
        // Given
        viewModel = HomeViewModel(getCategorizedMoviesUseCase, getMovieDetailsUseCase)

        // When
        configStatusFlow.value = FetchStatus.SUCCESS
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.isConfigLoaded)
            assertEquals(testPreviewMovieId, state.previewMovieId)
            assertEquals(mockMovieDetail, state.previewMovieDetail)
            cancelAndConsumeRemainingEvents()
        }

        coVerify(exactly = 1) { getMovieDetailsUseCase.invoke(testPreviewMovieId.toInt()) }
    }

    @Test
    fun getConfig_whenError_expectCorrectUiState() = runTest {
        // Given
        viewModel = HomeViewModel(getCategorizedMoviesUseCase, getMovieDetailsUseCase)

        // When
        configStatusFlow.value = FetchStatus.ERROR
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { getMovieDetailsUseCase.invoke(testPreviewMovieId.toInt()) }
    }

    @Test
    fun getConfig_whenLoading_expectCorrectUiState() = runTest {
        // Given
        viewModel = HomeViewModel(getCategorizedMoviesUseCase, getMovieDetailsUseCase)

        // When - configStatusFlow is already set to LOADING in setup
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isConfigLoaded)
            cancelAndConsumeRemainingEvents()
        }

        coVerify(exactly = 0) { getMovieDetailsUseCase.invoke(any()) }
    }

    @Test
    fun getMovieDetails_whenSuccess_expectCorrectUiState() = runTest {
        // Given
        coEvery { getMovieDetailsUseCase.invoke(testPreviewMovieId.toInt()) } returns Resource.Success(mockMovieDetail)
        viewModel = HomeViewModel(getCategorizedMoviesUseCase, getMovieDetailsUseCase)

        // When
        configStatusFlow.value = FetchStatus.SUCCESS
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(mockMovieDetail, state.previewMovieDetail)
            assertNull(state.errorMessage)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun getMovieDetails_whenError_expectCorrectUiState() = runTest {
        // Given
        val errorMessage = "Failed to get movie details"
        coEvery { getMovieDetailsUseCase.invoke(testPreviewMovieId.toInt()) } returns Resource.Error(
            BaseException(errorMessage)
        )
        viewModel = HomeViewModel(getCategorizedMoviesUseCase, getMovieDetailsUseCase)

        // When
        configStatusFlow.value = FetchStatus.SUCCESS
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertNull(state.previewMovieDetail)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Ignore("Needs to be fixed")
    @Test
    fun getMovies_expectCorrectUiState() = runTest {
        // When
        viewModel = HomeViewModel(getCategorizedMoviesUseCase, getMovieDetailsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertNotNull(state.nowPlayingMovies)
            assertNotNull(state.popularMovies)
            assertNotNull(state.topRatedMovies)
            assertNotNull(state.upcomingMovies)
            cancelAndConsumeRemainingEvents()
        }

        verify(exactly = 1) {
            getCategorizedMoviesUseCase.invoke(MovieCategory.NOW_PLAYING)
            getCategorizedMoviesUseCase.invoke(MovieCategory.POPULAR)
            getCategorizedMoviesUseCase.invoke(MovieCategory.TOP_RATED)
            getCategorizedMoviesUseCase.invoke(MovieCategory.UPCOMING)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
        unmockkAll()
    }
}
