package com.sunrin.necvcproject.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.sunrin.necvcproject.ui.theme.LocalColorPalette
import com.sunrin.necvcproject.ui.theme.Pretendard

class TimeTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 6) text.text.substring(0..5) else text.text
        val out = StringBuilder()
        for (i in trimmed.indices) {
            out.append(trimmed[i])
            if (i == 1 || i == 3) out.append(':')
        }
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 1) return offset
                if (offset <= 3) return offset + 1
                if (offset <= 5) return offset + 2
                return 8
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 5) return offset - 1
                if (offset <= 8) return offset - 2
                return 6
            }
        }
        return TransformedText(AnnotatedString(out.toString()), offsetMapping)
    }
}

fun parseTimeToMillis(text: String): Long {
    val parts = text.chunked(2)
    val hours = parts.getOrNull(0)?.toIntOrNull() ?: 0
    val minutes = parts.getOrNull(1)?.toIntOrNull() ?: 0
    val seconds = parts.getOrNull(2)?.toIntOrNull() ?: 0
    return (hours * 3600 + minutes * 60 + seconds) * 1000L
}

fun formatMillisToTime(millis: Long): String {
    val totalSeconds = millis / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return String.format("%02d%02d%02d", hours, minutes, seconds)
}

@Composable
fun TimePicker(
    onTimeChange: (Long) -> Unit, isEnable: Boolean, currentTimeInMillis: Long
) {
    var text by remember { mutableStateOf("00:00:00") }
    LaunchedEffect(currentTimeInMillis) {
        val textValue = formatMillisToTime(currentTimeInMillis)
        text = textValue
    }

    Column {
        BasicTextField(
            enabled = isEnable,
            value = text,
            onValueChange = {
                if (it.length <= 6) {
                    text = it
                    if (isEnable) {
                        onTimeChange(parseTimeToMillis(text))
                    }
                }
            },
            visualTransformation = TimeTransformation(),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontFamily = Pretendard,
                fontWeight = FontWeight.Medium,
                fontSize = 64.sp,
                color = if (isEnable) LocalColorPalette.current.text.primary else LocalColorPalette.current.text.secondary
            )
        )
    }
}