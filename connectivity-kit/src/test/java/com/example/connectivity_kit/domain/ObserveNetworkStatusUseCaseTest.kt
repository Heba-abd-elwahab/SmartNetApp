package com.example.connectivity_kit.domain

import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ObserveNetworkStatusUseCaseTest {

    @Test
    fun `invoke delegates to repository observeNetworkStatus`() = runTest {
        val expectedStatus = ConnectionStatus.Available(ConnectionType.WIFI)
        val repository = mock<ConnectivityRepository> {
            on { observeNetworkStatus() } doReturn flowOf(expectedStatus)
        }

        val useCase = ObserveNetworkStatusUseCase(repository)

        useCase().test {
            assertEquals(expectedStatus, awaitItem())
            awaitComplete()
        }

        verify(repository).observeNetworkStatus()
    }
}
