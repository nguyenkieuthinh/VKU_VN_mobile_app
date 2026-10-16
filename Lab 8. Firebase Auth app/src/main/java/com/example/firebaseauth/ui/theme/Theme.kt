package com.example.firebaseauth.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// App chi dung mot bo mau sang cho dung voi anh thiet ke trong de bai
private val LightColors =
        lightColorScheme(
                primary = PizzaRed,
                onPrimary = Color.White,
                background = Color.White,
                surface = Color.White,
        )

@Composable
fun FirebaseAuthTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = LightColors, typography = Typography, content = content)
}
