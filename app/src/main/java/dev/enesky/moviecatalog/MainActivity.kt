package dev.enesky.moviecatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.metrics.performance.JankStats
import dagger.hilt.android.AndroidEntryPoint
import dev.enesky.core.common.jankstats.JankStat
import dev.enesky.core.common.remoteconfig.FetchStatus
import dev.enesky.core.common.remoteconfig.RemoteConfigManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var jankStats: JankStats? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        initRemoteConfig()
        splashScreen.setKeepOnScreenCondition {
            RemoteConfigManager.configStatus.value == FetchStatus.LOADING
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieCatalogApp()
        }
        setJankStats()
    }

    override fun onResume() {
        super.onResume()
        jankStats?.isTrackingEnabled = true
    }

    override fun onPause() {
        super.onPause()
        jankStats?.isTrackingEnabled = false
    }

    private fun initRemoteConfig() {
        lifecycleScope.launch(Dispatchers.IO) {
            RemoteConfigManager.init(BuildConfig.DEBUG)
        }
    }

    private fun setJankStats() {
        jankStats = JankStats.createAndTrack(
            window,
            JankStat.jankFrameListener
        )
    }
}
