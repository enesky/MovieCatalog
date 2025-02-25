package dev.enesky.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import dev.enesky.core.ui.navigation.Screen
import dev.enesky.feature.detail.navigation.Detail
import dev.enesky.feature.detail.navigation.detailScreen
import dev.enesky.feature.home.navigation.Home
import dev.enesky.feature.home.navigation.homeScreen
import dev.enesky.feature.player.navigation.Player
import dev.enesky.feature.player.navigation.playerScreen
import kotlinx.serialization.Serializable

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */

@Serializable
object MainJourney : Screen

internal fun NavGraphBuilder.mainJourneyNavigation(navController: NavHostController) {
    navigation<MainJourney>(startDestination = Home) {
        homeScreen(
            onNavigateToDetail = { movieId ->
                navController.onScreenNavigate(Detail(movieId))
            }
        )
        detailScreen(
            onNavigateToPlayer = { movieId ->
                navController.onScreenNavigate(Player(movieId))
            }
        )
        playerScreen()
    }
}
