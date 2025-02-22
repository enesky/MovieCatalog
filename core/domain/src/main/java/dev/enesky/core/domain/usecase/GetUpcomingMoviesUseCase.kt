package dev.enesky.core.domain.usecase

import androidx.paging.PagingData
import dev.enesky.core.data.model.MovieDto
import dev.enesky.core.network.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
class GetUpcomingMoviesUseCase(private val movieRepository: MovieRepository) {
    operator fun invoke(id: Int): Flow<PagingData<MovieDto>> {
        return movieRepository.getUpcomingMoviesPagingData()
    }
}
