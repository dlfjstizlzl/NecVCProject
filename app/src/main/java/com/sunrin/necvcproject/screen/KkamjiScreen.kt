package com.sunrin.necvcproject.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sunrin.necvcproject.component.Kkamji
import com.sunrin.necvcproject.component.TitleText
import com.sunrin.necvcproject.data.QuizList

@Composable
fun KkamjiScreen(navigate: () -> Unit) {
    val context = LocalContext.current
    val pref = context.getSharedPreferences("alarm", Context.MODE_PRIVATE)
    val time = millsToMinutes(pref.getLong("initTime", 0))
    val text by remember { mutableStateOf(QuizList) }
    val booleanList = remember { mutableStateListOf(*Array(text.size) { false }) }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp, 22.dp),
        bottomBar = {
            Button(onClick = {navigate()}) {
                Text(text = "Skip~")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier
                .height(screenHeight / 15)
                .background(Color.Black))
            TitleText(text = time.toString())
            Spacer(modifier = Modifier
                .height(screenHeight / 20)
                .background(Color.Black))

            // Column 사용
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Spacer 역할을 대신할 수 있음
            ) {
                text.forEachIndexed { index, item ->
                    Kkamji(
                        scrap = item,
                        isCollect = { booleanList[index] = it }
                    )
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