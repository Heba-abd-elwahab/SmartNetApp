package com.example.connectivity_kit.data

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.connectivity_kit.domain.ConnectionStatus
import com.example.connectivity_kit.domain.ConnectionType
import com.example.connectivity_kit.domain.ConnectivityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
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

        suspend fun doesNetworkHaveInternet(): Boolean = withContext(Dispatchers.IO) {
            try {
                val url = URL("http://clients3.google.com/generate_204")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "HEAD"
                connection.connectTimeout = 1500
                connection.readTimeout = 1500
                connection.connect()
                val responseCode = connection.responseCode
                connection.disconnect()
                responseCode == 204
            } catch (e: Exception) {
                false
            }
        }

        fun checkInternetAndSendStatus(capabilities: NetworkCapabilities?) {
            val type = getCapabilitiesType(capabilities)
            if (capabilities == null) {
                trySend(ConnectionStatus.Unavailable)
                return
            }
            if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                trySend(ConnectionStatus.Available(type))
            } else if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                launch {
                    if (doesNetworkHaveInternet()) {
                        trySend(ConnectionStatus.Available(type))
                    } else {
                        trySend(ConnectionStatus.Unavailable)
                    }
                }
            } else {
                trySend(ConnectionStatus.Unavailable)
            }
        }

        fun checkCurrentStatus() {
            try {
                val activeNetwork = connectivityManager.activeNetwork
                val capabilities = if (activeNetwork != null) connectivityManager.getNetworkCapabilities(activeNetwork) else null
                checkInternetAndSendStatus(capabilities)
            } catch (e: Exception) {
                trySend(ConnectionStatus.Unavailable)
            }
        }

        // Emit initial connectivity state immediately upon subscription
        checkCurrentStatus()

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val capabilities = connectivityManager.getNetworkCapabilities(network)
                checkInternetAndSendStatus(capabilities)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                checkInternetAndSendStatus(networkCapabilities)
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                val capabilities = connectivityManager.getNetworkCapabilities(network)
                val type = getCapabilitiesType(capabilities)
                trySend(ConnectionStatus.Losing(type, maxMsToLive))
            }

            override fun onLost(network: Network) {
                checkCurrentStatus()
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
