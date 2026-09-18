package com.example.woof.ui.theme

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme =
        lightColorScheme(
                primary = md_theme_light_primary,
                onPrimary = md_theme_light_onPrimary,
                background = md_theme_light_background,
                onBackground = md_theme_light_onBackground,
                surface = md_theme_light_surface,
                onSurface = md_theme_light_onSurface
        )

private val DarkColorScheme =
        darkColorScheme(
                primary = md_theme_dark_primary,
                onPrimary = md_theme_dark_onPrimary,
                background = md_theme_dark_background,
                onBackground = md_theme_dark_onBackground,
                surface = md_theme_dark_surface,
                onSurface = md_theme_dark_onSurface
        )

@Composable
fun WoofTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        // Dynamic color is available on Android 12+
        dynamicColor: Boolean = false,
        content: @Composable () -> Unit
) {
    val colorScheme =
            when {
                dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    val context = LocalContext.current
                    if (darkTheme) dynamicDarkColorScheme(context)
                    else dynamicLightColorScheme(context)
                }
                darkTheme -> DarkColorScheme
                else -> LightColorScheme
            }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect { setUpEdgeToEdge(view, darkTheme) }
    }

    MaterialTheme(
            colorScheme = colorScheme,
            shapes = Shapes,
            typography = Typography,
            content = content
    )
}

/**
 * Sets up edge-to-edge for the window of this [view]. The system icon colors are set to either
 * light or dark depending on whether [darkTheme] is enabled or not.
 */
@Suppress("DEPRECATION")
private fun setUpEdgeToEdge(view: View, darkTheme: Boolean) {
    val window = (view.context as Activity).window
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor = Color.Transparent.toArgb()
    val navigationBarColor =
            when {
                Build.VERSION.SDK_INT >= 29 -> Color.Transparent.toArgb()
                Build.VERSION.SDK_INT >= 26 -> Color(0xFF, 0xFF, 0xFF, 0x63).toArgb()
                else -> Color(0x00, 0x00, 0x00, 0x50).toArgb()
            }
    window.navigationBarColor = navigationBarColor
    val controller = WindowCompat.getInsetsController(window, view)
    controller.isAppearanceLightStatusBars = !darkTheme
    controller.isAppearanceLightNavigationBars = !darkTheme
}
