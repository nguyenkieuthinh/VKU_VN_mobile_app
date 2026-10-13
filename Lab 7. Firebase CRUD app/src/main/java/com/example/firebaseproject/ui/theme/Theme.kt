package com.example.firebaseproject.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors =
        lightColorScheme(
                primary = greenColor,
                onPrimary = Color.White,
        )

private val DarkColors =
        darkColorScheme(
                primary = greenColor,
                onPrimary = Color.White,
        )

@Composable
fun FirebaseprojectTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
) {
    MaterialTheme(
            colorScheme = if (darkTheme) DarkColors else LightColors,
            typography = Typography,
            content = content
    )
}
