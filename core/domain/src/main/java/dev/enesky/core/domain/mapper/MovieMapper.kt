package dev.enesky.core.domain.mapper

import dev.enesky.core.data.model.GenreDto
import dev.enesky.core.data.model.MovieDetailDto
import dev.enesky.core.data.model.MovieDto
import dev.enesky.core.data.model.ProductionCompanyDto
import dev.enesky.core.domain.constant.ImageConstants
import dev.enesky.core.domain.model.Genre
import dev.enesky.core.domain.model.Movie
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.domain.model.ProductionCompany
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

    /**
     * Maps [MovieDetailDto] to [MovieDetail] domain model
     */
    fun mapToDomain(dto: MovieDetailDto): MovieDetail {
        return MovieDetail(
            id = dto.id ?: 0,
            title = dto.title ?: "",
            overview = dto.overview ?: "",
            popularity = dto.popularity ?: 0.0,
            adult = dto.adult ?: false,
            budget = dto.budget ?: 0,
            genres = dto.genres?.map { mapToDomain(it) } ?: emptyList(),
            homepage = dto.homepage ?: "",
            revenue = dto.revenue ?: 0,
            runtime = dto.runtime ?: 0,
            status = dto.status ?: "",
            tagline = dto.tagline ?: "",
            video = dto.video ?: false,
            backdropUrl = ImageConstants.getBackdropUrl(dto.backdropPath),
            imdbId = dto.imdbId ?: "",
            language = dto.originalLanguage ?: "",
            originalTitle = dto.originalTitle ?: "",
            posterUrl = ImageConstants.getPosterUrl(dto.posterPath),
            releaseDate = dto.releaseDate ?: "",
            rating = dto.voteAverage ?: 0.0,
            voteCount = dto.voteCount ?: 0,
            productionCompanies = dto.productionCompanies?.map { mapToDomain(it) } ?: emptyList()
        )
    }

    /**
     * Maps [GenreDto] data model to [Genre] domain model
     */
    private fun mapToDomain(dto: GenreDto): Genre {
        return Genre(
            id = dto.id ?: 0,
            name = dto.name
        )
    }

    /**
     * Maps [ProductionCompanyDto] data model to [ProductionCompany] domain model
     */
    private fun mapToDomain(dto: ProductionCompanyDto): ProductionCompany {
        return ProductionCompany(
            id = dto.id,
            name = dto.name,
            logoUrl = ImageConstants.getLogoUrl(dto.logoPath),
            originCountry = dto.originCountry
        )
    }
}
