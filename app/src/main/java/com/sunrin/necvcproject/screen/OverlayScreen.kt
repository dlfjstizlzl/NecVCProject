package com.sunrin.necvcproject.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sunrin.necvcproject.alarm.AppForegroundService
import com.sunrin.necvcproject.ui.theme.NecVCProjectTheme

@Composable
fun OverlayScreen(
    service: AppForegroundService
) {
    NecVCProjectTheme {
        val context = LocalContext.current
        val currentScreen = remember { mutableStateOf("Kkamji") }
        var inCorrect by remember { mutableStateOf(0) }

        Surface(modifier = Modifier.fillMaxSize()) {
            when (currentScreen.value) {
                "Kkamji" -> KkamjiScreen { currentScreen.value = "Quiz" }
                "Quiz" -> QuizScreen(navigate = {currentScreen.value="Result"}, sendResult = {inCorrect = it})
                "Result"-> ResultScreen(inCorrect,{currentScreen.value="Kkamji"},{service.closeOverlay()})
            }
        }
    }
}