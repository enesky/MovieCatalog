package dev.enesky.core.domain.mapper

import dev.enesky.core.data.model.MovieDto
import dev.enesky.core.domain.constant.ImageConstants
import dev.enesky.core.domain.model.Movie
import javax.inject.Inject

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 *
 * Mapper for converting movie data models to domain models
 */
class MovieMapper @Inject constructor() {

    /**
     * Maps [MovieDto] to [Movie] domain model
     */
    fun mapToDomain(dto: MovieDto): Movie {
        return Movie(
            id = dto.id,
            title = dto.title,
            overview = dto.overview,
            popularity = dto.popularity,
            backdropUrl = ImageConstants.getBackdropUrl(dto.backdropPath),
            releaseDate = dto.releaseDate,
            genreIds = dto.genreIds,
            language = dto.originalLanguage,
            originalTitle = dto.originalTitle,
            posterUrl = ImageConstants.getPosterUrl(dto.posterPath),
            rating = dto.voteAverage,
            voteCount = dto.voteCount,
            adult = dto.adult,
            video = dto.video
        )
    }
}
