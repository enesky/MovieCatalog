package dev.enesky.core.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.enesky.core.data.model.MovieResponse
import dev.enesky.core.network.api.MovieDbApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
class TopRatedMoviesPagingSource @Inject constructor(
    private val moviesService: MovieDbApi,
) : PagingSource<Int, MovieResponse>() {

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        return try {
            val nextPage = params.key ?: 1
            val topRatedMovies = moviesService.getTopRatedMovies(nextPage)
            LoadResult.Page(
                data = topRatedMovies.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (topRatedMovies.results.isEmpty()) null else topRatedMovies.page?.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
