package com.sunrin.necvcproject.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sunrin.necvcproject.alarm.AppForegroundService
import com.sunrin.necvcproject.data.ProcessedQuizList

import com.sunrin.necvcproject.ui.theme.NecVCProjectTheme

@Composable
fun OverlayScreen(
    service: AppForegroundService
) {
    NecVCProjectTheme {
        val currentScreen = remember { mutableStateOf("Kkamji") }
        var inCorrect by remember { mutableStateOf(0) }
        val quizList = remember {
            ProcessedQuizList.shuffled().take(2)
        }

        Surface(modifier = Modifier.fillMaxSize()) {
            when (currentScreen.value) {
                "Kkamji" -> KkamjiScreen(quizList = quizList) { currentScreen.value = "Quiz" }
                "Quiz" -> QuizScreen(navigate = { currentScreen.value = "Result" },
                    sendResult = { inCorrect = it },
                    quizList = quizList.shuffled().take(1)
                )
                "Result" -> ResultScreen(inCorrect,
                    { currentScreen.value = "Kkamji" },
                    { service.closeOverlay() })
            }
        }
    }
}