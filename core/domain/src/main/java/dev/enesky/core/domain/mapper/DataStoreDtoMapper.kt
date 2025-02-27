package dev.enesky.core.domain.mapper

import dev.enesky.core.data.model.MovieDto
import dev.enesky.core.domain.constant.ImageConstants
import dev.enesky.core.domain.model.Movie
import dev.enesky.core.domain.utils.formatDateToMonthYear
import dev.enesky.core.domain.utils.formatRating
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * Created by Enes Kamil YILMAZ on 26/02/2025
 *
 * Mapper for converting between DTO and domain models for DataStore operations
 */
class DataStoreDtoMapper @Inject constructor(
    private val json: Json
) {
    // MovieDto -> Movie (Domain)
    fun mapMovieDtoToDomain(dto: MovieDto): Movie {
        return Movie(
            id = dto.id,
            title = dto.title,
            overview = dto.overview,
            popularity = dto.popularity,
            backdropUrl = ImageConstants.getBackdropUrl(dto.backdropPath),
            releaseDate = formatDateToMonthYear(dto.releaseDate),
            genreIds = dto.genreIds,
            language = dto.originalLanguage,
            originalTitle = dto.originalTitle,
            posterUrl = ImageConstants.getPosterUrl(dto.posterPath),
            rating = formatRating(dto.voteAverage),
            voteCount = dto.voteCount,
            adult = dto.adult,
            video = dto.video
        )
    }

    // Movie (Domain) -> MovieDto
    fun mapMovieDomainToDto(movie: Movie): MovieDto {
        return MovieDto(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            adult = movie.adult,
            backdropPath = movie.backdropUrl,
            posterPath = movie.posterUrl,
            popularity = movie.popularity,
            voteAverage = movie.rating.toDouble(),
            voteCount = movie.voteCount,
            video = movie.video,
            originalLanguage = movie.language,
            originalTitle = movie.originalTitle,
            releaseDate = movie.releaseDate,
            genreIds = emptyList(),
        )
    }

    // List serialization helpers
    fun serializeMovieDtoList(movies: List<MovieDto>): String {
        return json.encodeToString(movies)
    }

    fun deserializeMovieDtoList(jsonString: String): List<MovieDto> {
        return json.decodeFromString(jsonString)
    }

    fun serializeMovieDomainList(movies: List<Movie>): String {
        return json.encodeToString(movies.map { mapMovieDomainToDto(it) })
    }

    fun deserializeToMovieDomainList(jsonString: String): List<Movie> {
        val dtoList: List<MovieDto> = json.decodeFromString(jsonString)
        return dtoList.map { mapMovieDtoToDomain(it) }
    }
}