package dev.enesky.moviecatalog

import app.cash.turbine.test
import dev.enesky.core.common.connectivity.ConnectivityListener
import dev.enesky.moviecatalog.ui.MainEvent
import dev.enesky.moviecatalog.ui.MainViewModel
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var connectivityListener: ConnectivityListener

    private val testDispatcher = StandardTestDispatcher()
    private val networkStatusFlow = MutableStateFlow(true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        connectivityListener = mockk()
        every { connectivityListener.isNetworkAvailable } returns networkStatusFlow
    }

    @Test
    fun init_shouldListenConnectivity() = runTest {
        // When
        viewModel = MainViewModel(connectivityListener)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify(exactly = 1) { connectivityListener.isNetworkAvailable }
    }

    @Test
    fun listenConnectivity_whenOnline_triggerOnlineEvent() = runTest {
        // Given
        networkStatusFlow.value = true

        // When
        viewModel = MainViewModel(connectivityListener)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.eventFlow.test {
            val event = awaitItem()
            assert(event is MainEvent.OnNoNetworkDialog)
            assertEquals(true, (event as MainEvent.OnNoNetworkDialog).isOnline)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun listenConnectivity_whenOffline_triggerOfflineEvent() = runTest {
        // Given
        networkStatusFlow.value = false

        // When
        viewModel = MainViewModel(connectivityListener)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.eventFlow.test {
            val event = awaitItem()
            assert(event is MainEvent.OnNoNetworkDialog)
            assertEquals(false, (event as MainEvent.OnNoNetworkDialog).isOnline)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun listenConnectivity_whenNetworkChanges_triggerMultipleEvents() = runTest {
        // Given
        networkStatusFlow.value = true

        // When
        viewModel = MainViewModel(connectivityListener)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - first event (online)
        viewModel.eventFlow.test {
            val firstEvent = awaitItem()
            assert(firstEvent is MainEvent.OnNoNetworkDialog)
            assertEquals(true, (firstEvent as MainEvent.OnNoNetworkDialog).isOnline)

            // When - network changes to offline
            networkStatusFlow.value = false
            testDispatcher.scheduler.advanceUntilIdle()

            // Then - second event (offline)
            val secondEvent = awaitItem()
            assert(secondEvent is MainEvent.OnNoNetworkDialog)
            assertEquals(false, (secondEvent as MainEvent.OnNoNetworkDialog).isOnline)

            // When - network changes back to online
            networkStatusFlow.value = true
            testDispatcher.scheduler.advanceUntilIdle()

            // Then - third event (online again)
            val thirdEvent = awaitItem()
            assert(thirdEvent is MainEvent.OnNoNetworkDialog)
            assertEquals(true, (thirdEvent as MainEvent.OnNoNetworkDialog).isOnline)

            cancelAndConsumeRemainingEvents()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }
}
