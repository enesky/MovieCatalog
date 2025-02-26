package dev.enesky.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.enesky.core.common.utils.ObserveAsEvents
import dev.enesky.core.ui.annotation.PreviewUiMode
import dev.enesky.core.ui.theme.MovieCatalogTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * Created by Enes Kamil YILMAZ on 26/02/2025
 */
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    eventFlow: Flow<SplashEvent> = emptyFlow(),
    onNavigateToHomeScreen: () -> Unit = {},
) {
    ObserveAsEvents(eventFlow) { detailEvent ->
        when (detailEvent) {
            is SplashEvent.OnError -> { /* Handle error */ }
            is SplashEvent.OnNavigateToHomeScreen -> onNavigateToHomeScreen()
        }
    }

    SplashContent(modifier = modifier)
}

@Composable
fun SplashContent(modifier: Modifier = Modifier) {
    val lottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.movie_anim_1)
    )

    Box(
        modifier = modifier
            .fillMaxSize().
            background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        LottieAnimation(
            composition = lottieComposition,
            restartOnPlay = true,
        )
    }
}

@PreviewUiMode
@Composable
private fun SplashScreenPreview() {
    MovieCatalogTheme {
        SplashContent(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
    }
}
