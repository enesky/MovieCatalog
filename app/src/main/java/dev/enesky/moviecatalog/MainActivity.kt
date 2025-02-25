package dev.enesky.moviecatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var jankStats: JankStats? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO: Add splash screen api
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

    override fun onResume() {
        super.onResume()
        jankStats?.isTrackingEnabled = true
    }

    override fun onPause() {
        super.onPause()
        jankStats?.isTrackingEnabled = false
    }
}
