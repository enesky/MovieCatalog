package dev.enesky.core.domain.usecase

import dev.enesky.core.common.data.Resource
import dev.enesky.core.common.data.map
import dev.enesky.core.domain.mapper.MovieDetailMapper
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.network.repository.MovieRepository

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */

class GetMovieDetailsUseCase(
    private val movieRepository: MovieRepository,
    private val movieDetailMapper: MovieDetailMapper
) {
    suspend operator fun invoke(id: Int): Resource<MovieDetail> =
        movieRepository.getMovieDetails(id).map {
            movieDetailMapper.mapToDomain(it)
        }
}
