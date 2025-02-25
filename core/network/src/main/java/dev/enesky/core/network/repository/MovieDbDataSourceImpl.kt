package dev.enesky.core.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.enesky.core.common.constants.PagingConstants
import dev.enesky.core.common.data.Resource
import dev.enesky.core.data.model.MovieDetailDto
import dev.enesky.core.data.model.MovieDto
import dev.enesky.core.network.api.MovieDbApi
import dev.enesky.core.network.paging.NowPlayingMoviesPagingSource
import dev.enesky.core.network.paging.PopularMoviesPagingSource
import dev.enesky.core.network.paging.TopRatedMoviesPagingSource
import dev.enesky.core.network.paging.UpcomingMoviesPagingSource
import dev.enesky.core.network.safeApiCall
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */

class MovieDbDataSourceImpl(
    private val movieDbApi: MovieDbApi
) : MovieDbDataSource {

    override fun getNowPlayingMovies(): Flow<PagingData<MovieDto>> {
        return Pager(
            config = PagingConfig(pageSize = PagingConstants.ITEMS_PER_PAGE),
            pagingSourceFactory = { NowPlayingMoviesPagingSource(movieDbApi) }
        ).flow
    }

    override fun getPopularMovies(): Flow<PagingData<MovieDto>> {
        return Pager(
            config = PagingConfig(pageSize = PagingConstants.ITEMS_PER_PAGE),
            pagingSourceFactory = { PopularMoviesPagingSource(movieDbApi) }
        ).flow
    }

    override fun getTopRatedMovies(): Flow<PagingData<MovieDto>> {
        return Pager(
            config = PagingConfig(pageSize = PagingConstants.ITEMS_PER_PAGE),
            pagingSourceFactory = { TopRatedMoviesPagingSource(movieDbApi) }
        ).flow
    }

    override fun getUpcomingMovies(): Flow<PagingData<MovieDto>> {
        return Pager(
            config = PagingConfig(pageSize = PagingConstants.ITEMS_PER_PAGE),
            pagingSourceFactory = { UpcomingMoviesPagingSource(movieDbApi) }
        ).flow
    }

    override suspend fun getMovieDetails(id: Int): Resource<MovieDetailDto> {
        return safeApiCall { movieDbApi.getMovieDetails(id) }
    }
}
