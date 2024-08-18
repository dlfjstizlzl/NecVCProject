package com.sunrin.necvcproject.component

import android.widget.Button
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.sunrin.necvcproject.ui.theme.LocalColorPalette
import com.sunrin.necvcproject.ui.theme.Pretendard

@Composable
fun CustomButton(
    text: String,
    colors: Color = LocalColorPalette.current.primary.primary,
    textColor: Color = Color.White,
    todo: () -> Unit,
    isEnable: Boolean = false
) {
    Button(
        onClick = {todo()},
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
        shape = RoundedCornerShape(12.dp),
        colors = if(isEnable) ButtonDefaults.buttonColors(containerColor = colors) else ButtonDefaults.buttonColors(containerColor = LocalColorPalette.current.primary.secondary),
        contentPadding = PaddingValues(15.dp)
    ) {
        Text(
            text = if(isEnable) text else "집중모드 종료하기",
            color = if(isEnable) textColor else LocalColorPalette.current.primary.primary,
            style = TextStyle(
            fontFamily = Pretendard,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
        ))
    }
}

@Composable
@Preview
fun CustomButtonPreview() {
    CustomButton(text = "집중모드 시작하기", todo = {})
}

@Composable
fun ResultButton(
    text: String,
    colors: Color = LocalColorPalette.current.primary.primary,
    textColor: Color = Color.White,
    todo: () -> Unit,
) {
    Button(
        onClick = {todo()},
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colors),
        contentPadding = PaddingValues(15.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            style = TextStyle(
                fontFamily = Pretendard,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            ))
    }
}