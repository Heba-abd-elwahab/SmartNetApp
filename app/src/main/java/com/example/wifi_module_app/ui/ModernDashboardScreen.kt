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
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SwapVert
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
import androidx.compose.ui.graphics.Brush
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernDashboardScreen(
    viewModel: ConnectivityViewModel = hiltViewModel()
) {
    val connectionStatus by viewModel.connectionStatus.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Network Monitor",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
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
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(4.dp))

                HeaderCard(connectionStatus = connectionStatus)

                Text(
                    text = "System Metrics",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 8.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        MetricCard(
                            title = "Active Interface",
                            value = getInterfaceName(connectionStatus),
                            icon = getInterfaceIcon(connectionStatus),
                            accentColor = Color(0xFF673AB7)
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
    val gradientColors = when (connectionStatus) {
        is ConnectionStatus.Available -> listOf(Color(0xFF1E88E5), Color(0xFF1565C0))
        is ConnectionStatus.Losing -> listOf(Color(0xFFFB8C00), Color(0xFFEF6C00))
        is ConnectionStatus.Lost, ConnectionStatus.Unavailable -> listOf(Color(0xFFE53935), Color(0xFFC62828))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(Brush.horizontalGradient(gradientColors))
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.labelLarge.copy(color = Color.White.copy(alpha = 0.8f))
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Connectivity Hub",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(
                                if (connectionStatus is ConnectionStatus.Available) Color.Green else Color.Red
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when (connectionStatus) {
                            is ConnectionStatus.Available -> "Online (${formatType(connectionStatus.type)})"
                            is ConnectionStatus.Losing -> "Losing Connection"
                            is ConnectionStatus.Lost -> "Offline"
                            is ConnectionStatus.Unavailable -> "Disconnected"
                        },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White,
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
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
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
