package com.example.connectivity_kit.domain

import javax.inject.Inject

class PerformSpeedTestUseCase @Inject constructor(
    private val repository: ConnectivityRepository
) {
    suspend operator fun invoke(): SpeedTestResult {
        return repository.performSpeedTest()
    }
}
