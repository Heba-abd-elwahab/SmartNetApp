package com.example.connectivity_kit.data

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.connectivity_kit.domain.ConnectionStatus
import com.example.connectivity_kit.domain.ConnectionType
import com.example.connectivity_kit.domain.ConnectivityRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ConnectivityRepositoryImpl @Inject constructor(private val connectivityManager: ConnectivityManager
) : ConnectivityRepository {
    override fun observeNetworkStatus(): Flow<ConnectionStatus> = callbackFlow {
        fun getCapabilitiesType(capabilities: NetworkCapabilities?): ConnectionType {
            if (capabilities == null) return ConnectionType.UNKNOWN
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> ConnectionType.WIFI
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectionType.CELLULAR
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> ConnectionType.ETHERNET
                else -> ConnectionType.UNKNOWN
            }
        }

        fun getCurrentStatus(): ConnectionStatus {
            return try {
                val activeNetwork = connectivityManager.activeNetwork
                if (activeNetwork != null) {
                    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
                    if (capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                        ConnectionStatus.Available(getCapabilitiesType(capabilities))
                    } else {
                        ConnectionStatus.Unavailable
                    }
                } else {
                    ConnectionStatus.Unavailable
                }
            } catch (e: Exception) {
                ConnectionStatus.Unavailable
            }
        }

        // Emit initial connectivity state immediately upon subscription
        trySend(getCurrentStatus())

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val capabilities = connectivityManager.getNetworkCapabilities(network)
                val type = getCapabilitiesType(capabilities)
                trySend(ConnectionStatus.Available(type))
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                val hasInternet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                if (hasInternet) {
                    val type = getCapabilitiesType(networkCapabilities)
                    trySend(ConnectionStatus.Available(type))
                } else {
                    trySend(ConnectionStatus.Unavailable)
                }
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                val capabilities = connectivityManager.getNetworkCapabilities(network)
                val type = getCapabilitiesType(capabilities)
                trySend(ConnectionStatus.Losing(type, maxMsToLive))
            }

            override fun onLost(network: Network) {
                val current = getCurrentStatus()
                if (current is ConnectionStatus.Available) {
                    trySend(current)
                } else {
                    trySend(ConnectionStatus.Lost)
                }
            }

            override fun onUnavailable() {
                trySend(ConnectionStatus.Unavailable)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        try {
            connectivityManager.registerNetworkCallback(request, callback)
        } catch (e: Exception) {
            trySend(ConnectionStatus.Unavailable)
        }

        awaitClose {
            try {
                connectivityManager.unregisterNetworkCallback(callback)
            } catch (e: Exception) {
                // Ignore cleanup exceptions if system unregisters callback automatically
            }
        }
    }.distinctUntilChanged()
}
