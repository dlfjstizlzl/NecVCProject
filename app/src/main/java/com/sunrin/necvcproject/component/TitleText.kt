package com.sunrin.necvcproject.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.sunrin.necvcproject.ui.theme.LocalColorPalette
import com.sunrin.necvcproject.ui.theme.Pretendard


@Composable
fun StartTitle() {
    Text(
        buildAnnotatedString {
            append("딴 짓 알림 ")
            withStyle(style = SpanStyle(color = LocalColorPalette.current.primary.primary)) {
                append("시간을\n")
            }
            withStyle(style = SpanStyle(fontFamily = Pretendard, fontWeight = FontWeight.Medium, fontSize = 24.sp)){
                append("설정해주세요")
            }},
        style = MaterialTheme.typography.headlineLarge,
        )
}
@Composable
fun TitleText(text: String){
    Text(
        buildAnnotatedString {
            append("딴 짓한지 \n")
            withStyle(style = SpanStyle(color = LocalColorPalette.current.primary.primary)) {
                append(text+"분")
            }
            append(" 지났어요")},
        style = MaterialTheme.typography.headlineLarge,
    )
}
@Composable
fun ResultText(text: String){
    if(text == "전부") {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = LocalColorPalette.current.primary.primary)) {
                    append(text)
                }
                append(" 맞췄어요!\n")
                withStyle(style = SpanStyle(fontFamily = Pretendard, fontWeight = FontWeight.Medium, fontSize = 18.sp, color = LocalColorPalette.current.text.secondary))
                {
                    append("홈으로 돌아가요")}
            },
            style = MaterialTheme.typography.headlineLarge.copy(textAlign = TextAlign.Center),
        )
    }else{
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = LocalColorPalette.current.primary.primary)) {
                    append(text+"개")
                }
                append(" 문제를 틀렸어요\n")
                withStyle(style = SpanStyle(fontFamily = Pretendard, fontWeight = FontWeight.Medium, fontSize = 18.sp, color = LocalColorPalette.current.text.secondary))
                {
                    append("다시 시도해주세요!")}
            },
            style = MaterialTheme.typography.headlineLarge.copy(textAlign = TextAlign.Center),
        )
    }
}