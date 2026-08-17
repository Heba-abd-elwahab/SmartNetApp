package com.example.connectivity_kit.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connectivity_kit.domain.ConnectionStatus
import com.example.connectivity_kit.domain.ObserveNetworkStatusUseCase
import com.example.connectivity_kit.domain.PerformSpeedTestUseCase
import com.example.connectivity_kit.domain.SpeedTestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel @Inject constructor(
    observeNetworkStatusUseCase: ObserveNetworkStatusUseCase,
    private val performSpeedTestUseCase: PerformSpeedTestUseCase
) : ViewModel() {

    val connectionStatus: StateFlow<ConnectionStatus> = observeNetworkStatusUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ConnectionStatus.Unavailable
        )
        
    private val _speedTestResult = MutableStateFlow<SpeedTestResult?>(null)
    val speedTestResult: StateFlow<SpeedTestResult?> = _speedTestResult.asStateFlow()
    
    private val _isSpeedTestRunning = MutableStateFlow(false)
    val isSpeedTestRunning: StateFlow<Boolean> = _isSpeedTestRunning.asStateFlow()
    
    fun runSpeedTest() {
        if (_isSpeedTestRunning.value) return
        
        viewModelScope.launch {
            _isSpeedTestRunning.value = true
            _speedTestResult.value = performSpeedTestUseCase()
            _isSpeedTestRunning.value = false
        }
    }
}
