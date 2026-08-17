package com.example.wifi_module_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.wifi_module_app.ui.ModernDashboardScreen
import com.example.wifi_module_app.ui.theme.Wifi_module_appTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Wifi_module_appTheme {
                ModernDashboardScreen()
            }
        }
    }
}