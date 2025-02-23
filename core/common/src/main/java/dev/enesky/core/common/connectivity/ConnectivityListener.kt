package dev.enesky.core.common.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityListener {
    val isNetworkAvailable: Flow<Boolean>
}
