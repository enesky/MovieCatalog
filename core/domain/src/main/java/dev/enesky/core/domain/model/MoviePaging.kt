package dev.enesky.core.domain.model

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 * Domain model for Movie Paging
 */
data class MoviePaging(
    val page: Int,
    val movies: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)
