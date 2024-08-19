package com.sunrin.necvcproject.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.sunrin.necvcproject.component.QuizKkamji
import com.sunrin.necvcproject.component.ResultButton
import com.sunrin.necvcproject.component.TitleText
import com.sunrin.necvcproject.data.QuizList
import com.sunrin.necvcproject.data.modifiedQuizList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuizScreen(navigate: () -> Unit, sendResult: (Int) -> Unit) {
    val context = LocalContext.current
    val pref = context.getSharedPreferences("alarm", Context.MODE_PRIVATE)
    val time = millsToMinutes(pref.getLong("initTime", 0))
    val text by remember { mutableStateOf(QuizList) }
    val booleanList = remember { mutableStateListOf(*Array(text.size) { false }) }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val isImeVisible = WindowInsets.isImeVisible
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp, 22.dp),
        bottomBar = {
                ResultButton(text = "채점하기", todo = {
                    sendResult(booleanList.count{!it})
                    navigate()})
            Button(onClick = {
                   sendResult(0)
                    navigate()
                }) {
                    Text(text = "Skip~")
                }
        }
    ){paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues).consumeWindowInsets(paddingValues)
        ) {
            Spacer(modifier = Modifier
                .height(screenHeight / 15)
                .background(Color.Black))
            TitleText(text = time.toString())
            Spacer(modifier = Modifier
                .height(screenHeight / 20))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Spacer 역할을 대신할 수 있음
            ) {
                modifiedQuizList.forEach { (index, word, modified) ->
                    QuizKkamji(modified, word) {
                        booleanList[index] = it
                    }
                }
            }
        }
    }
}
val WindowInsets.Companion.isImeVisible: Boolean
    @Composable
    get() {
        val density = LocalDensity.current
        val ime = this.ime
        return remember {
            derivedStateOf {
                ime.getBottom(density) > 0
            }
        }.value
    }