package dev.enesky.core.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.enesky.core.data.model.MovieDto
import dev.enesky.core.network.api.MovieDbApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
class UpcomingMoviesPagingSource @Inject constructor(
    private val moviesService: MovieDbApi,
) : PagingSource<Int, MovieDto>() {

    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        return try {
            val nextPage = params.key ?: 1
            val upcomingMovies = moviesService.getUpcomingMovies(nextPage)
            LoadResult.Page(
                data = upcomingMovies.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (upcomingMovies.results.isEmpty()) null else upcomingMovies.page?.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
