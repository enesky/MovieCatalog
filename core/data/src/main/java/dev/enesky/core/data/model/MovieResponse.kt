package dev.enesky.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
@Serializable
data class MovieResponse(
    val id: Int,
    val name: String,
    val overview: String,
    val popularity: Double,
    @SerialName("backdrop_path") val backdropPath: String,
    @SerialName("first_air_date") val firstAirDate: String,
    @SerialName("genre_ids") val genreIds: List<Int>,
    @SerialName("origin_country") val originCountry: List<String>,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_name") val originalName: String,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
)
