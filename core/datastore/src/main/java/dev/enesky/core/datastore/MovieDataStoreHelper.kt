package dev.enesky.core.datastore

import dev.enesky.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieDataStoreHelper {
    suspend fun saveNowPlayingMovies(movies: List<Movie>)
    fun getNowPlayingMovies(): Flow<List<Movie>>

    suspend fun savePopularMovies(movies: List<Movie>)
    fun getPopularMovies(): Flow<List<Movie>>

    suspend fun saveTopRatedMovies(movies: List<Movie>)
    fun getTopRatedMovies(): Flow<List<Movie>>

    suspend fun saveUpcomingMovies(movies: List<Movie>)
    fun getUpcomingMovies(): Flow<List<Movie>>

    suspend fun savePreviewMovieId(movieId: Long)
    fun getPreviewMovieId(): Flow<Long?>

    suspend fun clear()
}
