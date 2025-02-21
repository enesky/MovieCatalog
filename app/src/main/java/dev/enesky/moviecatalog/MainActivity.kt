package dev.enesky.moviecatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO: Add splash screen api
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MovieCatalogApp()
        }
    }
}
