package dev.enesky.core.network.api

import dev.enesky.core.data.model.MovieDetailDto
import dev.enesky.core.data.model.MoviePagingDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
interface MovieDbApi {
    @GET(ApiEndpoints.Movie.NOW_PLAYING)
    suspend fun getNowPlayingMovies(
        @Query(ApiEndpoints.Query.PAGE) page: Int = ApiEndpoints.DefaultValues.DEFAULT_PAGE
    ): MoviePagingDto

    @GET(ApiEndpoints.Movie.POPULAR)
    suspend fun getPopularMovies(
        @Query(ApiEndpoints.Query.PAGE) page: Int = ApiEndpoints.DefaultValues.DEFAULT_PAGE
    ): MoviePagingDto

    @GET(ApiEndpoints.Movie.TOP_RATED)
    suspend fun getTopRatedMovies(
        @Query(ApiEndpoints.Query.PAGE) page: Int = ApiEndpoints.DefaultValues.DEFAULT_PAGE
    ): MoviePagingDto

    @GET(ApiEndpoints.Movie.UPCOMING)
    suspend fun getUpcomingMovies(
        @Query(ApiEndpoints.Query.PAGE) page: Int = ApiEndpoints.DefaultValues.DEFAULT_PAGE
    ): MoviePagingDto

    @GET(ApiEndpoints.Movie.DETAILS)
    suspend fun getMovieDetails(
        @Path(ApiEndpoints.Query.MOVIE_ID) movieId: Int
    ): Response<MovieDetailDto>
}
