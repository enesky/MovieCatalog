package dev.enesky.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
@Serializable
data class MovieDetailResponse(
    val id: Int? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val adult: Boolean? = null,
    val budget: Int? = null,
    val genres: List<Genres>? = null,
    val homepage: String? = null,
    val revenue: Int? = null,
    val runtime: Int? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("imdb_id") val imdbId: String? = null,
    @SerialName("original_language") val originalLanguage: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("vote_count") val voteCount: Int? = null
)

@Serializable
data class Genres(
    val id: Int?,
    val name: String
)
