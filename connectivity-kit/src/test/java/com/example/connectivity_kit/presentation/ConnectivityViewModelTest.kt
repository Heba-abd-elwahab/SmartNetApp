package com.example.connectivity_kit.presentation

import app.cash.turbine.test
import com.example.connectivity_kit.domain.ConnectionStatus
import com.example.connectivity_kit.domain.ConnectionType
import com.example.connectivity_kit.domain.ObserveNetworkStatusUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class ConnectivityViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `connectionStatus exposes flow state from use case`() = runTest {
        val statusFlow = MutableStateFlow<ConnectionStatus>(ConnectionStatus.Available(ConnectionType.WIFI))
        val useCase = mock<ObserveNetworkStatusUseCase> {
            on { invoke() } doReturn statusFlow
        }

        val viewModel = ConnectivityViewModel(useCase)

        viewModel.connectionStatus.test {
            val item = awaitItem()
            if (item is ConnectionStatus.Unavailable) {
                assertEquals(ConnectionStatus.Available(ConnectionType.WIFI), awaitItem())
            } else {
                assertEquals(ConnectionStatus.Available(ConnectionType.WIFI), item)
            }

            statusFlow.value = ConnectionStatus.Lost
            assertEquals(ConnectionStatus.Lost, awaitItem())
        }
    }
}
