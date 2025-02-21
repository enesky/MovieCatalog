package dev.enesky.moviecatalog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.enesky.moviecatalog.ui.theme.MovieCatalogTheme

/**
 * Created by Enes Kamil YILMAZ on 21/02/2025
 */

@Composable
fun MovieCatalogApp(modifier: Modifier = Modifier) {
    MovieCatalogTheme {
        Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(innerPadding),
                    text = "Welcome to Movie Catalog!"
                )
            }
        }
    }
}