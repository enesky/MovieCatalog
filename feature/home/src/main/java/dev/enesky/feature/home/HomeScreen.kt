package dev.enesky.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.enesky.core.ui.theme.MovieCatalogTheme

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit = {},
) {
    // HomeRoute implementation
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState = HomeUiState(),
    onMovieClick: (Int) -> Unit = {},
) {
    // HomeContent implementation
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MovieCatalogTheme {
        HomeContent {}
    }
}
