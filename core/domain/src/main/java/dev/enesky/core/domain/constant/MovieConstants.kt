package dev.enesky.core.domain.constant

import dev.enesky.core.domain.model.Genre
import dev.enesky.core.domain.model.Movie
import dev.enesky.core.domain.model.MovieDetail
import dev.enesky.core.domain.model.ProductionCompany

/**
 * Created by Enes Kamil YILMAZ on 25/02/2025
 */
object MovieConstants {
    val PLACEHOLDER_MOVIE = Movie(
        id = 550,
        title = "Fight Club",
        overview = "An insomniac office worker and a devil-may-care soapmaker form an " +
                "underground fight club that evolves into something much, much more.",
        popularity = 90.5,
        backdropUrl = "https://image.tmdb.org/t/p/w500/rr7E0NoGKxvbkb89eR1GwfoYjpA.jpg",
        releaseDate = "1999-10-15",
        genreIds = listOf(18, 53, 35),
        language = "en",
        originalTitle = "Fight Club",
        posterUrl = "https://image.tmdb.org/t/p/w500/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
        rating = "8.4",
        voteCount = 25000,
        adult = false,
        video = false
    )

    val PLACEHOLDER_DETAILED_MOVIE = MovieDetail(
        id = 550,
        title = "Fight Club",
        overview = "A ticking-time-bomb insomniac and a slippery soap salesman channel primal " +
                "male aggression into a shocking new form of therapy. Their concept catches on, " +
                "with underground \"fight clubs\" forming in every town, until an eccentric gets " +
                "in the way and ignites an out-of-control spiral toward oblivion.",
        popularity = 42.5,
        adult = false,
        budget = 63000000,
        genres = listOf(
            Genre(18, "Drama"),
            Genre(53, "Thriller"),
            Genre(35, "Comedy")
        ),
        genreText = "Drama | Thriller | Comedy",
        homepage = "https://www.foxmovies.com/movies/fight-club",
        revenue = 100853753,
        runtime = "139 min",
        status = "Released",
        tagline = "How much can you know about yourself if you've never been in a fight?",
        video = false,
        backdropUrl = "https://image.tmdb.org/t/p/original/hZkgoQYus5vegHoetLkCJzb17zJ.jpg",
        imdbId = "tt0137523",
        language = "en",
        originalTitle = "Fight Club",
        posterUrl = "https://image.tmdb.org/t/p/original/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
        releaseDate = "Nov 1999",
        rating = "8.4",
        voteCount = 24856,
        productionCompanies = listOf(
            ProductionCompany(
                id = 508,
                name = "Regency Enterprises",
                logoUrl = "https://image.tmdb.org/t/p/original/7PzJdsLGlR7oW4J0J5Xcd0pHGRg.png",
                originCountry = "US"
            ),
            ProductionCompany(
                id = 711,
                name = "Fox 2000 Pictures",
                logoUrl = "https://image.tmdb.org/t/p/original/tEiIH5QesdheJmDAqQwvtN60727.png",
                originCountry = "US"
            ),
            ProductionCompany(
                id = 20555,
                name = "Taurus Film",
                logoUrl = null,
                originCountry = "DE"
            )
        )
    )
}
