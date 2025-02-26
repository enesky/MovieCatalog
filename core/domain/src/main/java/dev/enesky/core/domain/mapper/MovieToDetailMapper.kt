package dev.enesky.core.domain.mapper

import dev.enesky.core.domain.constant.ImageConstants
import dev.enesky.core.domain.model.Movie
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.domain.utils.formatDateToMonthYear
import javax.inject.Inject

/**
 * Created by Enes Kamil YILMAZ on 26/02/2025
 *
 * Specific mapper for offline usage
 * Mapper for converting movie data models to movie detail models
 */
class MovieToDetailMapper @Inject constructor() {
    /**
     * Maps [Movie] to [MovieDetail] domain model
     */
    fun mapToDomain(movie: Movie): MovieDetail {
        return MovieDetail(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            popularity = movie.popularity,
            adult = movie.adult,
            budget = 0,
            genres = emptyList(),
            genreText = "",
            homepage = "",
            revenue = 0,
            runtime = "",
            status = "",
            tagline = "",
            video = movie.video,
            backdropUrl = movie.backdropUrl,
            imdbId = "",
            language = "",
            originalTitle = movie.originalTitle,
            posterUrl = ImageConstants.getPosterUrl(movie.posterUrl),
            releaseDate = formatDateToMonthYear(movie.releaseDate),
            rating = movie.rating,
            voteCount = movie.voteCount,
            productionCompanies =  emptyList()
        )
    }
}

