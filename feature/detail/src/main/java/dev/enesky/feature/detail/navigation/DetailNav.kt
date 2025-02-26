package dev.enesky.feature.detail.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.enesky.core.ui.navigation.Screen
import dev.enesky.feature.detail.DetailScreen
import dev.enesky.feature.detail.DetailViewModel
import kotlinx.serialization.Serializable

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */

@Serializable
data class Detail(
    val movieId: Int
) : Screen

fun NavGraphBuilder.detailScreen(
    onNavigateToPlayer: (Int) -> Unit,
) {
    composable<Detail> {
        val viewModel = hiltViewModel<DetailViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        DetailScreen(
            uiState = uiState,
            eventFlow = viewModel.eventFlow,
            onRefresh = { viewModel.getMovieDetails() },
            onNavigateToPlayerScreen = onNavigateToPlayer
        )
    }
}
