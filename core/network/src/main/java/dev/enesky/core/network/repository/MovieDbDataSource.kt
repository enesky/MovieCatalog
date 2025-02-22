package dev.enesky.core.network.repository

import androidx.paging.PagingData
import dev.enesky.core.common.data.Resource
import dev.enesky.core.data.model.MovieDetailDto
import dev.enesky.core.data.model.MovieDto
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
interface MovieDbDataSource {

    fun getPopularMovies(): Flow<PagingData<MovieDto>>
    fun getTopRatedMovies(): Flow<PagingData<MovieDto>>
    fun getUpcomingMovies(): Flow<PagingData<MovieDto>>
    suspend fun getMovieDetails(id: Int): Resource<MovieDetailDto>
}
