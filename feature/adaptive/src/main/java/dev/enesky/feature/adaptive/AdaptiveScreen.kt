package dev.enesky.feature.adaptive

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.feature.detail.DetailScreen
import dev.enesky.feature.detail.DetailViewModel
import dev.enesky.feature.home.HomeScreen
import dev.enesky.feature.home.HomeViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */
@Composable
fun AdaptiveScreen(
    modifier: Modifier = Modifier,
    uiState: AdaptiveUiState = AdaptiveUiState(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    detailViewModel: DetailViewModel = hiltViewModel(),
    eventFlow: Flow<AdaptiveEvent> = emptyFlow(),
) {
    val detailUiState by detailViewModel.uiState.collectAsStateWithLifecycle()
    val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(eventFlow) { adaptiveEvent ->
        when (adaptiveEvent) {
            is AdaptiveEvent.OnError -> { /* Handle error */ }
        }
    }

    AdaptiveContent(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        homeContent = {
            HomeScreen(
                uiState = homeUiState,
                eventFlow = homeViewModel.eventFlow,
                onRefresh = { homeViewModel.getConfig() }
            )
        },
        detailContent = {
            DetailScreen(
                uiState = detailUiState,
                eventFlow = detailViewModel.eventFlow,
                onRefresh = { detailViewModel.getMovieDetails() },
            )
        }
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveContent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail? = null,
    onMovieClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
    homeContent: @Composable () -> Unit = {},
    detailContent: @Composable () -> Unit = {},
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator(
        scaffoldDirective = calculatePaneScaffoldDirective(windowAdaptiveInfo),
        initialDestinationHistory = listOfNotNull(
            ThreePaneScaffoldDestinationItem(ListDetailPaneScaffoldRole.List),
            ThreePaneScaffoldDestinationItem<Nothing>(ListDetailPaneScaffoldRole.Detail),
        ),
    )
    BackHandler(listDetailNavigator.canNavigateBack()) {
        onBackClick()
    }

    val homeScreenWidth = calculateHomeScreenWidth()
    ListDetailPaneScaffold(
        value = listDetailNavigator.scaffoldValue,
        directive = listDetailNavigator.scaffoldDirective,
        listPane = {
            AnimatedPane(modifier = Modifier.preferredWidth(homeScreenWidth)) {
                homeContent()
            }
        },
        detailPane = {
            AnimatedPane(modifier = Modifier) {
                detailContent()
            }
        },
    )

}

@Composable
private fun calculateHomeScreenWidth(): Dp {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    return screenWidth * (0.6f)
}
