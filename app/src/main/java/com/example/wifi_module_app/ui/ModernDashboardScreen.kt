package com.example.wifi_module_app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.connectivity_kit.domain.ConnectionStatus
import com.example.connectivity_kit.domain.ConnectionType
import com.example.connectivity_kit.presentation.ConnectivityViewModel
import com.example.connectivity_kit.presentation.ModernConnectivityBanner

// Color Palette for Minimalist UI
private val BackgroundColor = Color(0xFFF5F7FA) // Soft light-gray
private val CardBackgroundColor = Color.White
private val DarkSlateText = Color(0xFF2D3748)
private val DarkSlateTextMuted = Color(0xFF718096)
private val PastelBlueAccent = Color(0xFF8BA5D2) // Pastel blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernDashboardScreen(
    viewModel: ConnectivityViewModel = hiltViewModel()
) {
    val connectionStatus by viewModel.connectionStatus.collectAsStateWithLifecycle()
    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Network Monitor",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = DarkSlateText
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundColor
                )
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp), // Generous whitespace
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Spacer(modifier = Modifier.height(4.dp))

                HeaderCard(connectionStatus = connectionStatus)

                Text(
                    text = "System Metrics",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = DarkSlateText
                    )
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        MetricCard(
                            title = "Active Interface",
                            value = getInterfaceName(connectionStatus),
                            icon = getInterfaceIcon(connectionStatus),
                            accentColor = PastelBlueAccent
                        )
                    }
                }
            }

            ModernConnectivityBanner(
                status = connectionStatus,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
private fun HeaderCard(connectionStatus: ConnectionStatus) {
    val statusColor = when (connectionStatus) {
        is ConnectionStatus.Available -> PastelBlueAccent
        is ConnectionStatus.Losing -> Color(0xFFF6AD55) // Muted orange
        is ConnectionStatus.Lost, ConnectionStatus.Unavailable -> Color(0xFFFC8181) // Muted red
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp), // More rounded corners
        colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // Flat design
    ) {
        Box(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Overall Network Status",
                    style = MaterialTheme.typography.labelLarge.copy(color = DarkSlateTextMuted)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Connectivity Hub",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = DarkSlateText,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(statusColor)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = when (connectionStatus) {
                            is ConnectionStatus.Available -> "Online (${formatType(connectionStatus.type)})"
                            is ConnectionStatus.Losing -> "Losing Connection"
                            is ConnectionStatus.Lost -> "Offline"
                            is ConnectionStatus.Unavailable -> "Disconnected"
                        },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = DarkSlateText,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    icon: ImageVector,
    accentColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(color = DarkSlateTextMuted)
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = DarkSlateText
                )
            )
        }
    }
}

private fun getInterfaceName(status: ConnectionStatus): String = when (status) {
    is ConnectionStatus.Available -> formatType(status.type)
    is ConnectionStatus.Losing -> formatType(status.type)
    else -> "None"
}

private fun getInterfaceIcon(status: ConnectionStatus): ImageVector = when (status) {
    is ConnectionStatus.Available -> when (status.type) {
        ConnectionType.WIFI -> Icons.Default.Wifi
        ConnectionType.CELLULAR -> Icons.Default.SignalCellularAlt
        else -> Icons.Default.Wifi
    }
    else -> Icons.Default.Wifi
}

private fun formatType(type: ConnectionType): String = when (type) {
    ConnectionType.WIFI -> "Wi-Fi"
    ConnectionType.CELLULAR -> "Cellular"
    ConnectionType.ETHERNET -> "Ethernet"
    ConnectionType.UNKNOWN -> "Unknown"
}

