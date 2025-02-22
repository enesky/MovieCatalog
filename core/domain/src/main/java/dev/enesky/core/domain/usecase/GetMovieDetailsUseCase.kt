package dev.enesky.core.domain.usecase

import dev.enesky.core.common.data.Resource
import dev.enesky.core.data.model.MovieDetailResponse
import dev.enesky.core.network.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */

class GetMovieDetailsUseCase(private val movieRepository: MovieRepository) {
    operator fun invoke(id: Int): Flow<Resource<MovieDetailResponse>> {
        return flow {
            val result = movieRepository.getMovieDetails(id)
            emit(result)
        }
    }
}
