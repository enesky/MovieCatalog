package dev.enesky.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dev.enesky.core.domain.mapper.DataStoreDtoMapper
import dev.enesky.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MovieDataStoreHelperImpl @Inject constructor(
    private val context: Context,
    private val dataStoreDtoMapper: DataStoreDtoMapper
) : MovieDataStoreHelper {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PreferencesKeys.STORE_NAME)
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun saveNowPlayingMovies(movies: List<Movie>) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.NOW_PLAYING_MOVIES] = json.encodeToString(movies)
        }
    }

    override fun getNowPlayingMovies(): Flow<List<Movie>> =
        context.dataStore.data.map { preferences ->
            val moviesJson = preferences[PreferencesKeys.NOW_PLAYING_MOVIES] ?: "[]"
            json.decodeFromString(moviesJson)
        }

    override suspend fun savePopularMovies(movies: List<Movie>) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.POPULAR_MOVIES] = json.encodeToString(movies)
        }
    }

    override fun getPopularMovies(): Flow<List<Movie>> =
        context.dataStore.data.map { preferences ->
            val moviesJson = preferences[PreferencesKeys.POPULAR_MOVIES] ?: "[]"
            json.decodeFromString(moviesJson)
        }

    override suspend fun saveTopRatedMovies(movies: List<Movie>) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOP_RATED_MOVIES] = json.encodeToString(movies)
        }
    }

    override fun getTopRatedMovies(): Flow<List<Movie>> =
        context.dataStore.data.map { preferences ->
            val moviesJson = preferences[PreferencesKeys.TOP_RATED_MOVIES] ?: "[]"
            json.decodeFromString(moviesJson)
        }

    override suspend fun saveUpcomingMovies(movies: List<Movie>) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.UPCOMING_MOVIES] = json.encodeToString(movies)
        }
    }

    override fun getUpcomingMovies(): Flow<List<Movie>> =
        context.dataStore.data.map { preferences ->
            val moviesJson = preferences[PreferencesKeys.UPCOMING_MOVIES] ?: "[]"
            json.decodeFromString(moviesJson)
        }

    override suspend fun savePreviewMovieId(movieId: Long) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.PREVIEW_MOVIE_ID] = movieId.toString()
        }
    }

    override fun getPreviewMovieId(): Flow<Long?> =
        context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.PREVIEW_MOVIE_ID]?.toLongOrNull()
        }

    override suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
