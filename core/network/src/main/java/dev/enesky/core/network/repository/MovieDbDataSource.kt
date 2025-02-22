package dev.enesky.core.network.repository

import androidx.paging.PagingData
import dev.enesky.core.common.data.Resource
import dev.enesky.core.data.model.MovieDetailResponse
import dev.enesky.core.data.model.MovieResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
interface MovieDbDataSource {

    fun getPopularMovies(): Flow<PagingData<MovieResponse>>
    fun getTopRatedMovies(): Flow<PagingData<MovieResponse>>
    fun getUpcomingMovies(): Flow<PagingData<MovieResponse>>
    suspend fun getMovieDetails(id: Int): Resource<MovieDetailResponse>
}
