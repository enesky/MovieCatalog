package dev.enesky.core.domain.mapper

import dev.enesky.core.data.model.MovieDto
import dev.enesky.core.data.model.MoviePagingDto
import dev.enesky.core.domain.model.Movie
import dev.enesky.core.domain.model.MoviePaging
import javax.inject.Inject

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 *
 * Mapper for converting movie paging data models to domain models
 */
class MoviePagingMapper @Inject constructor(
    private val movieMapper: MovieMapper
) {

    /**
     * Maps [MoviePagingDto] to [MoviePaging] domain model
     */
    fun mapToDomain(dto: MoviePagingDto): MoviePaging {
        return MoviePaging(
            page = dto.page ?: 1,
            movies = dto.results.map { movieMapper.mapToDomain(it) },
            totalPages = dto.totalPages ?: 1,
            totalResults = dto.totalResults ?: 0
        )
    }

    /**
     * Maps a list of [MovieDto] to a list of [Movie] domain models
     */
    fun mapToDomainList(dtos: List<MovieDto>): List<Movie> {
        return dtos.map { movieMapper.mapToDomain(it) }
    }
}