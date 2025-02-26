package dev.enesky.feature.splash.navigation

import androidx.hilt.navigation.compose.hiltViewModel
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
        SplashScreen(
            eventFlow = viewModel.eventFlow,
            onNavigateToHomeScreen = onNavigateToHome,
        )
    }
}
