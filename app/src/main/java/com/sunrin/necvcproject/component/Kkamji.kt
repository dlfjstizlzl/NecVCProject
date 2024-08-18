package com.sunrin.necvcproject.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunrin.necvcproject.ui.theme.LocalColorPalette
import com.sunrin.necvcproject.ui.theme.NecVCProjectTheme

@Composable
fun Kkamji(scrap: String, isCollect: (Boolean) -> Unit) {
    var isFinished by remember { mutableStateOf(false) }
    val (text, setText) = remember { mutableStateOf("") }
    if(text == scrap){
        isFinished = true
        isCollect(true)
    }else{
        isFinished = false
        isCollect(false)
    }
    var expandedState by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = LocalColorPalette.current.background.secondary),
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 12.dp),
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    modifier = Modifier.size(20.dp),
                    selected = isFinished,
                    onClick = {},
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = scrap)
            }
            if (expandedState) {
                Spacer(modifier = Modifier.height(12.dp))
                BasicTextField(
                    value = text,
                    onValueChange = { setText(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            LocalColorPalette.current.background.primary,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp, 10.dp),
                    textStyle = MaterialTheme.typography.titleLarge.copy(color = LocalColorPalette.current.text.primary),
                    decorationBox = { innerTextField ->
                        if (text == "") {
                            Text(text = "내용을 입력해주세요.", style = MaterialTheme.typography.titleLarge)
                        } else {
                            innerTextField()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun QuizKkamji(scrap: String, answer : String, isCollect: (Boolean) -> Unit) {
    var isFinished by remember { mutableStateOf(false) }
    val (text, setText) = remember { mutableStateOf("") }
    isFinished = text != ""
    var expandedState by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = LocalColorPalette.current.background.secondary),
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 12.dp),
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    modifier = Modifier.size(20.dp),
                    selected = isFinished,
                    onClick = {},
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = scrap)
            }
            if (expandedState) {
                Spacer(modifier = Modifier.height(12.dp))
                BasicTextField(
                    value = text,
                    onValueChange = {
                        setText(it)
                        if(it == answer) isCollect(true) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            LocalColorPalette.current.background.primary,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp, 10.dp),
                    textStyle = MaterialTheme.typography.titleLarge.copy(color = LocalColorPalette.current.text.primary),
                    decorationBox = { innerTextField ->
                        if (text == "") {
                            Text(text = "내용을 입력해주세요.", style = MaterialTheme.typography.titleLarge)
                        } else {
                            innerTextField()
                        }
                    }
                )
            }
        }
    }
}
