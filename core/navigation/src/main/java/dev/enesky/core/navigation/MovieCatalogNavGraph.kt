package dev.enesky.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import dev.enesky.core.ui.navigation.Screen

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */
@Composable
fun MovieCatalogNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = MainJourney,
        modifier = modifier,
    ) {
        mainJourneyNavigation(navController)
    }
}

fun NavHostController.onScreenNavigate(destination: Screen) {
    val navOptions = navOptions {
        // Avoid multiple copies of the same destination when reselecting the same item.
        launchSingleTop = true
        // Restore state when reselecting a previously selected item.
        restoreState = true

        // Animate the transition when navigating to a new destination.
        anim {
            enter = R.anim.from_left
            exit = R.anim.to_right
            popEnter = R.anim.from_left
            popExit = R.anim.to_right
        }
    }

    navigate(destination, navOptions)
}