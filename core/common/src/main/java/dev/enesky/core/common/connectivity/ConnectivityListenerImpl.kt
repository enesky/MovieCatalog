package dev.enesky.core.common.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

internal class ConnectivityListenerImpl(context: Context) : ConnectivityListener {

    private val connectivityManager = context.getSystemService<ConnectivityManager>()

    override val isNetworkAvailable: Flow<Boolean> = callbackFlow {
        // Initial check for network state
        val isCurrentlyConnected = isNetworkConnected()
        trySend(isCurrentlyConnected)

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                val connected = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                trySend(connected)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                trySend(false)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(false)
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(true)
            }
        }

        connectivityManager?.registerDefaultNetworkCallback(callback)
        awaitClose { connectivityManager?.unregisterNetworkCallback(callback) }
    }

    // Helper function to check current network state
    private fun isNetworkConnected(): Boolean {
        val networkCapabilities = connectivityManager?.getNetworkCapabilities(
            connectivityManager.activeNetwork
        )
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}
