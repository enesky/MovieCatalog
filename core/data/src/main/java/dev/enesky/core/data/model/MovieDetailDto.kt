package dev.enesky.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
@Serializable
data class MovieDetailDto(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val popularity: Double?,
    val adult: Boolean?,
    val budget: Int?,
    val genres: List<GenreDto>?,
    val homepage: String?,
    val revenue: Int?,
    val runtime: Int?,
    val status: String?,
    val tagline: String?,
    val video: Boolean?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("belongs_to_collection") val belongsToCollection: JsonElement? = null,
    @SerialName("imdb_id") val imdbId: String?,
    @SerialName("original_language") val originalLanguage: String?,
    @SerialName("original_title") val originalTitle: String?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String?,
    @SerialName("vote_average") val voteAverage: Double?,
    @SerialName("vote_count") val voteCount: Int?,
    @SerialName("production_companies") val productionCompanies: List<ProductionCompanyDto>? = emptyList()
)

@Serializable
data class GenreDto(
    val id: Int?,
    val name: String
)

@Serializable
data class ProductionCompanyDto(
    val id: Int,
    val name: String,
    @SerialName("logo_path") val logoPath: String?,
    @SerialName("origin_country") val originCountry: String
)
