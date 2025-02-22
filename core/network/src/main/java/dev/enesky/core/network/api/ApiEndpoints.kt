package dev.enesky.core.network.api

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
object ApiEndpoints {
    object Movie {
        const val UPCOMING = "movie/upcoming"
        const val POPULAR = "movie/popular"
        const val TOP_RATED = "movie/top_rated"
        const val DETAILS = "movie/{movie_id}"
    }

    object Query {
        const val PAGE = "page"
        const val API_KEY = "api_key"
        const val LANGUAGE = "language"
        const val MOVIE_ID = "movie_id"
    }

    object DefaultValues {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_LANGUAGE = "en-US"
    }
}
