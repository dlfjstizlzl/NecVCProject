package com.sunrin.necvcproject.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

val LightColorPalette = ColorPalette(
    text = TextColors(
        primary = Color(0xFF272523),
        secondary = Color(0xFFB2B1AF)
    ),
    primary = PrimaryColors(
        primary = Color(0xFFF66020),
        secondary = Color(0xFFFFEAE1)
    ),
    background = BackgroundColors(
        primary = Color(0xFFF5F5F5),
        secondary = Color(0xFFFFFFFF)
    )
)
val DarkColorPalette = ColorPalette(
    text = TextColors(
        primary = Color(0xFFFFFFFF),
        secondary = Color(0xFF484848)
    ),
    primary = PrimaryColors(
        primary = Color(0xFFE0591F),
        secondary = Color(0xFF2D211C)
    ),
    background = BackgroundColors(
        primary = Color(0xFF161616),
        secondary = Color(0xFF1C1C1C)
    )
)

@Composable
fun NecVCProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val customColorsPalette =
        if (darkTheme) DarkColorPalette
        else LightColorPalette

    CompositionLocalProvider(
        LocalColorPalette provides customColorsPalette
    ) {
        MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
    }
}