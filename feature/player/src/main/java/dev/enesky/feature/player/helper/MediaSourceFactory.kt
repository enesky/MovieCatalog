package dev.enesky.feature.player.helper

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.dash.DefaultDashChunkSource
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter

/**
 * Created by Enes Kamil YILMAZ on 26/02/2025
 */
class MediaSourceFactory {
    /**
     * Creates a DASH media source with Widevine DRM protection
     * @param context Android context used for bandwidth metering
     * @param mediaUrl URL of the MPD manifest file (defaults to constant)
     * @param licenseUrl URL for DRM license server (defaults to constant)
     * @return Configured DashMediaSource ready to be used by ExoPlayer
     */
    @SuppressLint("UnsafeOptInUsageError")
    fun createMediaSource(
        context: Context,
        mediaUrl: String = MediaConstants.MEDIA_URL,
        licenseUrl: String = MediaConstants.LICENCE_URL
    ): DashMediaSource {
        try {
            // Create bandwidth meter for adaptive streaming
            val bandwidthMeter = DefaultBandwidthMeter.Builder(context)
                .setResetOnNetworkTypeChange(false)
                .build()

            // Create HTTP source factory for video chunks
            val httpDataSourceFactory = DefaultHttpDataSource.Factory()
                .setUserAgent(MediaConstants.USER_AGENT)
                .setTransferListener(bandwidthMeter)
                .setConnectTimeoutMs(MediaConstants.DEFAULT_CONNECT_TIMEOUT_MS)
                .setReadTimeoutMs(MediaConstants.DEFAULT_READ_TIMEOUT_MS)

            // Create DASH chunk source factory
            val dashChunkSourceFactory = DefaultDashChunkSource.Factory(httpDataSourceFactory)

            // Create HTTP source factory for manifest
            val manifestDataSourceFactory = DefaultHttpDataSource.Factory()
                .setUserAgent(MediaConstants.USER_AGENT)

            // Create and configure media item with DRM
            val mediaItem = MediaItem.Builder()
                .setUri(Uri.parse(mediaUrl))
                .setDrmConfiguration(
                    MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
                        .setLicenseUri(licenseUrl)
                        .setMultiSession(false)
                        .setForceDefaultLicenseUri(false)
                        .build()
                )
                .setMimeType(MimeTypes.APPLICATION_MPD)
                .build()

            // Create and return the DASH media source
            return DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory)
                .createMediaSource(mediaItem)
        } catch (e: Exception) {
            throw e
        }
    }
}
