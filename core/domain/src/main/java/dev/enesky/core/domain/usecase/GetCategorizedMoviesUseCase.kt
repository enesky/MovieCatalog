package dev.enesky.core.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import dev.enesky.core.data.model.MovieDto
import dev.enesky.core.domain.constant.MovieCategory
import dev.enesky.core.domain.mapper.MovieMapper
import dev.enesky.core.domain.model.Movie
import dev.enesky.core.network.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Enes Kamil YILMAZ on 23/02/2025
 *
 * Use case for getting categorized movies.
 * This use case transforms data layer models (MovieDto) to domain layer models (Movie)
 * and handles the paging data flow.
 */
class GetCategorizedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieMapper: MovieMapper,
) {
    /**
     * Invokes the use case to get a categorized movie list
     * @param movieType The category of movies to fetch (POPULAR, TOP_RATED, UPCOMING)
     * @return Flow of PagingData containing domain model Movie objects
     */
    operator fun invoke(movieCategory: MovieCategory): Flow<PagingData<Movie>> {
        val pagingFlow = when (movieCategory) {
            MovieCategory.NOW_PLAYING -> movieRepository.getNowPlayingMoviesPagingData()
            MovieCategory.POPULAR -> movieRepository.getPopularMoviesPagingData()
            MovieCategory.TOP_RATED -> movieRepository.getTopRatedMoviesPagingData()
            MovieCategory.UPCOMING -> movieRepository.getUpcomingMoviesPagingData()
        }

        // Transform the Flow<PagingData<MovieDto>> to Flow<PagingData<Movie>>
        return pagingFlow.map { value: PagingData<MovieDto> ->
            value.map { movieDto: MovieDto ->
                movieMapper.mapToDomain(movieDto)
            }
        }
    }
}
