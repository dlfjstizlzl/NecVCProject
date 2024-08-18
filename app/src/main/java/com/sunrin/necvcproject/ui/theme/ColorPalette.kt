package com.sunrin.necvcproject.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ColorPalette(
    val text: TextColors = TextColors(),
    val primary: PrimaryColors = PrimaryColors(),
    val background: BackgroundColors = BackgroundColors(),
)

data class TextColors(
    val primary: Color  = Color.Unspecified,
    val secondary: Color = Color.Unspecified,
)

data class PrimaryColors(
    val primary: Color = Color.Unspecified,
    val secondary: Color = Color.Unspecified,
)

data class BackgroundColors(
    val primary: Color = Color.Unspecified,
    val secondary: Color = Color.Unspecified,
)

val LocalColorPalette = staticCompositionLocalOf { ColorPalette() }