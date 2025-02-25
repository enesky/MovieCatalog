package dev.enesky.core.common.remoteconfig

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Enes Kamil YILMAZ on 25/02/2025
 */
object RemoteConfigManager {

    val configStatus: StateFlow<FetchStatus>
        get() = _configStatus
    private val _configStatus = MutableStateFlow(FetchStatus.LOADING)
    private val remoteConfig by lazy { Firebase.remoteConfig }
    private const val FETCH_INTERVAL_DEBUG: Long = 10 // Fetch every 10 seconds in debug
    private const val FETCH_INTERVAL: Long = 60 * 60 * 1 // Fetch every 1 hour
    private const val FETCH_RETRY: Long = 30

    object Values {
        val previewMovieId: Long
            get() = remoteConfig.getLong(RemoteConfigArgs.Keys.PREVIEW_MOVIE_ID)
    }

    /**
     * Initialize Remote Config with default values and fetch the remote config
     */
    fun init(isDebug: Boolean) {
        fetchRemoteConfig()

        val fetchInterval: Long = if (isDebug) FETCH_INTERVAL_DEBUG else FETCH_INTERVAL
        remoteConfig.apply {
            setConfigSettingsAsync(
                remoteConfigSettings {
                    minimumFetchIntervalInSeconds = fetchInterval
                    fetchTimeoutInSeconds = FETCH_RETRY
                },
            )
            setDefaultsAsync(
                mutableMapOf<String, Any>(
                    RemoteConfigArgs.Keys.PREVIEW_MOVIE_ID to RemoteConfigArgs.DefaultValues.PREVIEW_MOVIE_ID,
                ),
            )
        }
    }

    /**
     * Fetch remote config and update the status
     */
    private fun fetchRemoteConfig() {
        _configStatus.value = FetchStatus.LOADING
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _configStatus.value = FetchStatus.SUCCESS
            } else {
                _configStatus.value = FetchStatus.ERROR
            }
        }
    }
}
