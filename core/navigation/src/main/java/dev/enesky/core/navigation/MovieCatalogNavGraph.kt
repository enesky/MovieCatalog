package dev.enesky.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.booleanResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import dev.enesky.core.ui.navigation.Screen

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */

@Composable
fun MovieCatalogNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val isTablet = booleanResource(id = dev.enesky.core.ui.R.bool.isTablet)
    val startDest = if (isTablet) AdaptiveJourney else MainJourney
    NavHost(
        navController = navController,
        startDestination = startDest,
        modifier = modifier,
    ) {
        when (isTablet) {
            true -> adaptiveJourneyNavigation(navController)
            false -> mainJourneyNavigation(navController)
        }
    }
}

fun NavHostController.onScreenNavigate(
    destination: Screen,
    inclusive: Boolean = false,
    popUpToScreen: Screen? = null,
) {
    val navOptions = navOptions {
        restoreState = true
        popUpToScreen?.let {
            popUpTo(it) {
                this.inclusive = inclusive
            }
        }

        anim {
            enter = R.anim.from_left
            exit = R.anim.to_right
            popEnter = R.anim.from_left
            popExit = R.anim.to_right
        }
    }

    navigate(destination, navOptions)
}
