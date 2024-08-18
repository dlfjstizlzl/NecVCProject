package com.sunrin.necvcproject.ui.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sunrin.necvcproject.R

val Pretendard = FontFamily(
    Font(R.font.pretendard_thin, weight = FontWeight.Thin),
    Font(R.font.pretendard_light, weight = FontWeight.Light),
    Font(R.font.pretendard_extralight, weight = FontWeight.ExtraLight),
    Font(R.font.pretendard_regular, weight = FontWeight.Normal),
    Font(R.font.pretendard_medium, weight = FontWeight.Medium),
    Font(R.font.pretendard_bold, weight = FontWeight.Bold),
    Font(R.font.pretendard_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.pretendard_semibold, weight = FontWeight.SemiBold),
)
// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
)