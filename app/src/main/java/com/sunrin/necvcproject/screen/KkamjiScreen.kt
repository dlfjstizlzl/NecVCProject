package com.sunrin.necvcproject.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sunrin.necvcproject.component.Kkamji
import com.sunrin.necvcproject.component.TitleText
import com.sunrin.necvcproject.data.Quiz

@Composable
fun KkamjiScreen(
    quizList: List<Quiz>, navigate: () -> Unit
) {
    val context = LocalContext.current
    val pref = context.getSharedPreferences("alarm", Context.MODE_PRIVATE)
    val time = millsToMinutes(pref.getLong("initTime", 0))
    val booleanList = remember { mutableStateListOf(*Array(quizList.size) { false }) }


    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val scrollState = rememberScrollState()

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp, 22.dp), bottomBar = {
        Button(onClick = { navigate() }) {
            Text(text = "Skip~")
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(
                modifier = Modifier.height(screenHeight / 15)
            )
            TitleText(text = time.toString())
            Spacer(
                modifier = Modifier.height(screenHeight / 20)
            )

            // Column 사용
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Spacer 역할을 대신할 수 있음
            ) {
                quizList.forEachIndexed { index, quiz ->
                    Kkamji(sentence = quiz.sentence, isCollect = { booleanList[index] = it })
                }
            }
        }
        if (booleanList.all { it }) {
            navigate()
        }
    }
}


fun millsToMinutes(mills: Long): Long {
    return mills / 1000 / 60
}