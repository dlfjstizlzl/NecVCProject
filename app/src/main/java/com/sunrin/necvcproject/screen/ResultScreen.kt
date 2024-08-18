package com.sunrin.necvcproject.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.sunrin.necvcproject.R
import com.sunrin.necvcproject.component.CustomButton
import com.sunrin.necvcproject.component.ResultButton
import com.sunrin.necvcproject.component.ResultText

@Composable
fun ResultScreen(inCorrect: Int, restart: () -> Unit, close: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(inCorrect == 0){
                Icon(
                    painter = painterResource(id = R.drawable.collect_shape),
                    contentDescription = "correct"
                )
                ResultText(text = "전부")
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.x_shape),
                    contentDescription = "incorrect"
                )
                ResultText(text = inCorrect.toString())
            }
        }
        val text = if(inCorrect == 0) "나가기" else "다시하기"
        val todo: () -> Unit = if(inCorrect == 0) close else restart
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            ResultButton(text = text, todo = todo)
        }
    }
}