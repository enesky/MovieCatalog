package dev.enesky.feature.home.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.enesky.core.ui.navigation.Screen
import dev.enesky.feature.home.HomeScreen
import dev.enesky.feature.home.HomeViewModel
import kotlinx.serialization.Serializable

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */

@Serializable
data object Home : Screen

fun NavGraphBuilder.homeScreen(
    onNavigateToDetail: (Int) -> Unit,
) {
    composable<Home> {
        val viewModel = hiltViewModel<HomeViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        HomeScreen(
            uiState = uiState,
            eventFlow = viewModel.eventFlow,
            onNavigateToDetail = onNavigateToDetail
        )
    }
}
