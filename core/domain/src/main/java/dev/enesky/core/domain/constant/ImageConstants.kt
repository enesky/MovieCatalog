package dev.enesky.core.domain.constant

/**
 * Created by Enes Kamil YILMAZ on 22/02/2025
 *
 * Constants for image URLs and sizes
 */
object ImageConstants {
    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p"
    private const val POSTER_SIZE = "/w500"
    private const val BACKDROP_SIZE = "/original"

    /**
     * Returns a complete poster URL
     * @param path Poster path from API
     * @return Full URL to poster image
     */
    fun getPosterUrl(path: String?): String? {
        return path?.let { "$IMAGE_BASE_URL$POSTER_SIZE$it" }
    }

    /**
     * Returns a complete backdrop URL
     * @param path Backdrop path from API
     * @return Full URL to backdrop image
     */
    fun getBackdropUrl(path: String?): String? {
        return path?.let { "$IMAGE_BASE_URL$BACKDROP_SIZE$it" }
    }

    /**
     * Returns a complete logo URL (using poster size)
     * @param path Logo path from API
     * @return Full URL to logo image
     */
    fun getLogoUrl(path: String?): String? {
        return path?.let { "$IMAGE_BASE_URL$POSTER_SIZE$it" }
    }
}
