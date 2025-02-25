package dev.enesky.feature.player.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.enesky.core.ui.navigation.Screen
import dev.enesky.feature.player.PlayerScreen
import dev.enesky.feature.player.PlayerViewModel
import kotlinx.serialization.Serializable

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */

@Serializable
data class Player(
    val movieId: Int
) : Screen

fun NavGraphBuilder.playerScreen() {
    composable<Player> {
        val viewModel = hiltViewModel<PlayerViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        PlayerScreen(
            uiState = uiState,
            eventFlow = viewModel.eventFlow
        )
    }
}
