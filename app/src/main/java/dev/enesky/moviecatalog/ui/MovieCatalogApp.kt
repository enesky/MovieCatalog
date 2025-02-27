package dev.enesky.moviecatalog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.core.navigation.MovieCatalogNavGraph
import dev.enesky.core.ui.components.BuildTypeIndicator
import dev.enesky.core.ui.components.MovieDialog
import dev.enesky.core.ui.theme.MovieCatalogTheme
import dev.enesky.moviecatalog.BuildConfig
import dev.enesky.moviecatalog.R
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 21/02/2025
 */
@Composable
fun MovieCatalogApp(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
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

                CheckConnectivity(viewModel.eventFlow)

                if (BuildConfig.DEBUG) {
                    BuildTypeIndicator(text = "Debug")
                }
            }
        }
    }
}

@Composable
private fun CheckConnectivity(eventFlow: Flow<MainEvent>) {
    val showDialog = remember { mutableStateOf(false) }
    ObserveAsEvents(eventFlow) { mainEvent ->
        when (mainEvent) {
            is MainEvent.OnError -> {}
            is MainEvent.OnNoNetworkDialog -> {
                showDialog.value = !mainEvent.isOnline
            }
        }
    }
    if (showDialog.value) {
        MovieDialog(
            showDialog = showDialog.value,
            onDismissRequest = { showDialog.value = false },
            onConfirmAction = { /* Confirm action */ },
            title = stringResource(R.string.label_no_connection),
            message = stringResource(R.string.label_no_connection_try_again),
            icon = {
                Icon(
                    modifier = Modifier.size(MovieCatalogTheme.spacing.largeIconSize),
                    imageVector = Icons.Default.Warning,
                    tint = MovieCatalogTheme.colors.red,
                    contentDescription = "Info icon",
                )
            }
        )
    }
}
