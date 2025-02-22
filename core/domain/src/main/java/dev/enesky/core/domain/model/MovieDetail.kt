package dev.enesky.core.domain.model

import kotlinx.serialization.Serializable

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 * Domain model for Movie Detail
 */
@Serializable
data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val popularity: Double,
    val adult: Boolean,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val video: Boolean,
    val backdropUrl: String?,
    val imdbId: String,
    val language: String,
    val originalTitle: String,
    val posterUrl: String?,
    val releaseDate: String,
    val rating: Double,
    val voteCount: Int,
    val productionCompanies: List<ProductionCompany>
)

/**
 * Domain model for Genre
 */
@Serializable
data class Genre(
    val id: Int,
    val name: String
)

/**
 * Domain model for Production Company
 */
@Serializable
data class ProductionCompany(
    val id: Int,
    val name: String,
    val logoUrl: String?,
    val originCountry: String
)