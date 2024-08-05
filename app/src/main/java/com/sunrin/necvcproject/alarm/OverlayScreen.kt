package com.sunrin.necvcproject.alarm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sunrin.necvcproject.ui.theme.NecVCProjectTheme

@Composable
fun OverlayScreen(
    service: AppForegroundService
) {
    NecVCProjectTheme {
        val context = LocalContext.current
        Scaffold { inset ->
            Box(
                modifier = Modifier
                    .padding(inset)
                    .fillMaxSize()
            ) {
                Button(modifier = Modifier.align(Alignment.Center), onClick = {
                    service.closeOverlay()
                }) {
                    Text(text = "close")
                }
            }
        }
    }
}