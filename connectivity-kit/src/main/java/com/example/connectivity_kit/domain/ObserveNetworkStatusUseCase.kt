package com.example.connectivity_kit.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveNetworkStatusUseCase @Inject constructor(
    private val repository: ConnectivityRepository
) {
    operator fun invoke(): Flow<ConnectionStatus> {
        return repository.observeNetworkStatus()
    }
}
