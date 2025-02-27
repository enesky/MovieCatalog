package dev.enesky.feature.adaptive.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.enesky.core.ui.navigation.Screen
import dev.enesky.feature.adaptive.AdaptiveScreen
import dev.enesky.feature.adaptive.AdaptiveViewModel
import kotlinx.serialization.Serializable

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */

@Serializable
data object Adaptive : Screen

fun NavGraphBuilder.adaptiveScreen() {
    composable<Adaptive> {
        val viewModel = hiltViewModel<AdaptiveViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        AdaptiveScreen(
            uiState = uiState,
            eventFlow = viewModel.eventFlow
        )
    }
}
