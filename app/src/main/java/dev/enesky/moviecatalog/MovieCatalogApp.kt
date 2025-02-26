package dev.enesky.moviecatalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.enesky.core.navigation.MovieCatalogNavGraph
import dev.enesky.core.ui.components.BuildTypeIndicator
import dev.enesky.core.ui.theme.MovieCatalogTheme

/**
 * Created by Enes Kamil YILMAZ on 21/02/2025
 */
@Composable
fun MovieCatalogApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    MovieCatalogTheme {
        Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                MovieCatalogNavGraph(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                )

                if (BuildConfig.DEBUG) {
                    BuildTypeIndicator(text = "Debug")
                }
            }
        }
    }
}
