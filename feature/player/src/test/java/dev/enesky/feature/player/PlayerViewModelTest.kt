package dev.enesky.feature.player

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import dev.enesky.core.common.data.Resource
import dev.enesky.core.common.data.base.BaseException
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.domain.usecase.GetMovieDetailsUseCase
import dev.enesky.feature.player.navigation.Player
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import androidx.navigation.toRoute

/**
 * Created by Enes Kamil YILMAZ on 27/02/2025
 */
@ExperimentalCoroutinesApi
class PlayerViewModelTest {

    private lateinit var viewModel: PlayerViewModel
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var playerRoute: Player

    private val testDispatcher = StandardTestDispatcher()
    private val mockMovieDetail = mockk<MovieDetail>()
    private val movieId = 123

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Mock Route and SavedStateHandle
        playerRoute = mockk<Player>()
        every { playerRoute.movieId } returns movieId

        savedStateHandle = mockk()

        // Mock toRoute extension function
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<Player>() } returns playerRoute

        getMovieDetailsUseCase = mockk()
    }

    @Test
    fun init_shouldCallGetMovieDetails() = runTest {
        // Given
        coEvery { getMovieDetailsUseCase.invoke(movieId) } returns Resource.Success(mockMovieDetail)

        // When
        viewModel = PlayerViewModel(getMovieDetailsUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { getMovieDetailsUseCase.invoke(movieId) }
    }

    @Test
    fun getMovieDetails_whenSuccess_expectCorrectUiState() = runTest {
        // Given
        coEvery { getMovieDetailsUseCase.invoke(movieId) } returns Resource.Success(mockMovieDetail)

        // When
        viewModel = PlayerViewModel(getMovieDetailsUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val finalState = awaitItem()
            assertFalse(finalState.isLoading)
            assertEquals(mockMovieDetail, finalState.movieDetail)
            assertNull(finalState.errorMessage)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun getMovieDetails_whenError_expectCorrectUiState() = runTest {
        // Given
        val errorMessage = "Failed to get movie details"
        val exception = mockk<BaseException>()
        every { exception.message } returns errorMessage
        coEvery { getMovieDetailsUseCase.invoke(movieId) } returns Resource.Error(exception)

        // When
        viewModel = PlayerViewModel(getMovieDetailsUseCase, savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val finalState = awaitItem()
            assertNull(finalState.movieDetail)

            cancelAndConsumeRemainingEvents()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }
}
