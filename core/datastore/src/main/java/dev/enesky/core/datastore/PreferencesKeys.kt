package dev.enesky.core.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    const val STORE_NAME = "movie_app_preferences"
    val NOW_PLAYING_MOVIES = stringPreferencesKey("now_playing_movies")
    val POPULAR_MOVIES = stringPreferencesKey("popular_movies")
    val TOP_RATED_MOVIES = stringPreferencesKey("top_rated_movies")
    val UPCOMING_MOVIES = stringPreferencesKey("upcoming_movies")
    val PREVIEW_MOVIE_ID = stringPreferencesKey("preview_movie_id")
}
