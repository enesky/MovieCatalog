package dev.enesky.feature.player.components

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.enesky.core.ui.annotation.PreviewUiMode
import dev.enesky.core.ui.theme.MovieCatalogTheme
import dev.enesky.feature.player.helper.MediaConstants
import dev.enesky.feature.player.helper.MediaSourceFactory

/**
 * A composable that displays a media player with fullscreen functionality
 *
 * @param modifier The modifier to be applied to the composable
 * @param onFullscreenChange Callback when fullscreen mode changes
 * @param isInFullscreenMode Flag to indicate if the player should be in fullscreen mode
 */
@SuppressLint("UnsafeOptInUsageError")
@Composable
fun MediaPlayer(
    modifier: Modifier = Modifier,
    onFullscreenChange: (Boolean) -> Unit = {},
    isInFullscreenMode: Boolean = false,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val systemUiController = rememberSystemUiController()
    var isFullscreen by rememberSaveable { mutableStateOf(isLandscape || isInFullscreenMode) }
    var playerView by remember { mutableStateOf<PlayerView?>(null) }
    val exoPlayer = rememberExoPlayer(context)

    LoadMediaContent(exoPlayer, context)

    HandleFullscreenChanges(
        isFullscreen = isFullscreen,
        isInFullscreenMode = isInFullscreenMode,
        isLandscape = isLandscape,
        systemUiController = systemUiController,
        context = context,
        onFullscreenChange = onFullscreenChange,
        onIsFullscreenChanged = { isFullscreen = it }
    )

    BackHandler(enabled = isFullscreen) {
        setPortraitMode(context, onFullscreenChange)
        isFullscreen = false
        onFullscreenChange(false)
    }

    LaunchedEffect(isFullscreen) {
        // This will trigger recomposition and update the AndroidView when fullscreen mode changes
    }

    val playerModifier = modifier
        .fillMaxWidth()
        .height(calculateMoviePreviewHeight())

    MediaPlayerContent(
        modifier = playerModifier.background(MovieCatalogTheme.colors.dark),
        exoPlayer = exoPlayer,
        isFullscreen = isFullscreen,
        onFullscreenToggle = { fullscreen ->
            isFullscreen = fullscreen
            onFullscreenChange(fullscreen)
        },
        onPlayerViewCreate = { view ->
            playerView = view
        }
    )
}

/**
 * Creates and remembers an ExoPlayer instance with custom buffering settings
 *
 * @param context Android context needed for ExoPlayer creation
 * @return Configured ExoPlayer instance
 */
@OptIn(UnstableApi::class)
@Composable
private fun rememberExoPlayer(context: Context): ExoPlayer? {
    if (LocalView.current.isInEditMode) {
        return null
    }
    return remember(context) {
        ExoPlayer.Builder(context)
            .setLoadControl(createDefaultLoadControl())
            .build()
            .apply {
                playWhenReady = false
            }
    }
}

/**
 * Creates a DefaultLoadControl with custom buffering parameters
 *
 * @return Configured DefaultLoadControl instance
 */
@OptIn(UnstableApi::class)
private fun createDefaultLoadControl(): DefaultLoadControl {
    return DefaultLoadControl.Builder()
        .setBufferDurationsMs(
            DefaultLoadControl.DEFAULT_MIN_BUFFER_MS,
            DefaultLoadControl.DEFAULT_MAX_BUFFER_MS,
            DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS / 2,
            DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS / 2
        )
        .build()
}

/**
 * Loads the media content into the ExoPlayer
 *
 * @param exoPlayer The ExoPlayer instance to load content into
 * @param context Android context needed for creating media source
 */
@OptIn(UnstableApi::class)
@Composable
private fun LoadMediaContent(exoPlayer: ExoPlayer?, context: Context) {
    LaunchedEffect(Unit) {
        if (exoPlayer != null && !exoPlayer.isPlaying) {
            try {
                val mediaSource = MediaSourceFactory().createMediaSource(context)
                exoPlayer.setMediaSource(mediaSource)
                exoPlayer.prepare()
            } catch (e: Exception) {
                throw e
            }
        }
    }
}

/**
 * Handles changes to fullscreen mode and system UI visibility
 */
@Composable
private fun HandleFullscreenChanges(
    isFullscreen: Boolean,
    isInFullscreenMode: Boolean,
    isLandscape: Boolean,
    systemUiController: SystemUiController,
    context: Context,
    onFullscreenChange: (Boolean) -> Unit,
    onIsFullscreenChanged: (Boolean) -> Unit
) {
    LaunchedEffect(isFullscreen, isInFullscreenMode) {
        val newFullscreenState = isFullscreen || isInFullscreenMode
        onIsFullscreenChanged(newFullscreenState)

        if (newFullscreenState) {
            systemUiController.isSystemBarsVisible = false
            setLandscapeMode(context)
        } else {
            systemUiController.isSystemBarsVisible = true
            if (isLandscape) {
                setPortraitMode(context, onFullscreenChange)
            }
        }
        onFullscreenChange(newFullscreenState)
    }
}

/**
 * Sets the device orientation to landscape mode
 *
 * @param context Android context needed for orientation change
 */
private fun setLandscapeMode(context: Context) {
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
}

/**
 * Sets the device orientation to portrait mode
 *
 * @param context Android context needed for orientation change
 * @param onFullscreenChange Callback for fullscreen state changes
 */
@SuppressLint("SourceLockedOrientationActivity")
private fun setPortraitMode(
    context: Context,
    onFullscreenChange: (Boolean) -> Unit
) {
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    onFullscreenChange(false)
}

/**
 * Internal composable that handles the actual PlayerView rendering
 *
 * @param exoPlayer The ExoPlayer instance to use
 * @param modifier The modifier to be applied to the composable
 * @param isFullscreen Flag to indicate if the player is in fullscreen mode
 * @param onFullscreenToggle Callback when fullscreen button is toggled
 * @param onPlayerViewCreate Callback when PlayerView is created
 */
@OptIn(UnstableApi::class)
@Composable
private fun MediaPlayerContent(
    exoPlayer: ExoPlayer?,
    modifier: Modifier = Modifier,
    isFullscreen: Boolean = false,
    onFullscreenToggle: (Boolean) -> Unit = {},
    onPlayerViewCreate: (PlayerView) -> Unit = {}
) {
    // Add a fallback message if the player is not available -> mostly for Preview
    if (exoPlayer == null) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Player not available",
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        return
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    ManagePlayerLifecycle(lifecycleOwner, exoPlayer)
    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            createPlayerView(
                ctx,
                exoPlayer,
                isFullscreen,
                isLandscape,
                onFullscreenToggle,
            ).also { playerView ->
                onPlayerViewCreate(playerView)
            }
        },
        update = { playerView ->
            updatePlayerView(
                playerView,
                exoPlayer,
                isFullscreen,
                isLandscape,
                onFullscreenToggle
            )
        }
    )
}

/**
 * Manages the lifecycle events for the player
 */
@Composable
private fun ManagePlayerLifecycle(lifecycleOwner: LifecycleOwner, exoPlayer: ExoPlayer) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer.pause()
                }
                Lifecycle.Event.ON_RESUME -> {
                    if (exoPlayer.playWhenReady) {
                        exoPlayer.play()
                    }
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
        }
    }
}

/**
 * Creates and configures a PlayerView instance
 */
@OptIn(UnstableApi::class)
private fun createPlayerView(
    context: Context,
    exoPlayer: ExoPlayer,
    isFullscreen: Boolean,
    isLandscape: Boolean,
    onFullscreenToggle: (Boolean) -> Unit,
): PlayerView {
    return PlayerView(context).apply {
        player = exoPlayer
        setFullscreenButtonClickListener {
            onFullscreenToggle(!isFullscreen)
        }
        resizeMode = getResizeMode(isFullscreen, isLandscape)
        controllerAutoShow = true
        controllerShowTimeoutMs = MediaConstants.TIMEOUT_MS
        setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
        useController = true
        setShutterBackgroundColor(android.graphics.Color.TRANSPARENT)
    }
}

/**
 * Updates an existing PlayerView with current state
 */
@OptIn(UnstableApi::class)
private fun updatePlayerView(
    playerView: PlayerView,
    exoPlayer: ExoPlayer,
    isFullscreen: Boolean,
    isLandscape: Boolean,
    onFullscreenToggle: (Boolean) -> Unit
) {
    playerView.player = exoPlayer
    playerView.resizeMode = getResizeMode(isFullscreen, isLandscape)

    // Important: Update the fullscreen button click listener to reflect current state
    playerView.setFullscreenButtonClickListener {
        onFullscreenToggle(!isFullscreen)
    }

    if (isFullscreen || isLandscape) {
        playerView.layoutParams = playerView.layoutParams.apply {
            width = -1 // MATCH_PARENT
            height = -1 // MATCH_PARENT
        }
    }
}

/**
 * Determines the appropriate resize mode based on screen orientation
 */
@OptIn(UnstableApi::class)
private fun getResizeMode(isFullscreen: Boolean, isLandscape: Boolean): Int {
    return if (isFullscreen || isLandscape) {
        AspectRatioFrameLayout.RESIZE_MODE_FILL
    } else {
        AspectRatioFrameLayout.RESIZE_MODE_FIT
    }
}

/**
 * Calculates the appropriate height for the player based on screen orientation
 *
 * @return The calculated height as a Dp value
 */
@Composable
private fun calculateMoviePreviewHeight(): Dp {
    val config = LocalConfiguration.current
    val isLandscape = config.orientation == Configuration.ORIENTATION_LANDSCAPE
    val screenWidth = config.screenWidthDp.dp
    val screenHeight = config.screenHeightDp.dp

    val portraitMultiplier = 0.7f
    val landscapeMultiplier = 1f

    return if (isLandscape) {
        screenHeight * landscapeMultiplier
    } else {
        screenWidth * portraitMultiplier
    }
}

@PreviewUiMode
@Composable
private fun MediaPlayerPreview() {
    MovieCatalogTheme {
        MediaPlayer()
    }
}
