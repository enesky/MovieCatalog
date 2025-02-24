package dev.enesky.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

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