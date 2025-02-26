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
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = MainJourney,
        modifier = modifier,
    ) {
        mainJourneyNavigation(navController)
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
