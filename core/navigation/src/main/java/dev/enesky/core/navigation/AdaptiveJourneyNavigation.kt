package dev.enesky.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import dev.enesky.core.ui.navigation.Screen
import dev.enesky.feature.adaptive.navigation.Adaptive
import dev.enesky.feature.adaptive.navigation.adaptiveScreen
import dev.enesky.feature.splash.navigation.Splash
import dev.enesky.feature.splash.navigation.splashScreen
import kotlinx.serialization.Serializable

/**
 * Created by Enes Kamil YILMAZ on 27/02/2025
 */

@Serializable
object AdaptiveJourney : Screen

internal fun NavGraphBuilder.adaptiveJourneyNavigation(navController: NavHostController) {
    navigation<MainJourney>(startDestination = Splash) {
        splashScreen(
            onNavigateToHome = {
                navController.onScreenNavigate(
                    destination = Adaptive,
                    inclusive = true,
                    popUpToScreen = Splash
                )
            }
        )
        adaptiveScreen()
    }
}