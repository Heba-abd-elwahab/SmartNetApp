package com.example.connectivity_kit.domain

import kotlinx.coroutines.flow.Flow

interface ConnectivityRepository {
    fun observeNetworkStatus(): Flow<ConnectionStatus>
}
