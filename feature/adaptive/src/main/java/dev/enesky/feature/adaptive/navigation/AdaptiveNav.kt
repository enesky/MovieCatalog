package dev.enesky.feature.adaptive.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.enesky.core.ui.navigation.Screen
import dev.enesky.feature.adaptive.AdaptiveScreen
import dev.enesky.feature.adaptive.AdaptiveViewModel
import dev.enesky.feature.detail.DetailViewModel
import dev.enesky.feature.home.HomeViewModel
import kotlinx.serialization.Serializable

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */

@Serializable
data object Adaptive : Screen

fun NavGraphBuilder.adaptiveScreen(
    onNavigateToPlayerScreen: (Int) -> Unit = {}
) {
    composable<Adaptive> {
        val viewModel = hiltViewModel<AdaptiveViewModel>()
        val homeViewModel = hiltViewModel<HomeViewModel>()
        val detailViewModel = hiltViewModel<DetailViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
        val detailUiState by detailViewModel.uiState.collectAsStateWithLifecycle()
        AdaptiveScreen(
            uiState = uiState,
            homeUiState = homeUiState,
            detailUiState = detailUiState,
            eventFlow = viewModel.eventFlow,
            homeEventFlow = homeViewModel.eventFlow,
            detailEventFlow = detailViewModel.eventFlow,
            onHomeRefresh = { homeViewModel.getConfig() },
            onDetailRefresh = { detailViewModel.getMovieDetails() },
            onMovieClick = { detailViewModel.getMovieDetails(it) },
            onNavigateToPlayerScreen = onNavigateToPlayerScreen
        )
    }
}
