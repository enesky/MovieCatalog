package dev.enesky.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 */
@Serializable
data class MoviePagingDto(
    val page: Int?,
    val results: List<MovieDto>,
    @SerialName("total_pages") val totalPages: Int?,
    @SerialName("total_results") val totalResults: Int?
)
