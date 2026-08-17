package com.example.connectivity_kit.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connectivity_kit.domain.ConnectionStatus
import com.example.connectivity_kit.domain.ObserveNetworkStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel @Inject constructor(
    observeNetworkStatusUseCase: ObserveNetworkStatusUseCase
) : ViewModel() {

    val connectionStatus: StateFlow<ConnectionStatus> = observeNetworkStatusUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ConnectionStatus.Unavailable
        )
}
