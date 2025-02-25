package dev.enesky.feature.home.components

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.enesky.core.common.R
import dev.enesky.core.common.constants.PagingConstants
import dev.enesky.core.domain.constant.MovieConstants
import dev.enesky.core.domain.model.Movie
import dev.enesky.core.domain.utils.isLoading
import dev.enesky.core.domain.utils.isNotEmpty
import dev.enesky.core.ui.annotation.PreviewUiMode
import dev.enesky.core.ui.components.Message
import dev.enesky.core.ui.theme.MovieCatalogTheme
import kotlinx.coroutines.flow.flowOf

/**
 * Created by Enes Kamil YILMAZ on 25/02/2025
 */

@Suppress("LongMethod", "MultipleEmitters")
@Composable
fun MoviePagingRow(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.lorem_ipsum_medium),
    pagingItems: LazyPagingItems<Movie>?,
    onMovieClick: (id: Int) -> Unit,
    emptyContent: @Composable LazyItemScope.() -> Unit = {
        Message(
            modifier = Modifier.fillParentMaxSize(),
            messageResourceId = R.string.label_no_movie_result,
        )
    },
) {
    TitleRow(Modifier, title)

    Spacer(modifier = Modifier.size(MovieCatalogTheme.spacing.small))

    val rowState = rememberLazyListState()
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MovieCatalogTheme.spacing.medium),
        contentPadding = PaddingValues(
            horizontal = MovieCatalogTheme.spacing.medium,
        ),
        state = rowState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = rowState),
    ) {
        when {
            pagingItems?.isNotEmpty() == true -> {
                items(pagingItems.itemCount) { index ->
                    pagingItems[index]?.let {
                        MovieItem(
                            movie = it,
                            onNavigateDetailsClick = onMovieClick,
                        )
                    } ?: PlaceholderItem()
                }
            }

            pagingItems == null ||
                pagingItems.loadState.refresh.isLoading ||
                pagingItems.loadState.append.isLoading -> {
                items(PagingConstants.ITEMS_PER_PAGE) {
                    PlaceholderItem()
                }
            }

            else -> {
                item(content = emptyContent)
            }
        }
    }

    Spacer(modifier = Modifier.size(MovieCatalogTheme.spacing.xSmall))
}

@Composable
fun TitleRow(
    modifier: Modifier = Modifier,
    title: String,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = MovieCatalogTheme.spacing.medium,
            ),
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium,
        )
        HorizontalDivider(
            modifier = Modifier.padding(
                horizontal = MovieCatalogTheme.spacing.medium,
            ),
            color = MaterialTheme.colorScheme.onPrimary,
            thickness = 1.dp,
        )
    }
}

@Composable
fun PlaceholderItem() {
    MovieItem(
        movie = MovieConstants.PLACEHOLDER_MOVIE,
        isPlaceholder = true,
    )
}

@PreviewUiMode
@Composable
private fun PreviewMoviePagingRow() {
    val animeList = listOf(MovieConstants.PLACEHOLDER_MOVIE, MovieConstants.PLACEHOLDER_MOVIE, MovieConstants.PLACEHOLDER_MOVIE)
    val filledPagingData = flowOf(PagingData.from(animeList)).collectAsLazyPagingItems()
    val emptyPagingData = flowOf(PagingData.from(emptyList<Movie>())).collectAsLazyPagingItems()

    MovieCatalogTheme {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MovieCatalogTheme.spacing.medium),
        ) {
            item {
                MoviePagingRow(
                    title = "Airing Now",
                    pagingItems = filledPagingData,
                    onMovieClick = {},
                )
            }
            item {
                MoviePagingRow(
                    title = "Airing Now",
                    pagingItems = emptyPagingData,
                    onMovieClick = {},
                )
            }
            item {
                MoviePagingRow(
                    title = "Most Popular",
                    pagingItems = null,
                    onMovieClick = {},
                )
            }
        }
    }
}
