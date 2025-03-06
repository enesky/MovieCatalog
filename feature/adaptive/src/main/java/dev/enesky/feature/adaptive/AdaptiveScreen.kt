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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.feature.detail.DetailEvent
import dev.enesky.feature.detail.DetailScreen
import dev.enesky.feature.detail.DetailUiState
import dev.enesky.feature.home.HomeEvent
import dev.enesky.feature.home.HomeScreen
import dev.enesky.feature.home.HomeUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * Created by Enes Kamil YILMAZ on 24/02/2025
 */
@Composable
fun AdaptiveScreen(
    homeUiState: HomeUiState,
    detailUiState: DetailUiState,
    modifier: Modifier = Modifier,
    uiState: AdaptiveUiState = AdaptiveUiState(),
    eventFlow: Flow<AdaptiveEvent> = emptyFlow(),
    homeEventFlow: Flow<HomeEvent> = emptyFlow(),
    detailEventFlow: Flow<DetailEvent> = emptyFlow(),
    onHomeRefresh: () -> Unit = {},
    onDetailRefresh: () -> Unit = {},
    onMovieClick: (Int) -> Unit = {},
    onNavigateToPlayerScreen: (Int) -> Unit = {},
) {
    ObserveAsEvents(eventFlow) { adaptiveEvent ->
        when (adaptiveEvent) {
            is AdaptiveEvent.OnError -> { /* Handle error */ }
        }
    }
    ObserveAsEvents(homeEventFlow) { homeEvent ->
        when (homeEvent) {
            is HomeEvent.OnError -> { /* Handle error */ }
            is HomeEvent.OnMovieClick -> onMovieClick(homeEvent.movieId)
        }
    }
    ObserveAsEvents(detailEventFlow) { detailEvent ->
        when (detailEvent) {
            is DetailEvent.OnError -> { /* Handle error */ }
        }
    }

    AdaptiveContent(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        uiState = uiState,
        homeContent = {
            HomeScreen(
                uiState = homeUiState,
                eventFlow = homeEventFlow,
                onRefresh = onHomeRefresh,
                onMovieClick = onMovieClick,
            )
        },
        detailContent = {
            DetailScreen(
                uiState = detailUiState,
                eventFlow = detailEventFlow,
                onRefresh = onDetailRefresh,
                onNavigateToPlayerScreen = onNavigateToPlayerScreen
            )
        }
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveContent(
    uiState: AdaptiveUiState,
    modifier: Modifier = Modifier,
    homeContent: @Composable () -> Unit = {},
    detailContent: @Composable () -> Unit = {},
    onBackClick: () -> Unit = {},
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
        modifier = modifier,
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
    val listScreenMultiplier = 0.55f
    return screenWidth * listScreenMultiplier
}
