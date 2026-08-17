package com.example.connectivity_kit.domain

sealed class ConnectionStatus {
    data class Available(
        val type: ConnectionType
    ) : ConnectionStatus()

    data class Losing(
        val type: ConnectionType,
        val maxMsToLive: Int
    ) : ConnectionStatus()

    data object Lost : ConnectionStatus()

    data object Unavailable : ConnectionStatus()
}
