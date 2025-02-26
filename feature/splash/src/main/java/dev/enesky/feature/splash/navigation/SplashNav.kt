package dev.enesky.feature.splash.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.enesky.core.ui.navigation.Screen
import dev.enesky.feature.splash.SplashScreen
import dev.enesky.feature.splash.SplashViewModel
import kotlinx.serialization.Serializable

/**
 * Created by Enes Kamil YILMAZ on 26/02/2025
 */

@Serializable
data object Splash : Screen

fun NavGraphBuilder.splashScreen(
    onNavigateToHome: () -> Unit,
) {
    composable<Splash> {
        val viewModel = hiltViewModel<SplashViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        SplashScreen(
            uiState = uiState,
            eventFlow = viewModel.eventFlow,
            onNavigateToHomeScreen = onNavigateToHome,
        )
    }
}
