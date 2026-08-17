package com.example.connectivity_kit.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material.icons.filled.WifiTetheringError
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connectivity_kit.domain.ConnectionStatus
import com.example.connectivity_kit.domain.ConnectionType

@Composable
fun ModernConnectivityBanner(
    status: ConnectionStatus,
    modifier: Modifier = Modifier
) {
    val isVisible = status is ConnectionStatus.Lost ||
            status is ConnectionStatus.Unavailable ||
            status is ConnectionStatus.Losing

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { -it }) + expandVertically(expandFrom = Alignment.Top) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
        modifier = modifier.fillMaxWidth()
    ) {
        val dark = isSystemInDarkTheme()

        val bannerStyle = when (status) {
            is ConnectionStatus.Lost -> BannerStyle(
                backgroundColor = if (dark) Color(0xFF421515) else Color(0xFFFFEBEE),
                contentColor = if (dark) Color(0xFFFFB4AB) else Color(0xFFC62828),
                message = "No internet connection. Waiting to reconnect...",
                icon = Icons.Default.WifiOff
            )
            is ConnectionStatus.Unavailable -> BannerStyle(
                backgroundColor = if (dark) Color(0xFF3E2723) else Color(0xFFFFF3E0),
                contentColor = if (dark) Color(0xFFFFCC80) else Color(0xFFE65100),
                message = "Network unavailable. Check your connection.",
                icon = Icons.Default.WifiOff
            )
            is ConnectionStatus.Losing -> BannerStyle(
                backgroundColor = if (dark) Color(0xFF332A00) else Color(0xFFFFF8E1),
                contentColor = if (dark) Color(0xFFFFE082) else Color(0xFFF57F17),
                message = "Connection unstable (${formatConnectionType(status.type)})",
                icon = Icons.Default.WifiTetheringError
            )
            is ConnectionStatus.Available -> BannerStyle(
                backgroundColor = Color.Transparent,
                contentColor = Color.Transparent,
                message = "",
                icon = Icons.Default.WifiOff
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .semantics { liveRegion = LiveRegionMode.Polite },
            shape = RoundedCornerShape(16.dp),
            color = bannerStyle.backgroundColor,
            shadowElevation = 6.dp,
            tonalElevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = bannerStyle.icon,
                    contentDescription = null,
                    tint = bannerStyle.contentColor,
                    modifier = Modifier.size(22.dp)
                )

                Text(
                    text = bannerStyle.message,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    ),
                    color = bannerStyle.contentColor
                )
            }
        }
    }
}

private data class BannerStyle(
    val backgroundColor: Color,
    val contentColor: Color,
    val message: String,
    val icon: ImageVector
)

private fun formatConnectionType(type: ConnectionType): String = when (type) {
    ConnectionType.WIFI -> "Wi-Fi"
    ConnectionType.CELLULAR -> "Cellular"
    ConnectionType.ETHERNET -> "Ethernet"
    ConnectionType.UNKNOWN -> "Unknown"
}
