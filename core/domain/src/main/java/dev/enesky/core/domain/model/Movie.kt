package dev.enesky.core.domain.model

import kotlinx.serialization.Serializable

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 * Domain model for Movie
 */
@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val popularity: Double,
    val backdropUrl: String?,
    val releaseDate: String,
    val genreIds: List<Int>,
    val language: String,
    val originalTitle: String,
    val posterUrl: String?,
    val rating: Double,
    val voteCount: Int,
    val adult: Boolean,
    val video: Boolean
)
