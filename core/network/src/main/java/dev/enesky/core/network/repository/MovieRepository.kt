package dev.enesky.core.network.repository

/**
* Created by Enes Kamil YILMAZ on 22/02/2025
*/
class MovieRepository(
    private val movieDbDataSource: MovieDbDataSource
) {

    fun getPopularMoviesPagingData() = movieDbDataSource.getPopularMovies()

    fun getTopRatedMoviesPagingData() = movieDbDataSource.getTopRatedMovies()

    fun getUpcomingMoviesPagingData() = movieDbDataSource.getUpcomingMovies()

    suspend fun getMovieDetails(id: Int) = movieDbDataSource.getMovieDetails(id)
}
