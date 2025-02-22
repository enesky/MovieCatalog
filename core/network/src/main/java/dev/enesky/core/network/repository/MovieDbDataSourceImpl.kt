package dev.enesky.core.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.enesky.core.common.data.Resource
import dev.enesky.core.data.model.MovieDetailResponse
import dev.enesky.core.data.model.MovieResponse
import dev.enesky.core.network.api.MovieDbApi
import dev.enesky.core.network.paging.PopularMoviesPagingSource
import dev.enesky.core.network.paging.TopRatedMoviesPagingSource
import dev.enesky.core.network.safeApiCall
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */

private const val ITEMS_PER_PAGE = 20

class MovieDbDataSourceImpl(
    private val movieDbApi: MovieDbApi
) : MovieDbDataSource {

    override fun getPopularMovies(): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = { PopularMoviesPagingSource(movieDbApi) }
        ).flow
    }

    override fun getTopRatedMovies(): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = { TopRatedMoviesPagingSource(movieDbApi) }
        ).flow
    }

    override fun getUpcomingMovies(): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = { PopularMoviesPagingSource(movieDbApi) }
        ).flow
    }

    override suspend fun getMovieDetails(id: Int): Resource<MovieDetailResponse> {
        return safeApiCall { movieDbApi.getMovieDetails(id) }
    }
}
