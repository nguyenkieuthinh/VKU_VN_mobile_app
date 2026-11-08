package com.example.marsphotos.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MarsColorScheme = lightColorScheme()

@Composable
fun MarsPhotosTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MarsColorScheme,
        content = content
    )
}
